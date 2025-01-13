package com.hannah.resilience4j.llm.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Conversation {
    private List<Messages> messages;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    public static class Messages {
        private Role role;
        private String content;
        private LocalDateTime timestamp;
    }

    public void makeUserContent(String content) {
        this.messages.add(Messages.builder()
                .role(Role.USER)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    public void makeSystemContent(String content) {
        this.messages.add(Messages.builder()
                .role(Role.SYSTEM)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    public void makeAssistantContent(String content) {
        this.messages.add(Messages.builder()
                .role(Role.ASSISTANT)
                .content(content)
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

}
