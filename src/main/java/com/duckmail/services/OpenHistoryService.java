package com.duckmail.services;

import com.duckmail.dtos.openHistory.InOpenHistoryDTO;
import com.duckmail.models.OpenHistory;

public interface OpenHistoryService extends Writable<OpenHistory, InOpenHistoryDTO> {
}