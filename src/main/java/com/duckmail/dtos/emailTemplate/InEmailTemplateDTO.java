package com.duckmail.dtos.emailTemplate;

public record InEmailTemplateDTO(
        String name,
        String subject,
        String htmlBody,
        String textBody) {

}