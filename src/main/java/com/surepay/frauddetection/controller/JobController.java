package com.surepay.frauddetection.controller;

import com.surepay.frauddetection.model.Fraud;
import com.surepay.frauddetection.service.FraudService;
import com.surepay.frauddetection.service.JobService;
import com.surepay.frauddetection.service.enums.FileFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link com.surepay.frauddetection.service.batch}.
 */
@RestController
@RequestMapping("/job")
public class JobController {

    private final Logger log = LoggerFactory.getLogger(JobController.class);

    private FraudService fraudService;

    private JobService jobService;

    @Autowired
    public JobController(FraudService fraudService, JobService jobService) {
        this.fraudService = fraudService;
        this.jobService = jobService;
    }

    /**
     * {@code GET  /job/format} : Trigger processing transaction file job based on format.
     *
     * @param FileFormat enum It could be JSON or CSV.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Frauds in body., or with status
     * {@code 400 (Bad Request)} if the FileFormat has invalid value.
     */

    @Operation(summary = "Trigger processing transaction file job based on format")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Job trigger and return the list of Frauds in body",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Fraud.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid FileFormat",
                    content = @Content) })
    @GetMapping("/{format}")
    public ResponseEntity<List<Fraud>> load(@PathVariable("format") FileFormat format) throws URISyntaxException,JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        log.debug("REST request to trigger job and return fraud list with format : {}", format);
        if (format.equals(FileFormat.CSV)){
            jobService.runCSVBatch();
        }else if (format.equals(FileFormat.JSON)){
            jobService.runJsonBatch();
        }
        List<Fraud> frauds = fraudService.findAll();
        return ResponseEntity.ok().headers(new HttpHeaders()).body(frauds);
    }
}
