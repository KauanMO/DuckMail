package com.duckmail.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface LambdaService<T> {
    ResponseEntity<String> invokeLambda(T dto) throws JsonProcessingException;
}