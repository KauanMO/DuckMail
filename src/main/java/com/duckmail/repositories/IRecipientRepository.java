package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.Recipient;

public interface IRecipientRepository extends JpaRepository<Recipient, Long> {
}