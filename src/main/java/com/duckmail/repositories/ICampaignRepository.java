package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.Campaign;

public interface ICampaignRepository extends JpaRepository<Campaign, Long> { }