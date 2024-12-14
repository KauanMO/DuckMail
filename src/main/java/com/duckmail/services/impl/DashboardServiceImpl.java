package com.duckmail.services.impl;

import com.duckmail.repositories.DeliveryErrorLogRepository;
import com.duckmail.services.DashboardService;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final DeliveryErrorLogRepository deliveryErrorLogRepository;

    public DashboardServiceImpl(DeliveryErrorLogRepository deliveryErrorLogRepository) {
        this.deliveryErrorLogRepository = deliveryErrorLogRepository;
    }

    @Override
    public Long getTotalDeliveryErrors() {
        return deliveryErrorLogRepository.count();
    }
}
