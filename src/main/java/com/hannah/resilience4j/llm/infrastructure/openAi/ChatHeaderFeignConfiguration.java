package com.hannah.resilience4j.llm.infrastructure.openAi;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Slf4j
public class ChatHeaderFeignConfiguration {

    @Value("${llm.gpt.api-key}")
    private String API_KEY;

    @Bean("requestInterceptorWithMethod")
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + API_KEY);
        };
    }

}
