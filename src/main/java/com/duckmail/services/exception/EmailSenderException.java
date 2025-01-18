package com.duckmail.services.exception;

public class EmailSenderException extends RuntimeException {
    public EmailSenderException(Long recipientId) {
        super("Failed to send email to recipient with Id: " + recipientId);
    }
}
