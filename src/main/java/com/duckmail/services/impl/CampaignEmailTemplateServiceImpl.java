package com.duckmail.services.impl;

import com.duckmail.infra.email.RegisterEmailQueueListenerJob;
import com.duckmail.infra.rabbitmq.RabbitEmailProducer;
import com.duckmail.services.QuartzSchedulerService;
import com.duckmail.services.exception.BadRequestException;
import com.duckmail.services.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

import com.duckmail.dtos.campaignEmailTemplate.InCampaignEmailTemplateDTO;
import com.duckmail.models.Campaign;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.models.EmailTemplate;
import com.duckmail.repositories.CampaignEmailTemplateRepository;
import com.duckmail.services.CampaignEmailTemplateService;
import com.duckmail.services.CampaignService;
import com.duckmail.services.EmailTemplateService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CampaignEmailTemplateServiceImpl implements CampaignEmailTemplateService {
    private final CampaignEmailTemplateRepository repository;
    private final CampaignService campaignService;
    private final EmailTemplateService emailTemplateService;
    private final RabbitEmailProducer rabbitEmailProducer;

    @Override
    public CampaignEmailTemplate create(InCampaignEmailTemplateDTO dto) throws SchedulerException {
        Campaign campaignFound = campaignService.findById(dto.campaignId());

        if (campaignFound
                .getScheduledDate()
                .isBefore(LocalDateTime.now()
                        .atZone(ZoneId.of("America/Sao_Paulo"))
                        .toLocalDateTime())) {
            throw new BadRequestException("Campanha já realizada");
        }

        EmailTemplate emailTemplateFound = emailTemplateService.findById(dto.emailTemplateId());

        CampaignEmailTemplate newCampaignEmailTemplate = CampaignEmailTemplate.builder()
                .url(dto.url())
                .campaign(campaignFound)
                .emailTemplate(emailTemplateFound)
                .build();

        repository.save(newCampaignEmailTemplate);

        rabbitEmailProducer.generateEmailQueue(newCampaignEmailTemplate.getId());

        return newCampaignEmailTemplate;
    }

    @Override
    public CampaignEmailTemplate findById(Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }
}