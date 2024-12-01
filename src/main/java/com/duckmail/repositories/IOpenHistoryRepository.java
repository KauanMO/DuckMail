package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.OpenHistory;

public interface IOpenHistoryRepository extends JpaRepository<OpenHistory, Long> {

}