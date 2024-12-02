package com.duckmail.services.impl;

import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.dtos.recipient.OutRecipientDTO;
import com.duckmail.dtos.recipient.OutValidRecipientsSegregationDTO;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.models.Recipient;
import com.duckmail.repositories.RecipientRepository;
import com.duckmail.services.CampaignEmailTemplateService;
import com.duckmail.services.RecipientService;
import com.duckmail.services.exception.ConflictException;
import com.duckmail.services.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipientServiceImpl implements RecipientService {
    private final RecipientRepository repository;
    private final CampaignEmailTemplateService campaignEmailTemplateService;

    public RecipientServiceImpl(RecipientRepository repository, CampaignEmailTemplateService campaignEmailTemplateService) {
        this.repository = repository;
        this.campaignEmailTemplateService = campaignEmailTemplateService;
    }

    @Override
    public Recipient create(InRecipientDTO dto) throws ConflictException, NotFoundException {
        CampaignEmailTemplate campaignEmailTemplateFound = campaignEmailTemplateService.findById(dto.campaignEmailTemplateId());

        if (!UniqueRecipientInCampaignEmailTemplate(campaignEmailTemplateFound, dto.email()))
            throw new ConflictException();

        Recipient newRecipient = Recipient.builder()
                .email(dto.email())
                .campaignEmailTemplate(campaignEmailTemplateFound)
                .name(dto.name())
                .build();

        repository.save(newRecipient);

        return newRecipient;
    }

    @Override
    public OutValidRecipientsSegregationDTO createList(List<InRecipientDTO> dtos) {
        List<OutRecipientDTO> newRecipients = new ArrayList<>();
        List<InRecipientDTO> invalidRecipients = new ArrayList<>();

        for (var dto : dtos) {
            try {
                newRecipients.add(new OutRecipientDTO(this.create(dto)));
            } catch (ConflictException | NotFoundException exception) {
                invalidRecipients.add(dto);
            }
        }

        return new OutValidRecipientsSegregationDTO(newRecipients, invalidRecipients);
    }

    @Override
    public Recipient findById(Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

    private Boolean UniqueRecipientInCampaignEmailTemplate(CampaignEmailTemplate cet, String email) {
        return cet.getRecipients().stream()
                .noneMatch(recipient ->
                        recipient.getEmail()
                                .equals(email));
    }
}