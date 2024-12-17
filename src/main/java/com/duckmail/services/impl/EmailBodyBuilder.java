package com.duckmail.services.impl;

import com.duckmail.models.EmailTemplate;

public class EmailBodyBuilder {
    public static String alocateUrl(String url, EmailTemplate emailTemplate) {
        String body = emailTemplate.getTextBody() != null ? emailTemplate.getTextBody() : emailTemplate.getHtmlBody();

        if (!body.contains("!URL")) return body;

        return body.replace("!URL", url);
    }
}