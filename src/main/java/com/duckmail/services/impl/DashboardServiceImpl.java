package com.duckmail.services.impl;

import com.duckmail.repositories.ClickHistoryRepository;
import com.duckmail.repositories.DeliveryErrorLogRepository;
import com.duckmail.repositories.OpenHistoryRepository;
import com.duckmail.services.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final DeliveryErrorLogRepository deliveryErrorLogRepository;
    private final ClickHistoryRepository clickHistoryRepository;
    private final OpenHistoryRepository openHistoryRepository;

    public DashboardServiceImpl(DeliveryErrorLogRepository deliveryErrorLogRepository, ClickHistoryRepository clickHistoryRepository, OpenHistoryRepository openHistoryRepository) {
        this.deliveryErrorLogRepository = deliveryErrorLogRepository;
        this.clickHistoryRepository = clickHistoryRepository;
        this.openHistoryRepository = openHistoryRepository;
    }

    @Override
    public Long getTotalDeliveryErrors() {
        return deliveryErrorLogRepository.count();
    }

    @Override
    public Long getTotalClickHistories() {
        return clickHistoryRepository.count();
    }

    @Override
    public Long getTotalOpenHistories() {
        return openHistoryRepository.count();
    }
}
