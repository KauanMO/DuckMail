package com.duckmail.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    public ResponseEntity<OutCampaignDTO> postCampaign(@RequestBody InCampaignDTO dto) throws URISyntaxException {
        Campaign newCampaign = service.create(dto);

        URI newCampaignURL = new URI("/" + newCampaign.getId());

        return ResponseEntity.created(newCampaignURL).body(new OutCampaignDTO(newCampaign));
    }
}