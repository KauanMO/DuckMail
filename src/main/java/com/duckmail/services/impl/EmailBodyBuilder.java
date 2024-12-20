package com.duckmail.services.impl;

import com.duckmail.models.Recipient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailBodyBuilder {
    private String body;

    @Value("${ngrok.host}")
    private String HOST;

    public EmailBodyBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    public EmailBodyBuilder alocateUrl(Recipient recipient) {
        if (body.contains("!URL"))
            body = body.replace("!URL", String.format("<a href='%s/click/register?browserType=null&deviceType=null&recipientId=%d'>Ir até a campanha</a>", HOST, recipient.getId()));

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