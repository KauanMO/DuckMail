package com.duckmail.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.duckmail.enums.CampaignStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private CampaignStatus status = CampaignStatus.PENDING;

    private final LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime scheduledDate;

    private int sentCount;
    private int totalCount;

    @OneToMany(mappedBy = "campaign", fetch = FetchType.EAGER)
    private final List<CampaignEmailTemplate> campaignEmailTemplates = new ArrayList<>();
}