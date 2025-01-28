package com.duckmail.services.impl;

import com.duckmail.dtos.campaign.OutCampaignDTO;
import com.duckmail.dtos.dashboard.DailyHourSegregationEnum;
import com.duckmail.dtos.dashboard.OutCampaignsTotalOpeningsDTO;
import com.duckmail.dtos.dashboard.OutTotalOpeningsByHourDTO;
import com.duckmail.models.*;
import com.duckmail.repositories.ClickHistoryRepository;
import com.duckmail.repositories.DeliveryErrorLogRepository;
import com.duckmail.repositories.OpenHistoryRepository;
import com.duckmail.services.CampaignService;
import com.duckmail.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final DeliveryErrorLogRepository deliveryErrorLogRepository;
    private final ClickHistoryRepository clickHistoryRepository;
    private final OpenHistoryRepository openHistoryRepository;
    private final CampaignService campaignService;

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

    @Override
    public Map<String, Map<String, Integer>> getTotalOpeningsByHour() {
        List<Campaign> campaigns = campaignService.getAllCampaigns();

        Map<String, Map<String, Integer>> campaignsAndTotalOpeningsByHourSegregation = new HashMap<>();
        for (DailyHourSegregationEnum segregation : DailyHourSegregationEnum.values()) {
            campaignsAndTotalOpeningsByHourSegregation.put(segregation.getHourSegregationString(), new HashMap<>());
        }

        for (Campaign campaign : campaigns) {
            campaign.getCampaignEmailTemplates().stream()
                    .flatMap(template -> template.getRecipients().stream())
                    .flatMap(recipient -> recipient.getOpenHistories().stream())
                    .forEach(openHistory -> {
                        String segregation = getSegregationForTime(openHistory.getOpenedDate().toLocalTime());
                        if (segregation != null) {
                            incrementCampaignCount(campaignsAndTotalOpeningsByHourSegregation, segregation, campaign.getName());
                        }
                    });
        }

        return campaignsAndTotalOpeningsByHourSegregation;
    }

    private String getSegregationForTime(LocalTime time) {
        for (DailyHourSegregationEnum segregation : DailyHourSegregationEnum.values()) {
            LocalTime start = LocalTime.of(segregation.getInitialHour(), segregation.getInitialMinute());
            LocalTime end = LocalTime.of(segregation.getFinalHour(), segregation.getFinalMinute());
            if (!time.isBefore(start) && time.isBefore(end)) {
                return segregation.getHourSegregationString();
            }
        }
        return null;
    }

    private void incrementCampaignCount(Map<String, Map<String, Integer>> map, String segregation, String campaignName) {
        map.computeIfAbsent(segregation, k -> new HashMap<>())
                .merge(campaignName, 1, Integer::sum);
    }

    private int calculateTotalOpenings(Campaign campaign) {
        return campaign.getCampaignEmailTemplates().stream()
                .flatMap(template -> template.getRecipients().stream())
                .mapToInt(recipient -> recipient.getOpenHistories().size())
                .sum();
    }
}
