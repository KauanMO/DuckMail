package com.duckmail.services.impl;

import com.duckmail.models.DeliveryErrorLog;
import com.duckmail.models.Recipient;
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
    public void registerError(String message, LocalDateTime datetime, Long recipientId) {

        DeliveryErrorLog newDeliveryErrorLog = DeliveryErrorLog.builder()
                .message(message)
                .date(datetime)
                .recipient(Recipient.builder().id(recipientId).build())
                .code("500")
                .build();

        repository.save(newDeliveryErrorLog);
    }
}
