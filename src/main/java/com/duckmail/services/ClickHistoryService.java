package com.duckmail.services;

import com.duckmail.dtos.clickHistory.InClickHistoryDTO;
import com.duckmail.models.ClickHistory;

public interface ClickHistoryService extends Writable<ClickHistory, InClickHistoryDTO> {
}