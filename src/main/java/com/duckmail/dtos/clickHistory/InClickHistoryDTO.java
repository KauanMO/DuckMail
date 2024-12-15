package com.duckmail.dtos.clickHistory;

import java.time.LocalDateTime;

public record InClickHistoryDTO(String browserType,
                                String deviceType,
                                LocalDateTime date,
                                Long recipientId) {
}
