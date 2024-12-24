package com.duckmail.controllers;

import com.duckmail.dtos.dashboard.OutTotalOpenHistoriesDTO;
import com.duckmail.dtos.openHistory.InOpenHistoryDTO;
import com.duckmail.services.OpenHistoryService;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;


@RestController
@RequestMapping("open")
public class OpenHistoryController {
    private final OpenHistoryService openHistoryService;

    public OpenHistoryController(OpenHistoryService openHistoryService) {
        this.openHistoryService = openHistoryService;
    }

    @GetMapping("register")
    public ResponseEntity<OutTotalOpenHistoriesDTO> createOpen(@RequestParam Long recipientId) throws SchedulerException {
        openHistoryService.create(new InOpenHistoryDTO(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime(), recipientId));

        return ResponseEntity.ok().build();
    }
}