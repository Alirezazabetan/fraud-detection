package com.surepay.frauddetection.service.imlp;

import com.surepay.frauddetection.model.Fraud;
import com.surepay.frauddetection.repository.FraudRepository;
import com.surepay.frauddetection.service.FraudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service Implementation for managing {@link Fraud}.
 */
@Service
public class FraudServiceImpl implements FraudService {

    private final Logger log = LoggerFactory.getLogger(FraudServiceImpl.class);

    private JobLauncher jobLauncher;
    private Job csvFormatJob;
    private Job jsonFormatJob;

    private FraudRepository fraudRepository;

    @Autowired
    public FraudServiceImpl(JobLauncher jobLauncher, Job csvFormatJob, Job jsonFormatJob, FraudRepository fraudRepository) {
        this.jobLauncher = jobLauncher;
        this.csvFormatJob = csvFormatJob;
        this.jsonFormatJob = jsonFormatJob;
        this.fraudRepository = fraudRepository;
    }


    public BatchStatus runJsonBatch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

            Map<String, JobParameter> maps = new HashMap<>();
            maps.put("time", new JobParameter(System.currentTimeMillis()));
            JobParameters parameters = new JobParameters(maps);
            JobExecution jobExecution = jobLauncher.run(jsonFormatJob, parameters);

            System.out.println("Json JobExecution: " + jobExecution.getStatus());

            System.out.println("Json Batch is Running...");
            while (jobExecution.isRunning()) {
                System.out.println("...");
            }

            return jobExecution.getStatus();
    }

    public BatchStatus runCSVBatch() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(csvFormatJob, parameters);

        System.out.println("JobExecution: " + jobExecution.getStatus());

        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }
        return jobExecution.getStatus();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Fraud> findAll() {
        log.debug("Request to get all Stocks");
        return fraudRepository.findAll();
    }

}
