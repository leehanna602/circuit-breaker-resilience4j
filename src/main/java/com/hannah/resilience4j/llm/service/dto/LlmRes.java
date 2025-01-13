package com.hannah.resilience4j.llm.service.dto;

import com.hannah.resilience4j.llm.infrastructure.gemini.dto.GeminiAiRes;
import com.hannah.resilience4j.llm.infrastructure.openAi.dto.OpenAiRes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LlmRes {
    private String conversationId;
    private String output;
    private MetaData metadata;

    @Builder
    @Data
    public static class MetaData {
        private int promptTokens;
        private int completionTokens;
        private int totalTokens;
    }

    public static LlmRes from(String conversationId, OpenAiRes openAiRes) {
        return LlmRes.builder()
                .conversationId(conversationId)
                .output(openAiRes.getChoices().get(0).getMessage().getContent())
                .metadata(MetaData.builder()
                        .promptTokens(openAiRes.getUsage().getPromptTokens())
                        .completionTokens(openAiRes.getUsage().getCompletionTokens())
                        .totalTokens(openAiRes.getUsage().getTotalTokens())
                        .build())
                .build();
    }

    public static LlmRes from(String conversationId, GeminiAiRes geminiAiRes) {
        return LlmRes.builder()
                .conversationId(conversationId)
                .output(geminiAiRes.getCandidates().get(0).getContent().getParts().get(0).getText())
                .metadata(MetaData.builder()
                        .promptTokens(geminiAiRes.getUsageMetadata().getPromptTokenCount())
                        .completionTokens(geminiAiRes.getUsageMetadata().getCandidatesTokenCount())
                        .totalTokens(geminiAiRes.getUsageMetadata().getTotalTokenCount())
                        .build())
                .build();
    }
}
