package com.duckmail.services.impl;

import com.duckmail.TestDataFactory;
import com.duckmail.dtos.dashboard.OutCampaignsTotalOpeningsDTO;
import com.duckmail.models.*;
import com.duckmail.repositories.ClickHistoryRepository;
import com.duckmail.repositories.DeliveryErrorLogRepository;
import com.duckmail.repositories.OpenHistoryRepository;
import com.duckmail.services.CampaignService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {
    @Mock
    private DeliveryErrorLogRepository deliveryErrorLogRepository;
    @Mock
    private ClickHistoryRepository clickHistoryRepository;
    @Mock
    private OpenHistoryRepository openHistoryRepository;
    @Mock
    private CampaignService campaignService;

    @InjectMocks
    private DashboardServiceImpl service;

    @Test
    @DisplayName("Get total delivery errors successfully")
    void getTotalDeliveryErrorsOK() {
        when(deliveryErrorLogRepository.count()).thenReturn(1L);

        Long totalDeliveryErrors = service.getTotalDeliveryErrors();

        assertEquals(1L, totalDeliveryErrors);
    }

    @Test
    @DisplayName("Get total click histories successfully")
    void getTotalClickHistoriesOK() {
        when(clickHistoryRepository.count()).thenReturn(1L);

        Long totalClickHistories = service.getTotalClickHistories();

        assertEquals(1L, totalClickHistories);
    }

    @Test
    @DisplayName("Get total open histories successfully")
    void getTotalOpenHistoriesOK() {
        when(openHistoryRepository.count()).thenReturn(1L);

        Long totalOpenHistories = service.getTotalOpenHistories();

        assertEquals(1L, totalOpenHistories);
    }

    @Test
    @DisplayName("Get total pending campaigns successfully")
    void getTotalPendingCampaigns() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);

        when(campaignService.getPendingCampaigns()).thenReturn(List.of(campaign));

        Long totalPendingCampaigns = service.getTotalPendingCampaigns();

        assertEquals(1L, totalPendingCampaigns);
    }

    @Test
    @DisplayName("Get campaign with total openings successfully")
    void getCampaignsTotalOpeningsOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, true);
        TestDataFactory.generateOpenHistory(1L, recipient, true);

        when(campaignService.getAllCampaigns()).thenReturn(List.of(campaign));

        List<OutCampaignsTotalOpeningsDTO> campaignsTotalOpeningsDTOS = service.getCampaignsTotalOpenings();

        assertFalse(campaignsTotalOpeningsDTOS.isEmpty());
        assertEquals(1, campaignsTotalOpeningsDTOS.getFirst().totalOpenings());
    }

    @Test
    @DisplayName("Get total openings by hour successfully")
    void getTotalOpeningsByHourOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, true);
        TestDataFactory.generateOpenHistory(1L, recipient, true);

        when(campaignService.getAllCampaigns()).thenReturn(List.of(campaign));

        Map<String, Map<String, Integer>> totalOpeningsByHour = service.getTotalOpeningsByHour();

        boolean containsRegisterOpeningHistory = false;

        for (String date : totalOpeningsByHour.keySet())
            for (String campaignName : totalOpeningsByHour.get(date).keySet())
                if (totalOpeningsByHour.get(date).get(campaignName) > 0) containsRegisterOpeningHistory = true;

        assertTrue(containsRegisterOpeningHistory);
    }


}