package com.surepay.frauddetection.service.mapper;

import com.surepay.frauddetection.model.Fraud;
import com.surepay.frauddetection.service.dto.TransactionDTO;

import java.util.List;

/**
 * Mapper for the entity {@link Fraud} and its DTO {@link TransactionDTO}.
 */
public interface FraudMapper {
    List<Fraud> toEntity (List<? extends TransactionDTO> frauds);
}
