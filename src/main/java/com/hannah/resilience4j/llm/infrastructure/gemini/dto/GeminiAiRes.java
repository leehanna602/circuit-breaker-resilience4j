package com.hannah.resilience4j.llm.infrastructure.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiAiRes {
    private List<Candidates> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Candidates {
        private Content content;
        private String finishReason;
        private List<SafetyRatings> safetyRatings;
        private float avgLogprobs;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Content {
        private List<Parts> parts;
        private String role;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Parts {
        private String text;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class SafetyRatings {
        private String category;
        private String probability;
    }
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class UsageMetadata {
        private Integer promptTokenCount;
        private Integer candidatesTokenCount;
        private Integer totalTokenCount;
    }
}
