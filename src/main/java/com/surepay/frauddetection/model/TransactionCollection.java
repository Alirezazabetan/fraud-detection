package com.surepay.frauddetection.model;

import com.surepay.frauddetection.service.dto.TransactionDTO;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransactionCollection {

    private final ConcurrentHashMap<Double, TransactionDTO> TransactionList = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Double, TransactionDTO> getMap() {
        return TransactionList;
    }
}
