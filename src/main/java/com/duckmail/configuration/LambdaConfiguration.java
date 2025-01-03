package com.duckmail.configuration;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LambdaConfiguration {
    @Value("${aws.lambda.endpoint}")
    private String awsEndpoint;

    @Value("${aws.lambda.credentials.access-key}")
    private String awsAccessKey;

    @Value("${aws.lambda.credentials.secret-key}")
    private String awsSecretKey;

    @Bean
    public AWSLambda awsLambda() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTP);

        return AWSLambdaClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withEndpointConfiguration(
                        new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(awsEndpoint, "us-east-1"))
                .withClientConfiguration(clientConfiguration)
                .build();
    }
}
