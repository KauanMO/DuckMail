package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.OpenHistory;

public interface OpenHistoryRepository extends JpaRepository<OpenHistory, Long> {

}