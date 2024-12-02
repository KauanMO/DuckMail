package com.duckmail.services;

public interface Readable<ENTITY, ID> {
    ENTITY findById(ID id) throws Exception;
}