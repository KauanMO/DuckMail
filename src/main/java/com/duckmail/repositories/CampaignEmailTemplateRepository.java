package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.CampaignEmailTemplate;

public interface CampaignEmailTemplateRepository extends JpaRepository<CampaignEmailTemplate, Long> {
}