package com.duckmail.services.impl;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.enums.RecipientStatus;
import com.duckmail.services.DeliveryErrorLogService;
import com.duckmail.services.LambdaService;
import com.duckmail.services.RecipientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class EmailSenderLambdaService implements LambdaService<QueuedEmailDTO> {
    private final AWSLambda awsLambda;
    private final DeliveryErrorLogService deliveryErrorLogService;
    private final RecipientService recipientService;

    @Value("${aws.lambda.mail-sender-function}")
    private String emailSenderFunction;

    public EmailSenderLambdaService(AWSLambda awsLambda, DeliveryErrorLogService deliveryErrorLogService, RecipientService recipientService) {
        this.awsLambda = awsLambda;
        this.deliveryErrorLogService = deliveryErrorLogService;
        this.recipientService = recipientService;
    }

    @Override
    public void invokeLambda(QueuedEmailDTO dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        InvokeRequest request = new InvokeRequest()
                .withFunctionName(emailSenderFunction)
                .withPayload(dtoJson)
                .withLogType("Tail");

        InvokeResult result = awsLambda.invoke(request);

        if (result.getStatusCode().equals(HttpStatus.OK.value())) {
            recipientService.changeRecipientStatus(dto.recipientId(), RecipientStatus.SENT);
        } else {
            recipientService.changeRecipientStatus(dto.recipientId(), RecipientStatus.FAILED);
            deliveryErrorLogService.registerError(StandardCharsets.UTF_8.decode(result.getPayload()).toString(),
                    LocalDateTime.now(),
                    dto.recipientId());
        }
    }
}
