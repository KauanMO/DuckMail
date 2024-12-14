package com.duckmail.controllers;

import com.duckmail.dtos.dashboard.OutTotalClickHistoriesDTO;
import com.duckmail.dtos.dashboard.OutTotalDeliveryErrorsDTO;
import com.duckmail.dtos.dashboard.OutTotalOpenHistoriesDTO;
import com.duckmail.services.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dashboard")
public class DashboardController {
    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("total-delivery-errors")
    public ResponseEntity<OutTotalDeliveryErrorsDTO> getTotalDeliveryErrors() {
        Long totalDeliveryErrors = service.getTotalDeliveryErrors();

        return ResponseEntity.ok(new OutTotalDeliveryErrorsDTO(totalDeliveryErrors));
    }

    @GetMapping("total-clicks")
    public ResponseEntity<OutTotalClickHistoriesDTO> getTotalClicks() {
        Long totalClickHistories = service.getTotalClickHistories();

        return ResponseEntity.ok(new OutTotalClickHistoriesDTO(totalClickHistories));
    }

    @GetMapping("total-opens")
    public ResponseEntity<OutTotalOpenHistoriesDTO> getTotalOpens() {
        Long totalOpenHistories = service.getTotalOpenHistories();

        return ResponseEntity.ok(new OutTotalOpenHistoriesDTO(totalOpenHistories));
    }
}