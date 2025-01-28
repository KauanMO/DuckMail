package com.duckmail.controllers;

import com.duckmail.dtos.campaign.InCampaignPatchScheduledDateDTO;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duckmail.dtos.campaign.InCampaignDTO;
import com.duckmail.dtos.campaign.OutCampaignDTO;
import com.duckmail.models.Campaign;
import com.duckmail.services.CampaignService;

@RestController
@RequestMapping("campaign")
@RequiredArgsConstructor
public class CampaignController {
    public final CampaignService service;

    @PostMapping
    public ResponseEntity<OutCampaignDTO> postCampaign(@RequestBody InCampaignDTO dto) throws SchedulerException {
        Campaign newCampaign = service.create(dto);

        return ResponseEntity.ok(new OutCampaignDTO(newCampaign));
    }

    @PatchMapping("scheduled-date/{campaignId}")
    public ResponseEntity<OutCampaignDTO> patchCampaignScheduledDate(@PathVariable Long campaignId, @RequestBody InCampaignPatchScheduledDateDTO dto) throws SchedulerException {
        Campaign updatedCampaign = service.updateCampaignScheduledDate(campaignId, dto.newScheduledDate());

        return ResponseEntity.ok(new OutCampaignDTO(updatedCampaign));
    }
}