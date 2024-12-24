package com.duckmail.services;

import java.util.List;

public interface ListWritable<O, I> {
    O createList(List<I> dtoList);
}