package com.duckmail.dtos.recipient;

import com.duckmail.models.Recipient;

import java.util.List;

public record OutValidRecipientsSegregationDTO(
        List<OutRecipientDTO> validRecipients,
        List<InRecipientDTO> invalidRecipients) {
}
