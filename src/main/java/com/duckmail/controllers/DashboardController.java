package com.duckmail.controllers;

import com.duckmail.dtos.dashboard.OutTotalDeliveryErrorsDTO;
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
}