package com.duckmail.services.impl;

import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.models.Recipient;
import com.duckmail.repositories.RecipientRepository;
import com.duckmail.services.CampaignEmailTemplateService;
import com.duckmail.services.RecipientService;
import org.springframework.stereotype.Service;

@Service
public class RecipientServiceImpl implements RecipientService {
    private final RecipientRepository repository;
    private final CampaignEmailTemplateService campaignEmailTemplateService;

    public RecipientServiceImpl(RecipientRepository repository, CampaignEmailTemplateService campaignEmailTemplateService) {
        this.repository = repository;
        this.campaignEmailTemplateService = campaignEmailTemplateService;
    }

    @Override
    public Recipient create(InRecipientDTO dto) throws Exception {
        CampaignEmailTemplate campaignEmailTemplateFound = campaignEmailTemplateService.findById(dto.campaignEmailTemplateId());

        Recipient newRecipient = Recipient.builder()
                .email(dto.email())
                .name(dto.name())
                .campaignEmailTemplate(campaignEmailTemplateFound)
                .build();

        repository.save(newRecipient);

        return newRecipient;
    }

    @Override
    public Recipient findById(Long id) throws Exception {
        return repository
                .findById(id)
                .orElseThrow(Exception::new);
    }
}