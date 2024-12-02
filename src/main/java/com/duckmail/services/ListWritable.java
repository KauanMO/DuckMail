package com.duckmail.services;

import java.util.List;

public interface ListWritable<OUTDTO, INDTO> {
    OUTDTO createList(List<INDTO> dtoList);
}