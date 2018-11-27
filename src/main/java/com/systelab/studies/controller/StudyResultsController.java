package com.systelab.studies.controller;

import com.systelab.studies.model.study.StudyResult;
import com.systelab.studies.repository.StudyNotFoundException;
import com.systelab.studies.repository.StudyResultNotFoundException;
import com.systelab.studies.repository.StudyResultRepository;
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

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Api(value = "StudyResult", description = "API for StudyResult management", tags = {"StudyResult"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/studies/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudyResultsController {

    @Autowired
    private StudyResultRepository studyResultRepository;

    @ApiOperation(value = "Get all StudyResult", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("studyresults")
    @PermitAll
    public ResponseEntity<Page<StudyResult>> getAllStudyResults(Pageable pageable) {
        return ResponseEntity.ok(studyResultRepository.findAll(pageable));
    }

    @ApiOperation(value = "Get StudyResult", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("studyresults/{uid}")
    public ResponseEntity<StudyResult> getStudyResult(@PathVariable("uid") Long studyId) {
        return this.studyResultRepository.findById(studyId).map(ResponseEntity::ok).orElseThrow(() -> new StudyResultNotFoundException(studyId));

    }

    @ApiOperation(value = "Create a StudyResult", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("studyresults/studyresult")
    public ResponseEntity<StudyResult> createStudyResult(@RequestBody @ApiParam(value = "Study", required = true) @Valid StudyResult p) {
        StudyResult study = this.studyResultRepository.save(p);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(study.getId()).toUri();
        return ResponseEntity.created(uri).body(study);
    }


    @ApiOperation(value = "Create or Update (idempotent) an existing StudyResult", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("studyresults/{uid}")
    public ResponseEntity<StudyResult> updateStudyResult(@PathVariable("uid") Long studyresultId, @RequestBody @ApiParam(value = "StudyResult", required = true) @Valid StudyResult p) {
        return this.studyResultRepository
                .findById(studyresultId)
                .map(existing -> {
                    p.setId(Long.valueOf(studyresultId.toString()));
                    StudyResult study = this.studyResultRepository.save(p);
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                    return ResponseEntity.created(selfLink).body(study);
                }).orElseThrow(() -> new StudyResultNotFoundException(studyresultId));
    }


    @ApiOperation(value = "Delete a StudyResult", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("studyresults/{uid}")
    public ResponseEntity<?> removeStudyResult(@PathVariable("uid") Long studyId) {
        return this.studyResultRepository.findById(studyId)
                .map(c -> {
                    studyResultRepository.delete(c);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new StudyResultNotFoundException(studyId));
    }
}