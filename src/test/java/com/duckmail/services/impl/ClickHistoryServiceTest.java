package com.duckmail.services.impl;

import com.duckmail.TestDataFactory;
import com.duckmail.dtos.clickHistory.InClickHistoryDTO;
import com.duckmail.models.*;
import com.duckmail.repositories.ClickHistoryRepository;
import com.duckmail.services.RecipientService;
import com.duckmail.services.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClickHistoryServiceTest {
    @Mock
    private RecipientService recipientService;

    @Mock
    private ClickHistoryRepository repository;

    @InjectMocks
    private ClickHistoryServiceImpl service;

    @Test
    @DisplayName("Create click history successfully")
    void createClickHistoryOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, false);

        when(recipientService.findById(recipient.getId())).thenReturn(recipient);

        ClickHistory clickHistory = TestDataFactory.generateClickHistory(1L, recipient);

        InClickHistoryDTO dto = new InClickHistoryDTO(clickHistory.getBrowserType(),
                clickHistory.getDeviceType(),
                clickHistory.getDate(),
                recipient.getId());

        service.create(dto);

        assertEquals(clickHistory.getRecipient().getName(), recipient.getName());
    }

    @Test
    @DisplayName("Create click history Recipient not found")
    void createClickHistoryNOTFOUND_RecipientNotFound() {
        when(recipientService.findById(1L)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> {
            service.create(new InClickHistoryDTO("test", "test", LocalDateTime.now(), 1L));
        });
    }
}