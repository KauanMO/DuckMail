package com.duckmail.services.impl;

import com.duckmail.TestDataFactory;
import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.enums.CampaignStatus;
import com.duckmail.models.Campaign;
import com.duckmail.repositories.CampaignRepository;
import com.duckmail.services.QuartzSchedulerService;
import com.duckmail.services.exception.NotFoundException;
import jakarta.validation.constraints.AssertFalse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CampaignServiceTest {
    @Mock
    private CampaignRepository repository;

    @Mock
    private QuartzSchedulerService quartzSchedulerService;

    @InjectMocks
    private CampaignServiceImpl campaignService;

    @Test
    @DisplayName("Create campaign successfully")
    void createCampaignOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);

        Campaign createdCampaign = campaignService.create(new InCampaignDTO(campaign.getName(),
                campaign.getDescription(),
                campaign.getScheduledDate()));

        assertEquals(campaign.getName(), createdCampaign.getName());
    }

    @Test
    @DisplayName("Find campaign by id Successfully")
    void findCampaignByIdOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);

        when(repository.findById(campaign.getId())).thenReturn(Optional.of(campaign));

        Campaign campaignFound = campaignService.findById(campaign.getId());

        assertEquals(campaignFound.getName(), campaign.getName());
    }

    @Test
    @DisplayName("Find campaign by id not found")
    void findCampaignByIdNOTFOUND() {
        Long invalidCampaignId = 2L;

        when(repository.findById(invalidCampaignId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            campaignService.findById(invalidCampaignId);
        });
    }

    @Test
    @DisplayName("Get pending campaigns successfully")
    void findPendingCampaignsOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);

        when(repository.findByStatusIs(CampaignStatus.PENDING)).thenReturn(List.of(campaign));

        List<Campaign> campaignsFound = campaignService.getPendingCampaigns();

        assertFalse(campaignsFound.isEmpty());
        assertEquals(campaignsFound.getFirst().getName(), campaign.getName());
    }

    @Test
    @DisplayName("Get all campaigns successfully")
    void findAllCampaignsOK() {
        List<Campaign> campaigns = List.of(
                TestDataFactory.generateCampaign(1L),
                TestDataFactory.generateCampaign(2L)
        );

        when(repository.findAll()).thenReturn(campaigns);

        List<Campaign> campaignsFound = campaignService.getAllCampaigns();

        assertFalse(campaignsFound.isEmpty());
        assertEquals(campaignsFound.getFirst().getName(), campaigns.getFirst().getName());
    }

    @Test
    @DisplayName("Update campaign status successfully")
    void updateCampaignStatusOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);

        when(repository.findById(campaign.getId())).thenReturn(Optional.of(campaign));

        campaignService.updateCampaignStatus(campaign.getId(), CampaignStatus.PROGRESS);

        assertEquals(CampaignStatus.PROGRESS, campaign.getStatus());
    }

    @Test
    @DisplayName("Update campaign status not found")
    void updateCampaignStatusNOTFOUND() {
        Long invalidCampaignId = 2L;

        when(repository.findById(invalidCampaignId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            campaignService.updateCampaignStatus(invalidCampaignId, CampaignStatus.PROGRESS);
        });
    }

    @Test
    @DisplayName("Update campaign scheduled date successfully")
    void updateCampaignScheduledDateOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);

        when(repository.findById(campaign.getId())).thenReturn(Optional.of(campaign));

        LocalDateTime newScheduledDate = LocalDateTime.now().plusHours(10);

        campaignService.updateCampaignScheduledDate(campaign.getId(), newScheduledDate);

        assertEquals(campaign.getScheduledDate(), newScheduledDate);
    }
}