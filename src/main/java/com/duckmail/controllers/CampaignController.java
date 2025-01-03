package com.duckmail.controllers;

import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.dtos.campaign.OutCampaignDTO;
import com.duckmail.models.Campaign;
import com.duckmail.services.CampaignService;

@RestController
@RequestMapping("campaign")
public class CampaignController {
    public final CampaignService service;

    public CampaignController(CampaignService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OutCampaignDTO> postCampaign(@RequestBody InCampaignDTO dto) throws SchedulerException {
        Campaign newCampaign = service.create(dto);

        return ResponseEntity.ok(new OutCampaignDTO(newCampaign));
    }
}