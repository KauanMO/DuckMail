package com.duckmail.dtos.openHistory;

import java.time.LocalDateTime;

public record InOpenHistoryDTO(LocalDateTime date,
                               Long recipientId) {
}