package com.duckmail.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duckmail.dtos.emailTemplate.InEmailTemplateDTO;
import com.duckmail.dtos.emailTemplate.OutEmailTemplateDTO;
import com.duckmail.services.EmailTemplateService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.duckmail.models.EmailTemplate;

@RestController
@RequestMapping("email-template")
public class EmailTemplateController {

    private final EmailTemplateService service;

    public EmailTemplateController(EmailTemplateService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OutEmailTemplateDTO> postEmailTemplate(@RequestBody InEmailTemplateDTO dto) throws URISyntaxException, Exception {
        EmailTemplate newEmailTemplate = service.create(dto);

        URI newEmailTemplateURI = new URI("/" + newEmailTemplate.getId());

        return ResponseEntity.created(newEmailTemplateURI).body(new OutEmailTemplateDTO(newEmailTemplate));
    }

}
