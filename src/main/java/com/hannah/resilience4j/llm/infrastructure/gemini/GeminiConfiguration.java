package com.hannah.resilience4j.llm.infrastructure.gemini;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class GeminiConfiguration {
    @Value("${llm.gemini.api-key}")
    private String apiKey;

    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return requestTemplate -> {
            requestTemplate.query("key", apiKey);
        };
    }
}
