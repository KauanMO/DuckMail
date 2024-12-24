package com.duckmail.services;

import com.duckmail.services.exception.NotFoundException;

public interface Readable<T, I> {
    T findById(I id) throws NotFoundException;
}