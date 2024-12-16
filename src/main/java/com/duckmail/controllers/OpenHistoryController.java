package com.duckmail.controllers;

import com.duckmail.dtos.openHistory.InOpenHistoryDTO;
import com.duckmail.services.OpenHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("open")
public class OpenHistoryController {
    private final OpenHistoryService openHistoryService;

    public OpenHistoryController(OpenHistoryService openHistoryService) {
        this.openHistoryService = openHistoryService;
    }

    @GetMapping("register")
    public ResponseEntity<?> createOpen(@RequestParam LocalDateTime openedDate, @RequestParam Long recipientId) throws Exception {
        openHistoryService.create(new InOpenHistoryDTO(openedDate, recipientId));

        return ResponseEntity.ok().build();
    }
}