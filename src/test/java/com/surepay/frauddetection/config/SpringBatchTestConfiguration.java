package com.surepay.frauddetection.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBatchTestConfiguration
{
@Bean
public static JobLauncherTestUtils jobLauncherTestUtilsForSnapshot()
{
    return new SnapshotJobLauncherTestUtils();
}

public static class SnapshotJobLauncherTestUtils extends JobLauncherTestUtils
{
    @Override
    @Autowired
    public void setJob(@Qualifier("csvFormatJob") Job job )
    {
        super.setJob( job );
    }
}
}