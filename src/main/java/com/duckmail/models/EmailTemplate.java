package com.duckmail.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String htmlBody;

    @Column(columnDefinition = "TEXT")
    private String textBody;

    private final LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "emailTemplate")
    private final List<CampaignEmailTemplate> campaignEmailTemplates = new ArrayList<>();
}