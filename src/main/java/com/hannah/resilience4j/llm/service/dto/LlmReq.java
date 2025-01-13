package com.hannah.resilience4j.llm.service.dto;

import com.hannah.resilience4j.llm.domain.Conversation;
import com.hannah.resilience4j.llm.domain.PromptDictionary;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LlmReq {

    private String conversationId;
    private PromptDictionary promptDictionary;
    private List<Conversation.Messages> messages;

}
