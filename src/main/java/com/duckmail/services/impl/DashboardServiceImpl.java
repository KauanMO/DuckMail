package com.duckmail.services.impl;

import com.duckmail.dtos.campaign.OutCampaignDTO;
import com.duckmail.dtos.dashboard.OutCampaignsTotalOpeningsDTO;
import com.duckmail.models.Campaign;
import com.duckmail.repositories.ClickHistoryRepository;
import com.duckmail.repositories.DeliveryErrorLogRepository;
import com.duckmail.repositories.OpenHistoryRepository;
import com.duckmail.services.CampaignService;
import com.duckmail.services.DashboardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final DeliveryErrorLogRepository deliveryErrorLogRepository;
    private final ClickHistoryRepository clickHistoryRepository;
    private final OpenHistoryRepository openHistoryRepository;
    private final CampaignService campaignService;

    public DashboardServiceImpl(DeliveryErrorLogRepository deliveryErrorLogRepository, ClickHistoryRepository clickHistoryRepository, OpenHistoryRepository openHistoryRepository, CampaignService campaignService) {
        this.deliveryErrorLogRepository = deliveryErrorLogRepository;
        this.clickHistoryRepository = clickHistoryRepository;
        this.openHistoryRepository = openHistoryRepository;
        this.campaignService = campaignService;
    }

    @Override
    public Long getTotalDeliveryErrors() {
        return deliveryErrorLogRepository.count();
    }

    @Override
    public Long getTotalClickHistories() {
        return clickHistoryRepository.count();
    }

    @Override
    public Long getTotalOpenHistories() {
        return openHistoryRepository.count();
    }

    @Override
    public Long getTotalPendingCampaigns() {
        return (long) campaignService.getPendingCampaigns().size();
    }

    @Override
    public List<OutCampaignsTotalOpeningsDTO> getCampaignsTotalOpenings() {
        List<Campaign> campaigns = campaignService.getAllCampaigns();

        return campaigns.stream()
                .map(campaign -> {
                    int totalOpenings = calculateTotalOpenings(campaign);
                    return new OutCampaignsTotalOpeningsDTO(new OutCampaignDTO(campaign), totalOpenings);
                })
                .toList();
    }

    private int calculateTotalOpenings(Campaign campaign) {
        return campaign.getCampaignEmailTemplates().stream()
                .flatMap(template -> template.getRecipients().stream())
                .mapToInt(recipient -> recipient.getOpenHistories().size())
                .sum();
    }
}
