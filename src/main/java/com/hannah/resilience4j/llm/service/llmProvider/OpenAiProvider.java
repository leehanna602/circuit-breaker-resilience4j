package com.hannah.resilience4j.llm.service.llmProvider;

import com.hannah.resilience4j.llm.infrastructure.openAi.OpenAiApi;
import com.hannah.resilience4j.llm.infrastructure.openAi.dto.OpenAiReq;
import com.hannah.resilience4j.llm.infrastructure.openAi.dto.OpenAiRes;
import com.hannah.resilience4j.llm.service.dto.LlmReq;
import com.hannah.resilience4j.llm.service.dto.LlmRes;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
@Primary
@RequiredArgsConstructor
public class OpenAiProvider implements LlmProvider{

    private final OpenAiApi openAiApi;
    private final GeminiProvider geminiProvider;

    @CircuitBreaker(name = "openai", fallbackMethod = "getFallbackProvider")
    @TimeLimiter(name = "openai")
    @Override
    public CompletableFuture<LlmRes> generateResponse(LlmReq request) {
        return CompletableFuture.supplyAsync(() -> {
            OpenAiReq openAiReq = OpenAiReq.from(request);

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            log.info("openAI Req: {}", openAiReq);

            OpenAiRes openAiRes = openAiApi.openAiCompletions(openAiReq);
            stopWatch.stop();
            log.info("openAI Res: {}, {}ms", openAiRes, stopWatch.getTotalTimeMillis());

            return LlmRes.from(request.getConversationId(), openAiRes);
        } );
    }

    @Override
    public CompletableFuture<LlmRes> getFallbackProvider(LlmReq request, Throwable throwable) {
        log.error("openAI getFallbackProvider", throwable);
        return geminiProvider.generateResponse(request);
    }

}
