package com.duckmail.services.impl;

import org.springframework.stereotype.Service;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.models.Campaign;
import com.duckmail.repositories.CampaignRepository;
import com.duckmail.services.CampaignService;

@Service
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository repository;

    public CampaignServiceImpl(CampaignRepository repository) {
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
