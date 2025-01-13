package com.hannah.resilience4j.llm.controller.dto.response;

import com.hannah.resilience4j.llm.service.dto.LlmRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LlmConversationRes {
    private String conversationId;
    private String output;
    private LlmRes.MetaData metadata;

    @Builder
    @Data
    public static class MetaData {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
    }

    public static LlmConversationRes from(LlmRes llmRes) {
        return LlmConversationRes.builder()
                .conversationId(llmRes.getConversationId())
                .output(llmRes.getOutput())
                .metadata(llmRes.getMetadata())
                .build();
    }

}
