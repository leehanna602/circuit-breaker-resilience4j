package com.hannah.resilience4j.llm.service.llmProvider;

import com.hannah.resilience4j.llm.infrastructure.gemini.GeminiAiApi;
import com.hannah.resilience4j.llm.infrastructure.gemini.dto.GeminiAiReq;
import com.hannah.resilience4j.llm.infrastructure.gemini.dto.GeminiAiRes;
import com.hannah.resilience4j.llm.service.dto.LlmReq;
import com.hannah.resilience4j.llm.service.dto.LlmRes;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiProvider implements LlmProvider{

    private final GeminiAiApi geminiAiApi;

    @CircuitBreaker(name = "gemini", fallbackMethod = "getFallbackProvider")
    @TimeLimiter(name = "gemini")
    @Override
    public CompletableFuture<LlmRes> generateResponse(LlmReq request) {
        return CompletableFuture.supplyAsync(() -> {
            GeminiAiReq geminiAiReq = GeminiAiReq.from(request);

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            log.info("gemini Req: {}", geminiAiReq);

            GeminiAiRes geminiAiRes = geminiAiApi.generateContent(request.getPromptDictionary().getModels().getFallback(), geminiAiReq);
            stopWatch.stop();
            log.info("gemini Res: {}, {}ms", geminiAiRes, stopWatch.getTotalTimeMillis());

            return LlmRes.from(request.getConversationId(), geminiAiRes);
        });
    }

    @Override
    public CompletableFuture<LlmRes> getFallbackProvider(LlmReq request, Throwable throwable) {
        log.error("gemini getFallbackProvider", throwable);
        return CompletableFuture.supplyAsync(() -> LlmRes.builder()
                .conversationId(request.getConversationId())
                .output("죄송합니다. 일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
                .metadata(LlmRes.MetaData.builder().completionTokens(0)
                        .promptTokens(0)
                        .totalTokens(0)
                        .build())
                .build());
    }

}
