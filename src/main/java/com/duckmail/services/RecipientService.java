package com.duckmail.services;

import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.dtos.recipient.OutValidRecipientsSegregationDTO;
import com.duckmail.enums.RecipientStatus;
import com.duckmail.models.Recipient;

public interface RecipientService extends Writable<Recipient, InRecipientDTO>,
        Readable<Recipient, Long>,
        ListWritable<OutValidRecipientsSegregationDTO, InRecipientDTO> {
    void changeRecipientStatus(Long id, RecipientStatus newStatus);
}