package com.duckmail.services.impl;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.duckmail.TestDataFactory;
import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.enums.RecipientStatus;
import com.duckmail.models.Campaign;
import com.duckmail.models.CampaignEmailTemplate;
import com.duckmail.models.EmailTemplate;
import com.duckmail.models.Recipient;
import com.duckmail.repositories.RecipientRepository;
import com.duckmail.services.DeliveryErrorLogService;
import com.duckmail.services.RecipientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailSenderLambdaServiceTest {
    @Mock
    private AWSLambda awsLambda;
    @Mock
    private DeliveryErrorLogService deliveryErrorLogService;
    @Mock
    private RecipientService recipientService;

    @Value("${aws.lambda.mail-sender-function}")
    private String emailSenderFunction;

    @InjectMocks
    private EmailSenderLambdaService service;

    @Test
    @DisplayName("Invoke email sender lambda successfully")
    void invokeLambdaOK() throws JsonProcessingException {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, true);

        QueuedEmailDTO dto = new QueuedEmailDTO("Teste",
                "Teste",
                "teste@duckmail.com",
                recipient.getId());

        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        InvokeRequest request = new InvokeRequest()
                .withFunctionName(emailSenderFunction)
                .withPayload(dtoJson)
                .withLogType("Tail");

        when(awsLambda.invoke(request)).thenReturn(new InvokeResult()
                .withStatusCode(200));

        service.invokeLambda(dto);
    }

    @Test
    @DisplayName("Invoke email sender lambda failed")
    void invokeLambdaFAILED() throws JsonProcessingException {
        Campaign campaign = TestDataFactory.generateCampaign(1L);
        EmailTemplate emailTemplate = TestDataFactory.generateEmailTemplate(1L);
        CampaignEmailTemplate campaignEmailTemplate = TestDataFactory.generateCampaignEmailTemplate(1L, campaign, emailTemplate);
        Recipient recipient = TestDataFactory.generateRecipient(1L, campaignEmailTemplate, true);

        QueuedEmailDTO dto = new QueuedEmailDTO("Teste",
                "Teste",
                "teste@duckmail.com",
                recipient.getId());

        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        InvokeRequest request = new InvokeRequest()
                .withFunctionName(emailSenderFunction)
                .withPayload(dtoJson)
                .withLogType("Tail");

        when(awsLambda.invoke(request)).thenReturn(new InvokeResult()
                .withStatusCode(500)
                .withPayload(StandardCharsets.UTF_8.encode("Local Error")));

        service.invokeLambda(dto);
    }
}