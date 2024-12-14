package com.duckmail.services;

public interface DashboardService {
    Long getTotalDeliveryErrors();
    Long getTotalClickHistories();
    Long getTotalOpenHistories();
    Long getTotalPendingCampaigns();
}