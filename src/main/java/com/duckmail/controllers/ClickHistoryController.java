package com.duckmail.controllers;

import com.duckmail.dtos.clickHistory.InClickHistoryDTO;
import com.duckmail.services.ClickHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("click")
public class ClickHistoryController {
    private final ClickHistoryService service;

    public ClickHistoryController(ClickHistoryService service) {
        this.service = service;
    }

    @GetMapping("register")
    public ResponseEntity<?> createClickHistory(@RequestParam String browserType, @RequestParam String deviceType, @RequestParam LocalDateTime date, @RequestParam Long recipientId) throws Exception {
        service.create(new InClickHistoryDTO(browserType, deviceType, date, recipientId));

        return ResponseEntity.ok().build();
    }
}