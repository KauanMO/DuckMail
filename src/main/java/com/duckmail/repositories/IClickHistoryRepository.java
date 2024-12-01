package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.ClickHistory;

public interface IClickHistoryRepository extends JpaRepository<ClickHistory, Long> {

}
