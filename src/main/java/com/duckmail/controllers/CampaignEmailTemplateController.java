package com.duckmail.controllers;

import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.duckmail.dtos.campaignEmailTemplate.InCampaignEmailTemplateDTO;
import com.duckmail.dtos.campaignEmailTemplate.OutCampaignEmailTemplateDTO;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.services.CampaignEmailTemplateService;


@RestController
@RequestMapping("campaign-email-template")
public class CampaignEmailTemplateController {
    private final CampaignEmailTemplateService service;

    public CampaignEmailTemplateController(CampaignEmailTemplateService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<OutCampaignEmailTemplateDTO> postCampaignEmailTemplate(@RequestBody InCampaignEmailTemplateDTO dto) throws SchedulerException {
        CampaignEmailTemplate newCampaignEmailTemplate = service.create(dto);

        return ResponseEntity.ok(new OutCampaignEmailTemplateDTO(newCampaignEmailTemplate));
    }
}