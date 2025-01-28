package com.duckmail.services.impl;

import com.duckmail.TestDataFactory;
import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.dtos.recipient.OutValidRecipientsSegregationDTO;
import com.duckmail.enums.RecipientStatus;
import com.duckmail.infra.rabbitmq.RabbitEmailProducer;
import com.duckmail.models.Campaign;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.models.EmailTemplate;
import com.duckmail.models.Recipient;
import com.duckmail.repositories.RecipientRepository;
import com.duckmail.services.CampaignEmailTemplateService;
import com.duckmail.services.exception.ConflictException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipientServiceTest {
    @Mock
    private RecipientRepository repository;

    @Mock
    private CampaignEmailTemplateService campaignEmailTemplateService;

    @Mock
    private RabbitEmailProducer rabbitEmailProducer;

    @InjectMocks
    private RecipientServiceImpl recipientService;

    @Test
    @DisplayName("Create recipient successfully")
    void createRecipientTestOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, false);

        when(campaignEmailTemplateService.findById(1L)).thenReturn(campaignEmailTemplate);

        Recipient createdRecipient = recipientService.create(new InRecipientDTO(recipient.getEmail(),
                recipient.getName(),
                recipient.getCampaignEmailTemplate().getId()));

        assertEquals(createdRecipient.getEmail(), recipient.getEmail());
        assertDoesNotThrow(ConflictException::new);
    }

    @Test
    @DisplayName("Throw conflict exception when recipient already registered on that campaign email template")
    void createRecipientTestCONFLICT() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, true);

        when(campaignEmailTemplateService.findById(1L)).thenReturn(campaignEmailTemplate);

        InRecipientDTO dto = new InRecipientDTO(recipient.getEmail(),
                recipient.getName(),
                recipient.getCampaignEmailTemplate().getId());

        assertThrows(ConflictException.class, () -> recipientService.create(dto));
    }

    @Test
    @DisplayName("Create recipient list successfully")
    void createRecipientListOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);

        when(campaignEmailTemplateService.findById(1L)).thenReturn(campaignEmailTemplate);

        List<Recipient> recipients = Arrays.asList(
                TestDataFactory.generateRecipient(1L, campaignEmailTemplate, false),
                TestDataFactory.generateRecipient(2L, campaignEmailTemplate, false)
        );

        List<InRecipientDTO> recipientDTOS = Arrays.asList(
                new InRecipientDTO(recipients.get(0).getEmail(), recipients.get(0).getName(), campaignEmailTemplate.getId()),
                new InRecipientDTO(recipients.get(1).getEmail(), recipients.get(1).getName(), campaignEmailTemplate.getId())
        );

        OutValidRecipientsSegregationDTO recipientsSegregationDTO = recipientService.createList(recipientDTOS);

        assertTrue(recipientsSegregationDTO.invalidRecipients().isEmpty());
        assertEquals(recipientsSegregationDTO.validRecipients().getFirst().email(), recipients.getFirst().getEmail());
        assertEquals(recipientsSegregationDTO.validRecipients().getLast().email(), recipients.getLast().getEmail());
    }

    @Test
    @DisplayName("Create one valid and one invalid recipient")
    void createRecipientListONECONFLICT() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);

        when(campaignEmailTemplateService.findById(1L)).thenReturn(campaignEmailTemplate);

        List<Recipient> recipients = Arrays.asList(
                TestDataFactory.generateRecipient(1L, campaignEmailTemplate, false),
                TestDataFactory.generateRecipient(2L, "recipient2@test.spring", campaignEmailTemplate, true)
        );

        List<InRecipientDTO> recipientDTOS = Arrays.asList(
                new InRecipientDTO(recipients.get(0).getEmail(), recipients.get(0).getName(), campaignEmailTemplate.getId()),
                new InRecipientDTO(recipients.get(1).getEmail(), recipients.get(1).getName(), campaignEmailTemplate.getId())
        );

        OutValidRecipientsSegregationDTO recipientsSegregationDTO = recipientService.createList(recipientDTOS);

        assertEquals(recipientsSegregationDTO.validRecipients().size(), recipientsSegregationDTO.invalidRecipients().size());
        assertEquals(recipientsSegregationDTO.validRecipients().getFirst().email(), recipients.getFirst().getEmail());
        assertEquals(recipientsSegregationDTO.invalidRecipients().getFirst().email(), recipients.getLast().getEmail());
    }

    @Test
    @DisplayName("Find Recipient by id successfully")
    void findRecipientByIdOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, true);

        when(repository.findById(1L)).thenReturn(Optional.of(recipient));

        Recipient recipientFound = recipientService.findById(recipient.getId());

        assertEquals(recipientFound.getEmail(), recipient.getEmail());
    }

    @Test
    @DisplayName("Change recipient status successfully")
    void changeRecipientStatusOK() {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, true);

        when(repository.findById(1L)).thenReturn(Optional.of(recipient));

        recipientService.changeRecipientStatus(recipient.getId(), RecipientStatus.SENT);

        assertEquals(RecipientStatus.SENT, recipient.getStatus());
        assertNotNull(recipient.getSentDate());
    }
}