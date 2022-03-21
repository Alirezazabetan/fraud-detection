package com.surepay.frauddetection;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBatchTest
@SpringBootTest
@DirtiesContext
@PropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
public class SpringBootBatchIntegrationTest {

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;

    @After
    public void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void givenFraudList_whenJobExecuted_thenSuccess() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();
        ExitStatus jobExitStatus = jobExecution.getExitStatus();

        assertThat(jobInstance.getJobName(), is("CSV-Load"));
        assertThat(jobExitStatus.getExitCode(), is("COMPLETED"));
    }

}
