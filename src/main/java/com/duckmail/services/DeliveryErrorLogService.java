package com.duckmail.services;

import java.time.LocalDateTime;

public interface DeliveryErrorLogService {
    void registerError(String message, LocalDateTime datetime, Long recipientId);
}
