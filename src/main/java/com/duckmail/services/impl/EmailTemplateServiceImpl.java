package com.duckmail.services.impl;

import com.duckmail.services.exception.BadRequestException;
import com.duckmail.services.exception.NotFoundException;
import org.springframework.stereotype.Service;

import com.duckmail.dtos.emailTemplate.InEmailTemplateDTO;
import com.duckmail.models.EmailTemplate;
import com.duckmail.repositories.EmailTemplateRepository;
import com.duckmail.services.EmailTemplateService;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {
    private final EmailTemplateRepository repository;

    public EmailTemplateServiceImpl(EmailTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmailTemplate create(InEmailTemplateDTO dto) {
        if ((dto.htmlBody() == null && dto.textBody() == null) || (dto.htmlBody() != null && dto.textBody() != null))
            throw new BadRequestException("Atribua apenas um corpo html OU de texto ao template de email (Implementar global handler de exceções)");

        EmailTemplate newEmailTemplate = EmailTemplate.builder()
                .name(dto.name())
                .subject(dto.subject())
                .htmlBody(dto.htmlBody())
                .textBody(dto.textBody())
                .build();

        repository.save(newEmailTemplate);
        return newEmailTemplate;
    }

    @Override
    public EmailTemplate findById(Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }
}