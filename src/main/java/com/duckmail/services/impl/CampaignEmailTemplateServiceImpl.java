package com.duckmail.services.impl;

import com.duckmail.infra.email.EmailSenderJob;
import com.duckmail.infra.rabbitmq.EmailQueueListenerService;
import com.duckmail.infra.rabbitmq.RabbitEmailConsumer;
import com.duckmail.infra.rabbitmq.RabbitEmailProducer;
import com.duckmail.services.exception.NotFoundException;
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
public class CampaignEmailTemplateServiceImpl implements CampaignEmailTemplateService {
    private final CampaignEmailTemplateRepository repository;
    private final CampaignService campaignService;
    private final EmailTemplateService emailTemplateService;
    private final RabbitEmailProducer rabbitEmailProducer;
    private final Scheduler scheduler;

    public CampaignEmailTemplateServiceImpl(CampaignService campaignService,
                                            EmailTemplateService emailTemplateService,
                                            CampaignEmailTemplateRepository repository, RabbitEmailProducer rabbitEmailProducer, Scheduler scheduler) {
        this.campaignService = campaignService;
        this.emailTemplateService = emailTemplateService;
        this.repository = repository;
        this.rabbitEmailProducer = rabbitEmailProducer;
        this.scheduler = scheduler;
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

        rabbitEmailProducer.generateEmailQueue(newCampaignEmailTemplate.getId());

        scheduleCampaignEmailTemplate(newCampaignEmailTemplate);

        return newCampaignEmailTemplate;
    }

    private void scheduleCampaignEmailTemplate(CampaignEmailTemplate campaignEmailTemplate) throws SchedulerException {
        LocalDateTime campaignScheduledDate = campaignEmailTemplate.getCampaign().getScheduledDate();

        JobDetail jobDetail = JobBuilder.newJob(EmailSenderJob.class)
                .withIdentity("Campaign-email-template-job-" + campaignEmailTemplate.getId())
                .usingJobData("campaignEmailTemplateId", campaignEmailTemplate.getId())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("Campaign-email-template-trigger-" + campaignEmailTemplate.getId())
                .startAt(Date.from(campaignScheduledDate.atZone(ZoneId.of("America/Sao_Paulo")).toInstant()))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    @Override
    public CampaignEmailTemplate findById(Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }
}