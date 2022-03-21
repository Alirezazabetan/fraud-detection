package com.surepay.frauddetection.service.imlp;

import com.surepay.frauddetection.repository.FraudRepository;
import com.surepay.frauddetection.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service Implementation for managing {@link Jobs}.
 */
@Service
public class JobServiceImpl implements JobService {

    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    private JobLauncher jobLauncher;
    private Job csvFormatJob;
    private Job jsonFormatJob;

    private FraudRepository fraudRepository;

    @Autowired
    public JobServiceImpl(JobLauncher jobLauncher, Job csvFormatJob, Job jsonFormatJob, FraudRepository fraudRepository) {
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

}
