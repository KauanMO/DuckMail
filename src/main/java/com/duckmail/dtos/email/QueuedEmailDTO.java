package com.duckmail.dtos.email;

import com.duckmail.models.Recipient;

public record QueuedEmailDTO(
        String subject,
        String body,
        String to
) {
    public QueuedEmailDTO(Recipient recipient) {
        this(recipient.getCampaignEmailTemplate().getEmailTemplate().getSubject(),
                recipient.getCampaignEmailTemplate().getEmailTemplate().getHtmlBody() == null
                        ? recipient.getCampaignEmailTemplate().getEmailTemplate().getTextBody()
                        : recipient.getCampaignEmailTemplate().getEmailTemplate().getHtmlBody(),
                recipient.getEmail());
    }
}