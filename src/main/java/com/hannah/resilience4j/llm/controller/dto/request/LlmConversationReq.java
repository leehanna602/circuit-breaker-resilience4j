package com.hannah.resilience4j.llm.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LlmConversationReq {
    @Schema(defaultValue = "1")
    private String conversationId;
    @Schema(defaultValue = "common_conversation")
    private String promptType;
    @Schema(defaultValue = "안녕")
    private String input;
}
