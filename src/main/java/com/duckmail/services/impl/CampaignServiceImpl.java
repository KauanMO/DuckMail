package com.duckmail.services.impl;

import com.duckmail.enums.CampaignStatus;
import com.duckmail.services.exception.NotFoundException;
import org.springframework.stereotype.Service;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.models.Campaign;
import com.duckmail.repositories.CampaignRepository;
import com.duckmail.services.CampaignService;

import java.util.List;

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

    @Override
    public Campaign findById(Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public List<Campaign> getPendingCampaigns() {
        return repository.findByStatusIs(CampaignStatus.PENDING);
    }

    @Override
    public Campaign changeCampaignStatus(Long campaignId, CampaignStatus newStatus) {
        Campaign campaignFound = repository.findById(campaignId).orElseThrow(NotFoundException::new);
        campaignFound.setStatus(newStatus);

        return repository.save(campaignFound);
    }
}