package com.duckmail.services.impl;

import com.duckmail.models.DeliveryErrorLog;
import com.duckmail.repositories.DeliveryErrorLogRepository;
import com.duckmail.services.DeliveryErrorLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeliveryErrorLogServiceImpl implements DeliveryErrorLogService {
    private final DeliveryErrorLogRepository repository;

    public DeliveryErrorLogServiceImpl(DeliveryErrorLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registerError(String message, LocalDateTime datetime) {
        DeliveryErrorLog newDeliveryErrorLog = DeliveryErrorLog.builder()
                .message(message)
                .date(datetime)
                .code("500")
                .build();

        repository.save(newDeliveryErrorLog);
    }
}
