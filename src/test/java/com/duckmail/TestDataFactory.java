package com.duckmail;

import com.duckmail.enums.CampaignStatus;
import com.duckmail.enums.RecipientStatus;
import com.duckmail.models.Campaign;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.models.EmailTemplate;
import com.duckmail.models.Recipient;

import java.time.LocalDateTime;
import java.util.HashMap;

public class TestDataFactory {
    private static final HashMap<String, String> CAMPAIGN_EMAIL_TEMPLATE_STANDARDS = new HashMap<String, String>() {{
        put("CAMPAIGN_EMAIL_TEMPLATE_URL", "http://mockito.test");
    }};

    private static final HashMap<String, String> CAMPAIGN_STANDARDS = new HashMap<String, String>() {{
        put("CAMPAIGN_NAME_1", "Campaign test 01");
        put("CAMPAIGN_DESCRIPTION_1", "Campaign test 01");
    }};

    private static final HashMap<String, String> EMAIL_TEMPLATE_STANDARDS = new HashMap<String, String>() {{
        put("EMAIL_TEMPLATE_NAME_1", "Email template test 01");
        put("EMAIL_TEMPLATE_SUJECT_1", "Email template subject test 01");
        put("EMAIL_TEMPLATE_BODY_1", "Email template body test 01");
    }};

    private static final HashMap<String, String> RECIPIENT_STANDARDS = new HashMap<String, String>() {{
        put("RECIPIENT_EMAIL_1", "recipient@test.spring");
        put("RECIPIENT_NAME_1", "Recipient Test");
    }};

    public static CampaignEmailTemplate generateCampaignEmailTemplate(Long id, Campaign campaign, EmailTemplate emailTemplate) {
        return new CampaignEmailTemplate(id,
                CAMPAIGN_EMAIL_TEMPLATE_STANDARDS.get("CAMPAIGN_EMAIL_TEMPLATE_URL"),
                campaign,
                emailTemplate
        );
    }

    public static Campaign generateCampaign(Long id) {
        return new Campaign(id,
                CAMPAIGN_STANDARDS.get("CAMPAIGN_NAME_1"),
                CAMPAIGN_STANDARDS.get("CAMPAIGN_DESCRIPTION_1"),
                CampaignStatus.PENDING,
                LocalDateTime.now().plusHours(10),
                0, 0);
    }

    public static EmailTemplate generateEmailTemplate(Long id) {
        return new EmailTemplate(id,
                EMAIL_TEMPLATE_STANDARDS.get("EMAIL_TEMPLATE_NAME_1"),
                EMAIL_TEMPLATE_STANDARDS.get("EMAIL_TEMPLATE_SUJECT_1"),
                EMAIL_TEMPLATE_STANDARDS.get("EMAIL_TEMPLATE_BODY_1"),
                null, null);
    }

    public static Recipient generateRecipient(Long id, CampaignEmailTemplate campaignEmailTemplate, Boolean registerToCampaignEmailTemplate) {
        Recipient recipient = new Recipient(id,
                RECIPIENT_STANDARDS.get("RECIPIENT_EMAIL_1"),
                RECIPIENT_STANDARDS.get("RECIPIENT_NAME_1"),
                RecipientStatus.PENDING,
                null,
                campaignEmailTemplate,
                null);

        if (registerToCampaignEmailTemplate) campaignEmailTemplate.getRecipients().add(recipient);

        return recipient;
    }

    public static Recipient generateRecipient(Long id, String email, CampaignEmailTemplate campaignEmailTemplate, Boolean registerToCampaignEmailTemplate) {
        Recipient recipient = new Recipient(id,
                email,
                RECIPIENT_STANDARDS.get("RECIPIENT_NAME_1"),
                RecipientStatus.PENDING,
                null,
                campaignEmailTemplate,
                null);

        if (registerToCampaignEmailTemplate) campaignEmailTemplate.getRecipients().add(recipient);

        return recipient;
    }
}
