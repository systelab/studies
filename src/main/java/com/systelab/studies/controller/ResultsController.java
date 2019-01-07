package com.systelab.studies.controller;

import com.systelab.studies.model.study.Result;
import com.systelab.studies.service.ResultsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api(value = "Result", description = "API for Study Result management", tags = {"Result"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/studies/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResultsController {

    private final ResultsService resultsService;

    @Autowired
    public ResultsController(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    @ApiOperation(value = "Get all result", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("results")
    public ResponseEntity<Page<Result>> getAllResults(Pageable pageable) {
        return ResponseEntity.ok(this.resultsService.getAllResults(pageable));
    }

    @ApiOperation(value = "Get Result", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("results/{id}")
    public ResponseEntity<Result> getResult(@PathVariable("id") Long resultId) {
        return ResponseEntity.ok(this.resultsService.getResult(resultId));
    }

    @ApiOperation(value = "Create a Result", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("results/result")
    public ResponseEntity<Result> createResult(@RequestBody @ApiParam(value = "Result", required = true) @Valid Result r) {
        Result result = this.resultsService.createResult(r);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @ApiOperation(value = "Create or Update (idempotent) an existing Result", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("results/{id}")
    public ResponseEntity<Result> updateResult(@PathVariable("id") Long resultId, @RequestBody @ApiParam(value = "Result", required = true) @Valid Result r) {
        Result result = this.resultsService.updateResult(resultId, r);
        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(selfLink).body(result);
    }

    @ApiOperation(value = "Delete a Result", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("results/{id}")
    public ResponseEntity<?> removeResult(@PathVariable("id") Long resultId) {
        this.resultsService.removeResult(resultId);
        return ResponseEntity.noContent().build();
    }
}