package com.duckmail.services;

import com.duckmail.services.exception.NotFoundException;

public interface Readable<ENTITY, ID> {
    ENTITY findById(ID id) throws NotFoundException;
}