package com.duckmail.services;

import com.duckmail.services.exception.ConflictException;
import com.duckmail.services.exception.NotFoundException;
import org.quartz.SchedulerException;

public interface Writable<T, D> {
    public T create(D dto) throws ConflictException, NotFoundException, SchedulerException;
}