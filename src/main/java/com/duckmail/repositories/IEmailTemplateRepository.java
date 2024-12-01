package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.EmailTemplate;

public interface IEmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

}