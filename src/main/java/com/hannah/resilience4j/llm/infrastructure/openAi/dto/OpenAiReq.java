package com.hannah.resilience4j.llm.infrastructure.openAi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hannah.resilience4j.llm.domain.Conversation;
import com.hannah.resilience4j.llm.service.dto.LlmReq;
import com.hannah.resilience4j.llm.domain.Role;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OpenAiReq {

    private String model;
    private List<GptMessages> messages;
    private float temperature;
    @JsonProperty("top_p")
    private float topP;

    @Builder
    @Data
    public static class GptMessages {
        private String role;
        private String content;
    }

    public static OpenAiReq of(String model, List<GptMessages> messages, float temperature, float topP) {
        return OpenAiReq.builder()
                .model(model)
                .messages(messages)
                .temperature(temperature)
                .topP(topP)
                .build();
    }

    public static OpenAiReq from(LlmReq llmReq) {
        List<GptMessages> gptMessages = new ArrayList<>();
        for (Conversation.Messages message : llmReq.getMessages()) {
            gptMessages.add(GptMessages.builder()
                    .role(message.getRole().equals(Role.SYSTEM) ? "system" : "user")
                    .content(message.getContent())
                    .build());
        }

        return OpenAiReq.builder()
                .model(llmReq.getPromptDictionary().getModels().getPrimary())
                .messages(gptMessages)
                .temperature(llmReq.getPromptDictionary().getParameters().getTemperature())
                .topP(llmReq.getPromptDictionary().getParameters().getTopP())
                .build();
    }

}
