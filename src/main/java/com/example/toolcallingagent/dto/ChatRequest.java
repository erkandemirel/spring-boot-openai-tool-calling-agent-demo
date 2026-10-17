package com.example.toolcallingagent.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @NotBlank(message = "Conversation ID must not be blank") String conversationId,
        @NotBlank(message = "Message must not be blank") String message
) {
}
