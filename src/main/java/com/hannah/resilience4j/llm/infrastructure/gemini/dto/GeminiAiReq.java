package com.hannah.resilience4j.llm.infrastructure.gemini.dto;

import com.hannah.resilience4j.llm.domain.Conversation;
import com.hannah.resilience4j.llm.service.dto.LlmReq;
import com.hannah.resilience4j.llm.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
public class GeminiAiReq {

    private List<Contents> contents;
    private GenerationConfig generationConfig;

    @Builder
    @Data
    public static class Contents {
        private String role;
        private List<Parts> parts;
    }

    @Builder
    @Data
    public static class Parts {
        private String text;
    }

    @Builder
    @Data
    public static class GenerationConfig {
        private float temperature;
    }

    public static GeminiAiReq of(List<Contents> contents, GenerationConfig generationConfig) {
        return GeminiAiReq.builder()
                .contents(contents)
                .generationConfig(generationConfig)
                .build();
    }


    public static GeminiAiReq from(LlmReq llmReq) {
        List<Contents> contents = new ArrayList<>();
        for (Conversation.Messages message : llmReq.getMessages()) {
            contents.add(Contents.builder()
                    .role(message.getRole().equals(Role.SYSTEM) ? "model" : "user")
                    .parts(Arrays.asList(Parts.builder().text(message.getContent()).build()))
                    .build());
        }

        return GeminiAiReq.builder()
                .contents(contents)
                .generationConfig(GenerationConfig.builder()
                        .temperature(llmReq.getPromptDictionary().getParameters().getTemperature())
                        .build())
                .build();
    }
}
