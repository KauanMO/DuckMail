package com.duckmail.services;

public interface Writable<ENTITY, DTO> {
    public ENTITY create(DTO dto);
}