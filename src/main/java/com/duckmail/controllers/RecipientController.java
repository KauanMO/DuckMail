package com.duckmail.controllers;

import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.dtos.recipient.OutRecipientDTO;
import com.duckmail.models.Recipient;
import com.duckmail.services.RecipientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}