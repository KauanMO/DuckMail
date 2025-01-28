package com.duckmail.services.impl;

import com.duckmail.dtos.openHistory.InOpenHistoryDTO;
import com.duckmail.models.OpenHistory;
import com.duckmail.models.Recipient;
import com.duckmail.repositories.OpenHistoryRepository;
import com.duckmail.services.OpenHistoryService;
import com.duckmail.services.RecipientService;
import org.springframework.stereotype.Service;

@Service
public class OpenHistoryServiceImpl implements OpenHistoryService {
    private final OpenHistoryRepository repository;
    private final RecipientService recipientService;

    public OpenHistoryServiceImpl(OpenHistoryRepository repository, RecipientService recipientService) {
        this.repository = repository;
        this.recipientService = recipientService;
    }

    @Override
    public OpenHistory create(InOpenHistoryDTO dto) {
        Recipient recipientFound = recipientService.findById(dto.recipientId());

        OpenHistory openHistory = OpenHistory.builder()
                .openedDate(dto.date())
                .recipient(recipientFound)
                .build();

        return repository.save(openHistory);
    }
}