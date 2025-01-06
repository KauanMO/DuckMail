package com.duckmail.services.impl;

import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.duckmail.dtos.email.QueuedEmailDTO;
import com.duckmail.services.LambdaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailSenderLambdaService implements LambdaService<QueuedEmailDTO> {
    private final AWSLambda awsLambda;

    @Value("${aws.lambda.mail-sender-function}")
    private String emailSenderFunction;

    public EmailSenderLambdaService(AWSLambda awsLambda) {
        this.awsLambda = awsLambda;
    }

    @Override
    public ResponseEntity<String> invokeLambda(QueuedEmailDTO dto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String dtoJson = mapper.writeValueAsString(dto);

        InvokeRequest request = new InvokeRequest()
                .withFunctionName(emailSenderFunction)
                .withPayload(dtoJson)
                .withLogType("Tail");

        InvokeResult result = awsLambda.invoke(request);

        return ResponseEntity.status(result.getStatusCode()).body(StandardCharsets.UTF_8.decode(result.getPayload()).toString());
    }
}
