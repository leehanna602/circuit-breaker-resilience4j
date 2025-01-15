package com.hannah.resilience4j.llm.service.llmProvider;

import com.hannah.resilience4j.llm.service.dto.LlmReq;
import com.hannah.resilience4j.llm.service.dto.LlmRes;

import java.util.concurrent.CompletableFuture;

public interface LlmProvider {

    CompletableFuture<LlmRes> generateResponse(LlmReq request);
    CompletableFuture<LlmRes> getFallbackProvider(LlmReq request, Throwable throwable);

}
