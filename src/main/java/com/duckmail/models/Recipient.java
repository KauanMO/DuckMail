package com.duckmail.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.duckmail.enums.RecipientStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;

    @Enumerated(EnumType.STRING)
    private RecipientStatus status;

    private LocalDateTime sentDate;
    private LocalDateTime createdDate;

    @ManyToOne
    private CampaignEmailTemplate campaignEmailTemplate;

    @OneToMany(mappedBy = "recipient")
    private List<OpenHistory> openHistories = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<ClickHistory> clickHistories = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<DeliveryErrorLog> deliveryErrorLogs = new ArrayList<>();
}
