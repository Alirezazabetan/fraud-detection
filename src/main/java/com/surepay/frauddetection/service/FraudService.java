package com.surepay.frauddetection.service;

import com.surepay.frauddetection.model.Fraud;

import java.util.List;

/**
 * Service Interface for managing {@link com.surepay.frauddetection.model.Fraud}.
 */
public interface FraudService {

    /**
     * Get all the frauds.
     *
     * @return the list of entities.
     */
    List<Fraud> findAll();
}
