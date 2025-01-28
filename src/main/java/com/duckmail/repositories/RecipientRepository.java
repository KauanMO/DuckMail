package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.Recipient;

public interface RecipientRepository extends JpaRepository<Recipient, Long> {
}