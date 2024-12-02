package com.duckmail.services;

public interface IWritable<ENTITY, DTO> {
    public ENTITY create(DTO dto);
}