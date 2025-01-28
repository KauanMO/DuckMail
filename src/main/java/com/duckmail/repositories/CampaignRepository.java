package com.duckmail.repositories;

import com.duckmail.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.Campaign;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    List<Campaign> findByStatusIs(CampaignStatus campaignStatus);
}