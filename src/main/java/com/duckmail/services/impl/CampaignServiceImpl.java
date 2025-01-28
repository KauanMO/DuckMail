package com.duckmail.services.impl;

import com.duckmail.enums.CampaignStatus;
import com.duckmail.services.QuartzSchedulerService;
import com.duckmail.services.exception.BadRequestException;
import com.duckmail.services.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.models.Campaign;
import com.duckmail.repositories.CampaignRepository;
import com.duckmail.services.CampaignService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    private final CampaignRepository repository;
    private final QuartzSchedulerService quartzSchedulerService;

    @Override
    public Campaign create(InCampaignDTO dto) {
        Campaign newCampaign = Campaign.builder()
                .name(dto.name())
                .description(dto.description())
                .scheduledDate(dto.scheduledDate())
                .build();

        quartzSchedulerService.scheduleCampaign(newCampaign);

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
    public List<Campaign> getAllCampaigns() {
        return repository.findAll();
    }

    @Override
    public Campaign updateCampaignStatus(Long campaignId, CampaignStatus newStatus) {
        Campaign campaignFound = repository.findById(campaignId).orElseThrow(NotFoundException::new);
        campaignFound.setStatus(newStatus);

        return repository.save(campaignFound);
    }

    @Override
    public Campaign updateCampaignScheduledDate(Long campaignId, LocalDateTime newScheduledDate) {
        Campaign campaignFound = repository.findById(campaignId).orElseThrow(NotFoundException::new);
        campaignFound.setScheduledDate(newScheduledDate);

        quartzSchedulerService.updateScheduledCampaignDate(campaignFound, newScheduledDate);

        return repository.save(campaignFound);
    }
}