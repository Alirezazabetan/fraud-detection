package com.surepay.frauddetection.listener;

import com.surepay.frauddetection.model.TransactionCollection;
import com.surepay.frauddetection.repository.FraudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private TransactionCollection transactionCollection;

    private FraudRepository fraudRepository;

    @Autowired
    public JobCompletionNotificationListener(TransactionCollection transactionCollection, FraudRepository fraudRepository) {

        this.transactionCollection = transactionCollection;
        this.fraudRepository = fraudRepository;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LOGGER.info("!!! JOB FINISHED! Time to verify the results");
        transactionCollection.getMap().forEach((k,v) -> LOGGER.info("All transactions keys are = " + k));
        transactionCollection.getMap().clear();
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            fraudRepository.findAll().forEach(transaction -> LOGGER.info("Found fraud < {} > in the database.", transaction));
        }
    }
}
