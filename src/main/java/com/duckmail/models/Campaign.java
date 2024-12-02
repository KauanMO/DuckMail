package com.duckmail.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.duckmail.enums.CampaignStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private final CampaignStatus status = CampaignStatus.PENDING;

    private final LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime scheduledDate;

    private int sentCount;
    private int totalCount;

    @OneToMany(mappedBy = "campaign")
    private final List<CampaignEmailTemplate> campaignEmailTemplates = new ArrayList<>();
}