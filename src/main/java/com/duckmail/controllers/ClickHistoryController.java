package com.duckmail.controllers;

import com.duckmail.dtos.clickHistory.InClickHistoryDTO;
import com.duckmail.models.ClickHistory;
import com.duckmail.services.ClickHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("click")
public class ClickHistoryController {
    private final ClickHistoryService service;

    public ClickHistoryController(ClickHistoryService service) {
        this.service = service;
    }

    @GetMapping("register")
    public ResponseEntity<?> createClickHistory(@RequestParam String browserType, @RequestParam String deviceType, @RequestParam Long recipientId) throws Exception {
        ClickHistory newClickHistory = service.create(new InClickHistoryDTO(browserType, deviceType, LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime(), recipientId));

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(new URI(newClickHistory
                        .getRecipient()
                        .getCampaignEmailTemplate()
                        .getUrl()))
                .build();
    }
}