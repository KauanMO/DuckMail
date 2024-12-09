package com.duckmail.controllers;

import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.dtos.recipient.OutRecipientDTO;
import com.duckmail.dtos.recipient.OutValidRecipientsSegregationDTO;
import com.duckmail.infra.email.EmailSenderService;
import com.duckmail.models.Recipient;
import com.duckmail.services.RecipientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("recipient")
public class RecipientController {
    private final RecipientService service;

    public RecipientController(RecipientService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OutRecipientDTO> postRecipient(@RequestBody InRecipientDTO dto) throws Exception {
        Recipient newRecipient = service.create(dto);

        URI newRecipientURI = new URI("/" + newRecipient.getId());

        return ResponseEntity.created(newRecipientURI).body(new OutRecipientDTO(newRecipient));
    }

    @PostMapping("list")
    public ResponseEntity<OutValidRecipientsSegregationDTO> listRecipient(@RequestBody List<InRecipientDTO> dtos) throws Exception {
        var validRecipientsSegregation = service.createList(dtos);

        return ResponseEntity.ok(validRecipientsSegregation);
    }
}