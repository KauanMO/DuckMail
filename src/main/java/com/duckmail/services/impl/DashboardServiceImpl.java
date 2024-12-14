package com.duckmail.services.impl;

import com.duckmail.repositories.ClickHistoryRepository;
import com.duckmail.repositories.DeliveryErrorLogRepository;
import com.duckmail.services.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final DeliveryErrorLogRepository deliveryErrorLogRepository;
    private final ClickHistoryRepository clickHistoryRepository;

    public DashboardServiceImpl(DeliveryErrorLogRepository deliveryErrorLogRepository, ClickHistoryRepository clickHistoryRepository) {
        this.deliveryErrorLogRepository = deliveryErrorLogRepository;
        this.clickHistoryRepository = clickHistoryRepository;
    }

    @Override
    public Long getTotalDeliveryErrors() {
        return deliveryErrorLogRepository.count();
    }

    @Override
    public Long getTotalClickHistories() {
        return clickHistoryRepository.count();
    }
}
