package com.surepay.frauddetection.config;


import com.surepay.frauddetection.listener.JobCompletionNotificationListener;
import com.surepay.frauddetection.service.dto.TransactionDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Bean("csvFormatJob")
    @Primary
    public Job csvFormatJob(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<TransactionDTO> csvReader,
                   ItemProcessor<TransactionDTO, TransactionDTO> itemProcessor,
                   ItemWriter<TransactionDTO> itemWriter,
                   JobCompletionNotificationListener listener
    ) {

        Step step = stepBuilderFactory.get("CSV-step-load")
                .<TransactionDTO, TransactionDTO>chunk(100)
                .reader(csvReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();


        return jobBuilderFactory.get("CSV-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .listener(listener)
                .build();
    }

    @Bean("jsonFormatJob")
    public Job jsonFormatJob(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<TransactionDTO> jsonReader,
                   ItemProcessor<TransactionDTO, TransactionDTO> itemProcessor,
                   ItemWriter<TransactionDTO> itemWriter,
                   JobCompletionNotificationListener listener
    ) {

        Step step = stepBuilderFactory.get("Json-step-load")
                .<TransactionDTO, TransactionDTO>chunk(100)
                .reader(jsonReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();


        return jobBuilderFactory.get("Json-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .listener(listener)
                .build();
    }


}
