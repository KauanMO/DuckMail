package com.duckmail.services.impl;

import com.duckmail.TestDataFactory;
import com.duckmail.models.*;
import com.duckmail.repositories.DeliveryErrorLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryErrorLogServiceTest {
    @Mock
    private DeliveryErrorLogRepository repository;

    @InjectMocks
    private DeliveryErrorLogServiceImpl service;

    @Test
    @DisplayName("Register error log successfully")
    void registerErrorLogOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, true);
        TestDataFactory.generateOpenHistory(1L, recipient, true);

        DeliveryErrorLog deliveryErrorLog = TestDataFactory.generateDeliveryErrorLog(1L, recipient, "Error");

        service.registerError(deliveryErrorLog.getMessage(), deliveryErrorLog.getDate(), recipient.getId());

        assertNotNull(recipient.getDeliveryErrorLog());
    }
}