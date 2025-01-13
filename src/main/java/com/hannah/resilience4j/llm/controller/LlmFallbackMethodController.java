package com.hannah.resilience4j.llm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hannah.resilience4j.llm.controller.dto.request.LlmConversationReq;
import com.hannah.resilience4j.llm.controller.dto.response.LlmConversationRes;
import com.hannah.resilience4j.llm.service.LlmService;
import com.hannah.resilience4j.llm.service.dto.LlmRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/llm")
@Tag(name = "LLM")
public class LlmFallbackMethodController {

    private final LlmService llmService;

    @PostMapping("/generate")
    public ResponseEntity<LlmConversationRes> llmConversationWithFallbackMethod(@RequestBody LlmConversationReq llmConversationReq) {
        LlmRes llmRes = llmService.generate(llmConversationReq);
        return ResponseEntity.ok().body(LlmConversationRes.from(llmRes));
    }

}
