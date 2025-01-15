package com.hannah.resilience4j.llm.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hannah.resilience4j.llm.controller.dto.request.LlmConversationReq;
import com.hannah.resilience4j.llm.domain.Conversation;
import com.hannah.resilience4j.llm.domain.PromptDictionary;
import com.hannah.resilience4j.llm.service.dto.LlmReq;
import com.hannah.resilience4j.llm.service.dto.LlmRes;
import com.hannah.resilience4j.llm.service.llmProvider.LlmProvider;
import com.hannah.resilience4j.support.exception.CommonErrorCode;
import com.hannah.resilience4j.support.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class LlmService {

    private final ObjectMapper objectMapper;
    private final LlmProvider llmProvider;

    @Value("classpath:prompt/prompt.json")
    private Resource promptResource;

    private final ConcurrentHashMap<String, Conversation> chatHistory = new ConcurrentHashMap<>();

    public LlmRes generate(LlmConversationReq llmConversationReq) {
        log.info("Req: {}", llmConversationReq);

        /* history 조회 */
        Conversation conversation = chatHistory.computeIfAbsent(
                llmConversationReq.getConversationId(),
                k -> Conversation.builder()
                        .messages(new ArrayList<>())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        /* 프롬프트 조회 */
        Map<String, PromptDictionary> promptConfigDict;
        try {
            promptConfigDict = objectMapper.readValue(promptResource.getInputStream(),
                    new TypeReference<Map<String, PromptDictionary>>() {});

        } catch (IOException e) {
            throw new CommonException(CommonErrorCode.PROMPT_CONFIG_ERROR);
        }

        PromptDictionary promptDictionary = promptConfigDict.get(llmConversationReq.getPromptType());
        if (promptDictionary == null) {
            throw new CommonException(CommonErrorCode.PROMPT_CONFIG_ERROR);
        }

        /* 시스템 프롬프트 및 사용자 입력 추가 */
        if (conversation.getMessages().isEmpty()) {
            conversation.makeSystemContent(promptDictionary.getSystem());
        }

        conversation.makeUserContent(llmConversationReq.getInput());
        conversation.setUpdatedAt(LocalDateTime.now());

        /* LLM Generate Request */
        LlmReq llmReq = LlmReq.builder()
                .conversationId(llmConversationReq.getConversationId())
                .promptDictionary(promptDictionary)
                .messages(conversation.getMessages())
                .build();

        LlmRes llmRes = null;
        try {
            llmRes = llmProvider.generateResponse(llmReq).get();

        } catch (ExecutionException e) {
            throw new CommonException(CommonErrorCode.EXTERNAL_LLM_API_ERROR);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CommonException(CommonErrorCode.REQUEST_TIMEOUT);

        } catch (Exception e) {
            log.error("LLM 답변 생성 중 예상치 못한 오류 발생", e);
            throw new CommonException(CommonErrorCode.EXTERNAL_LLM_API_ERROR);
        }

        /* 응답 결과 history 추가 */
        conversation.makeAssistantContent(llmRes.getOutput());
        log.info("=====> history: {}", conversation.getMessages());
        log.info("Res: {}", llmRes);

        return llmRes;
    }
}
