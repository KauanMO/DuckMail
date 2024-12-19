package com.duckmail.services.impl;

import com.duckmail.models.EmailTemplate;
import com.duckmail.models.Recipient;
import org.springframework.beans.factory.annotation.Value;

public class EmailBodyBuilder {
    private String body;

    @Value("${ngrok.host}")
    private String HOST;

    public EmailBodyBuilder(EmailTemplate emailTemplate) {
        this.body = emailTemplate.getTextBody() != null
                ? emailTemplate.getTextBody()
                : emailTemplate.getHtmlBody();
    }

    public EmailBodyBuilder alocateUrl(String url) {
        if (body.contains("!URL")) body = body.replace("!URL", url);

        return this;
    }

    public EmailBodyBuilder applyWatermark(Recipient recipient) {
        body += String.format("</br></br> <img width='1' height='1' style='display: none' alt='' src='%s/open/register?recipientId=%d&unique=%d'>", HOST, recipient.getId(), System.currentTimeMillis());

        return this;
    }

    public String build() {
        return body;
    }
}