package com.hannah.resilience4j.llm.infrastructure.openAi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class OpenAiRes {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    @Builder
    @Data
    public static class Choice {
        private int index;
        private Message message;
        private Object logprobs;
        @JsonProperty("finish_reason")
        private String finishReason;
    }

    @Builder
    @Data
    public static class Message {
        private String role;
        private String content;
        private Object refusal;
    }

    @Data
    public static class Usage {
        @JsonProperty("prompt_tokens")
        private int promptTokens;
        @JsonProperty("completion_tokens")
        private int completionTokens;
        @JsonProperty("total_tokens")
        private int totalTokens;
        @JsonProperty("prompt_tokens_details")
        private Map<Object, Object> promptTokensDetails;
        @JsonProperty("completion_tokens_details")
        private Map<Object, Object> completionTokensDetails;
    }

    public static OpenAiRes createFallbackResponse(String content) {
        Message fallbackMessage = Message.builder()
                .content(content)
                .build();
        Choice fallbackChoice = Choice.builder()
                .message(fallbackMessage)
                .build();
        return OpenAiRes.builder()
                .choices(Collections.singletonList(fallbackChoice))
                .build();
    }

}


