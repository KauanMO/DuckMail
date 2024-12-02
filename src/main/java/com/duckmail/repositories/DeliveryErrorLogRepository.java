package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.DeliveryErrorLog;

public interface DeliveryErrorLogRepository extends JpaRepository<DeliveryErrorLog, Long> {

}
