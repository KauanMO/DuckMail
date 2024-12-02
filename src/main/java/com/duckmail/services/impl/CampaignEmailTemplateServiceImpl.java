package com.duckmail.services.impl;

import com.duckmail.services.exception.NotFoundException;
import org.springframework.stereotype.Service;

import com.duckmail.dtos.campaignEmailTemplate.InCampaignEmailTemplateDTO;
import com.duckmail.models.Campaign;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.models.EmailTemplate;
import com.duckmail.repositories.CampaignEmailTemplateRepository;
import com.duckmail.services.CampaignEmailTemplateService;
import com.duckmail.services.CampaignService;
import com.duckmail.services.EmailTemplateService;

@Service
public class CampaignEmailTemplateServiceImpl implements CampaignEmailTemplateService {
    private final CampaignEmailTemplateRepository repository;
    private final CampaignService campaignService;
    private final EmailTemplateService emailTemplateService;

    public CampaignEmailTemplateServiceImpl(CampaignService campaignService, 
            EmailTemplateService emailTemplateService,
            CampaignEmailTemplateRepository repository) {
        this.campaignService = campaignService;
        this.emailTemplateService = emailTemplateService;
        this.repository = repository;
    }

    @Override
    public CampaignEmailTemplate create(InCampaignEmailTemplateDTO dto) throws Exception {
        Campaign campaignFound = campaignService.findById(dto.campaignId());
        EmailTemplate emailTemplateFound = emailTemplateService.findById(dto.emailTemplateId());

        CampaignEmailTemplate newCampaignEmailTemplate = CampaignEmailTemplate.builder()
            .url(dto.url())
            .campaign(campaignFound)
            .emailTemplate(emailTemplateFound)
            .build();

        repository.save(newCampaignEmailTemplate);

        return newCampaignEmailTemplate;
    }

    @Override
    public CampaignEmailTemplate findById(Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }
}