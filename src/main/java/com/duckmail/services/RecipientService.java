package com.duckmail.services;

import com.duckmail.dtos.recipient.InRecipientDTO;
import com.duckmail.models.Recipient;

public interface RecipientService extends Writable<Recipient, InRecipientDTO>,
        Readable<Recipient, Long> {

}