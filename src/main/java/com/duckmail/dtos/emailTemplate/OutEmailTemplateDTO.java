package com.duckmail.dtos.emailTemplate;

import java.time.LocalDateTime;

import com.duckmail.models.EmailTemplate;

public record OutEmailTemplateDTO(
        Long id,
        String name,
        String subject,
        String htmlBody,
        String textBody,
        LocalDateTime createdDate) {
    public OutEmailTemplateDTO(EmailTemplate et) {
        this(et.getId(), et.getName(), et.getSubject(), et.getHtmlBody(), et.getTextBody(), et.getCreatedDate());
    }
}