package com.surepay.frauddetection.service.mapper;

import com.surepay.frauddetection.model.Fraud;
import com.surepay.frauddetection.service.dto.TransactionDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FraudMapperImpl implements FraudMapper {

    @Override
    public List<Fraud> toEntity(List<? extends TransactionDTO> frauds) {
        List<Fraud> list = new ArrayList<>();
        for (TransactionDTO trans : frauds) {
            Fraud fraud = new Fraud();
            fraud.setReference(trans.getReference());
            fraud.setDescription(trans.getDescription());
            list.add(fraud);
            if (trans.getTransactionDTO() != null) {
                Fraud secondFraud = new Fraud();
                secondFraud.setReference(trans.getTransactionDTO().getReference());
                secondFraud.setDescription(trans.getTransactionDTO().getDescription());
                list.add(secondFraud);
            }
        }
        return list;
    }

}