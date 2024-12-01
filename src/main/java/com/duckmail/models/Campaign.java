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
import lombok.Data;

@Data
@Entity
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status;

    private LocalDateTime createdDate;
    private LocalDateTime scheduledDate;

    private int sentCount;
    private int totalCount;

    @OneToMany(mappedBy = "campaign")
    private List<CampaignEmailTemplate> campaignEmailTemplates = new ArrayList<>();
}
