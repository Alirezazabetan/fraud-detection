package com.surepay.frauddetection.service.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surepay.frauddetection.service.dto.TransactionDTO;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class Reader {

    @Value("${file.input}")
    private String fileInput;

    private ObjectMapper mapper = new ObjectMapper();

    @Bean("csvReader")
    public FlatFileItemReader<TransactionDTO> csvReader() {

        FlatFileItemReader<TransactionDTO> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/records.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(csvLineMapper());
        return flatFileItemReader;
    }

    @Bean("jsonReader")
    public JsonItemReader<TransactionDTO> jsonReader() {

        ObjectMapper objectMapper = new ObjectMapper();
        // configure the objectMapper as required
        JacksonJsonObjectReader<TransactionDTO> jsonObjectReader =
                new JacksonJsonObjectReader<>(TransactionDTO.class);
        jsonObjectReader.setMapper(objectMapper);

        return new JsonItemReaderBuilder<TransactionDTO>()
                .jsonObjectReader(jsonObjectReader)
                .resource(new FileSystemResource("src/main/resources/records.json"))
                .name("jsonItemReader")
                .build();
    }

    @Bean
    public LineMapper<TransactionDTO> csvLineMapper() {

        DefaultLineMapper<TransactionDTO> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("reference", "accountNumber", "description", "startBalance", "mutation", "endBalance");

        BeanWrapperFieldSetMapper<TransactionDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TransactionDTO.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
