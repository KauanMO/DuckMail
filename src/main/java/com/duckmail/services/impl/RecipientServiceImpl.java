package com.duckmail.services.impl;

import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.dtos.recipient.OutRecipientDTO;
import com.duckmail.dtos.recipient.OutValidRecipientsSegregationDTO;
import com.duckmail.enums.RecipientStatus;
import com.duckmail.infra.rabbitmq.RabbitEmailProducer;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.models.Recipient;
import com.duckmail.repositories.RecipientRepository;
import com.duckmail.services.CampaignEmailTemplateService;
import com.duckmail.services.RecipientService;
import com.duckmail.services.exception.ConflictException;
import com.duckmail.services.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipientServiceImpl implements RecipientService {
    private final RecipientRepository repository;
    private final CampaignEmailTemplateService campaignEmailTemplateService;
    private final RabbitEmailProducer rabbitEmailProducer;

    public RecipientServiceImpl(RecipientRepository repository, CampaignEmailTemplateService campaignEmailTemplateService, RabbitEmailProducer rabbitEmailProducer) {
        this.repository = repository;
        this.campaignEmailTemplateService = campaignEmailTemplateService;
        this.rabbitEmailProducer = rabbitEmailProducer;
    }

    @Override
    public Recipient create(InRecipientDTO dto) throws ConflictException, NotFoundException {
        CampaignEmailTemplate campaignEmailTemplateFound = campaignEmailTemplateService.findById(dto.campaignEmailTemplateId());

        if (Boolean.FALSE.equals(uniqueRecipientInCampaignEmailTemplate(campaignEmailTemplateFound, dto.email())))
            throw new ConflictException();

        Recipient newRecipient = Recipient.builder()
                .email(dto.email())
                .campaignEmailTemplate(campaignEmailTemplateFound)
                .name(dto.name())
                .build();

        repository.save(newRecipient);

        rabbitEmailProducer.enqueueRecipient(newRecipient);

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

    @Override
    public void changeRecipientStatus(Long id, RecipientStatus newStatus) {
        Recipient recipientFound = repository.findById(id).orElseThrow(NotFoundException::new);

        recipientFound.setStatus(newStatus);
        if (newStatus.equals(RecipientStatus.SENT))
            recipientFound.setSentDate(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());

        repository.save(recipientFound);
    }

    private Boolean uniqueRecipientInCampaignEmailTemplate(CampaignEmailTemplate cet, String email) {
        return cet.getRecipients().stream()
                .noneMatch(recipient ->
                        recipient.getEmail()
                                .equals(email));
    }
}