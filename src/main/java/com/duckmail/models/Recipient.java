package com.duckmail.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.duckmail.enums.RecipientStatus;

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
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;

    @Enumerated(EnumType.STRING)
    private final RecipientStatus status = RecipientStatus.PENDING;

    private LocalDateTime sentDate;
    private final LocalDateTime createdDate = LocalDateTime.now();

    @ManyToOne
    private CampaignEmailTemplate campaignEmailTemplate;

    @OneToMany(mappedBy = "recipient")
    private List<OpenHistory> openHistories = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<ClickHistory> clickHistories = new ArrayList<>();

    @OneToOne(mappedBy = "recipient")
    private DeliveryErrorLog deliveryErrorLogs;
}