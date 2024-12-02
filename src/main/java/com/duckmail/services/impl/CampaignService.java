package com.duckmail.services.impl;

import org.springframework.stereotype.Service;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.models.Campaign;
import com.duckmail.repositories.ICampaignRepository;
import com.duckmail.services.ICampaignService;

@Service
public class CampaignService implements ICampaignService {
    private final ICampaignRepository repository;

    public CampaignService(ICampaignRepository repository) {
        this.repository = repository;
    }

    @Override
    public Campaign create(InCampaignDTO dto) {
        Campaign newCampaign = Campaign.builder()
                .name(dto.name())
                .description(dto.description())
                .scheduledDate(dto.scheduledDate())
                .build();

        repository.save(newCampaign);

        return newCampaign;
    }
}
