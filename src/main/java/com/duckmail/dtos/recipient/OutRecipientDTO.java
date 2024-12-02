package com.duckmail.dtos.recipient;

import com.duckmail.enums.RecipientStatus;
import com.duckmail.models.Recipient;

import java.time.LocalDateTime;

public record OutRecipientDTO(
        Long id,
        String email,
        String name,
        RecipientStatus status,
        LocalDateTime createdDate
) {
    public OutRecipientDTO(Recipient r) {
        this(r.getId(), r.getEmail(), r.getName(), r.getStatus(), r.getCreatedDate());
    }
}