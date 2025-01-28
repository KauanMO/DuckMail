package com.duckmail.dtos.email;

public record QueuedEmailDTO(
        String subject,
        String body,
        String to,
        Long recipientId
) {
}