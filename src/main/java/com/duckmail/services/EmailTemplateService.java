package com.duckmail.services;

import com.duckmail.dtos.emailTemplate.InEmailTemplateDTO;
import com.duckmail.models.EmailTemplate;

public interface EmailTemplateService extends Writable<EmailTemplate, InEmailTemplateDTO>,
    Readable<EmailTemplate, Long> {
    
}