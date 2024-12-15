package com.duckmail.services.impl;

import com.duckmail.dtos.clickHistory.InClickHistoryDTO;
import com.duckmail.models.ClickHistory;
import com.duckmail.models.Recipient;
import com.duckmail.repositories.ClickHistoryRepository;
import com.duckmail.services.ClickHistoryService;
import com.duckmail.services.RecipientService;
import org.springframework.stereotype.Service;

@Service
public class ClickHistoryServiceImpl implements ClickHistoryService {
    private final ClickHistoryRepository repository;
    private final RecipientService recipientService;

    public ClickHistoryServiceImpl(ClickHistoryRepository repository, RecipientService recipientService) {
        this.repository = repository;
        this.recipientService = recipientService;
    }

    @Override
    public ClickHistory create(InClickHistoryDTO dto) {
        Recipient recipientFound = recipientService.findById(dto.recipientId());

        ClickHistory newClickHistory = ClickHistory.builder()
                .deviceType(dto.deviceType())
                .date(dto.date())
                .browserType(dto.browserType())
                .recipient(recipientFound)
                .build();

        repository.save(newClickHistory);
        return newClickHistory;
    }
}
