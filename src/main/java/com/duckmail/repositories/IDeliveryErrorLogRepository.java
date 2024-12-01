package com.duckmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckmail.models.DeliveryErrorLog;

public interface IDeliveryErrorLogRepository extends JpaRepository<DeliveryErrorLog, Long> {

}
