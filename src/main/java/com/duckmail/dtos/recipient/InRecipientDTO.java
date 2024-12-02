package com.duckmail.dtos.recipient;

public record InRecipientDTO(
    String email,
    String name,
    Long campaignEmailTemplateId) {
    
}