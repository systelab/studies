package com.systelab.studies.controller;

import com.systelab.studies.model.study.Result;
import com.systelab.studies.model.study.Study;
import com.systelab.studies.model.study.StudyResult;
import com.systelab.studies.model.study.dto.StudyResultDTO;
import com.systelab.studies.repository.StudyNotFoundException;
import com.systelab.studies.repository.StudyRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Api(value = "Study", description = "API for Study management", tags = {"Study"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/studies/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudiesController {

    private final StudyRepository studyRepository;
    private final StudyResultRepository studyResultRepository;

    @Autowired
    public StudiesController(StudyRepository studyRepository, StudyResultRepository studyResultRepository) {
        this.studyRepository = studyRepository;
        this.studyResultRepository = studyResultRepository;
    }

    @ApiOperation(value = "Get all studies", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("studies")
    public ResponseEntity<Page<Study>> getAllStudies(Pageable pageable) {
        return ResponseEntity.ok(studyRepository.findAll(pageable));
    }

    @ApiOperation(value = "Get Study", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("studies/{uid}")
    public ResponseEntity<Study> getStudy(@PathVariable("uid") UUID studyId) {
        return this.studyRepository.findById(studyId).map(ResponseEntity::ok).orElseThrow(() -> new StudyNotFoundException(studyId));

    }

    @ApiOperation(value = "Create a Study", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("studies/study")
    public ResponseEntity<Study> createStudy(@RequestBody @ApiParam(value = "Study", required = true) @Valid Study s) {
        Study study = this.studyRepository.save(s);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(study.getId()).toUri();
        return ResponseEntity.created(uri).body(study);
    }

    @ApiOperation(value = "Create or Update (idempotent) an existing Study", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("studies/{uid}")
    public ResponseEntity<Study> updateStudy(@PathVariable("uid") UUID studyId, @RequestBody @ApiParam(value = "Study", required = true) @Valid Study s) {
        return this.studyRepository.findById(studyId)
                .map(existing -> {
                    s.setId(studyId);
                    Study study = this.studyRepository.save(s);
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                    return ResponseEntity.created(selfLink).body(study);
                }).orElseThrow(() -> new StudyNotFoundException(studyId));
    }

    @ApiOperation(value = "Delete a Study", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("studies/{uid}")
    public ResponseEntity<?> removeStudy(@PathVariable("uid") UUID studyId) {
        return this.studyRepository.findById(studyId)
                .map(study -> {
                    studyRepository.delete(study);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new StudyNotFoundException(studyId));
    }

    @ApiOperation(value = "Create a Result to Study", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("study/{uid}/result")
    public ResponseEntity<StudyResult> createResultStudy(@PathVariable("uid") UUID studyId, @RequestBody @ApiParam(value = "StudyResult", required = true) @Valid StudyResultDTO dto) {
        Study study = this.studyRepository.findById(studyId).orElseThrow(() -> new StudyNotFoundException(studyId));
        Result result = new Result();
        result.setId(dto.getResultId());
        StudyResult resultForStudy = new StudyResult(study, result);
        resultForStudy.setOmmited(dto.isOmmited());
        resultForStudy.setComments(dto.getComments());
        resultForStudy = studyResultRepository.save(resultForStudy);
        return ResponseEntity.ok().body(resultForStudy);
    }

    @ApiOperation(value = "Delete a Result from Study", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("study/{uid}/result/{id}")
    public ResponseEntity<?> removeResultStudy(@PathVariable("uid") UUID studyId, @PathVariable("id") Long resultId) {
        return this.studyResultRepository.findByStudyIdAndResultId(studyId, resultId)
                .map(result -> {
                    studyResultRepository.delete(result);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new StudyNotFoundException(studyId));
    }

    @ApiOperation(value = "Get Results from Study", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("study/{uid}/result")
    public ResponseEntity<Page<StudyResult>> getResultsStudy(@PathVariable("uid") UUID studyId, Pageable pageable) {
        return this.studyResultRepository.findByStudyId(studyId, pageable).map(ResponseEntity::ok).orElseThrow(() -> new StudyNotFoundException(studyId));

    }
}