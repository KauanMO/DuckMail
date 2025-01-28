package com.duckmail.services.impl;

import com.duckmail.TestDataFactory;
import com.duckmail.dtos.emailTemplate.InEmailTemplateDTO;
import com.duckmail.models.EmailTemplate;
import com.duckmail.repositories.EmailTemplateRepository;
import com.duckmail.services.exception.BadRequestException;
import com.duckmail.services.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailTemplateServiceTest {
    @Mock
    private EmailTemplateRepository repository;

    @InjectMocks
    private EmailTemplateServiceImpl service;

    @Test
    @DisplayName("Create email template successfully")
    void createEmailTemplateOK() {
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);

        InEmailTemplateDTO dto = new InEmailTemplateDTO(emailTemplate.getName(),
                emailTemplate.getSubject(),
                emailTemplate.getHtmlBody(),
                emailTemplate.getTextBody());

        EmailTemplate createdEmailTemplate = service.create(dto);

        assertEquals(createdEmailTemplate.getName(), emailTemplate.getName());
    }

    @Test
    @DisplayName("Create email template bad request with html and text body")
    void createEmailTemplateBADREQUEST_DoubleBody() {
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);

        InEmailTemplateDTO dto = new InEmailTemplateDTO(emailTemplate.getName(),
                emailTemplate.getSubject(),
                "HTML BODY",
                "TEXT BODY");

        assertThrows(BadRequestException.class, () -> {
            service.create(dto);
        });
    }

    @Test
    @DisplayName("Create email template bad request with null body")
    void createEmailTemplateBADREQUEST_NoBody() {
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);

        InEmailTemplateDTO dto = new InEmailTemplateDTO(emailTemplate.getName(),
                emailTemplate.getSubject(),
                null,
                null);

        assertThrows(BadRequestException.class, () -> {
            service.create(dto);
        });
    }

    @Test
    @DisplayName("Find email template by Id successfully")
    void findEmailTemplateByIdOK() {
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);

        when(repository.findById(emailTemplate.getId())).thenReturn(Optional.of(emailTemplate));

        EmailTemplate emailTemplateFound = service.findById(emailTemplate.getId());

        assertEquals(emailTemplate.getName(), emailTemplateFound.getName());
    }

    @Test
    @DisplayName("Find email template by Id not found")
    void findEmailTemplateByIdNOTFOUND() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            service.findById(2L);
        });
    }
}