package com.surepay.frauddetection.repository;

import com.surepay.frauddetection.model.Fraud;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data SQL repository for the Fraud entity.
 */
public interface FraudRepository extends JpaRepository<Fraud, Integer> {
}
