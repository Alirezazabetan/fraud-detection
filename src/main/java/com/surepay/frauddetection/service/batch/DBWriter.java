package com.surepay.frauddetection.service.batch;

import com.surepay.frauddetection.repository.FraudRepository;
import com.surepay.frauddetection.service.dto.TransactionDTO;
import com.surepay.frauddetection.service.mapper.FraudMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<TransactionDTO> {

    private FraudRepository fraudRepository;
    private FraudMapper fraudMapper;

    @Autowired
    public DBWriter(FraudRepository fraudRepository, FraudMapper fraudMapper) {
        this.fraudRepository = fraudRepository;
        this.fraudMapper = fraudMapper;
    }

    @Override
    public void write(List<? extends TransactionDTO> frauds) throws Exception{
        System.out.println("Data Saved for Frauds: " + frauds);
        fraudRepository.saveAll(fraudMapper.toEntity(frauds));
    }
}
