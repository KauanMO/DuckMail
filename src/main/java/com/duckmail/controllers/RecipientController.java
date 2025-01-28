package com.duckmail.controllers;

import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.dtos.recipient.OutRecipientDTO;
import com.duckmail.dtos.recipient.OutValidRecipientsSegregationDTO;
import com.duckmail.models.Recipient;
import com.duckmail.services.RecipientService;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recipient")
public class RecipientController {
    private final RecipientService service;

    public RecipientController(RecipientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OutRecipientDTO> postRecipient(@RequestBody InRecipientDTO dto) throws SchedulerException {
        Recipient newRecipient = service.create(dto);

        return ResponseEntity.ok(new OutRecipientDTO(newRecipient));
    }

    @PostMapping("list")
    public ResponseEntity<OutValidRecipientsSegregationDTO> listRecipient(@RequestBody List<InRecipientDTO> dtos) {
        var validRecipientsSegregation = service.createList(dtos);

        return ResponseEntity.ok(validRecipientsSegregation);
    }
}