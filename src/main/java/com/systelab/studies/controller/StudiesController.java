package com.systelab.studies.controller;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import com.systelab.studies.model.study.Result;
import com.systelab.studies.model.study.Study;
import com.systelab.studies.repository.StudyNotFoundException;
import com.systelab.studies.repository.StudyRepository;
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
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Api(value = "Study", description = "API for Study management", tags = {"Study"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/studies/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudiesController {

    @Autowired
    private StudyRepository studyRepository;

    @ApiOperation(value = "Get all studies", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("studies")
    @PermitAll
    public ResponseEntity<Page<Study>> getAllStudies(Pageable pageable) {
        return ResponseEntity.ok(studyRepository.findAll(pageable));
    }

    @ApiOperation(value = "Get Study", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("studies/{uid}")
    public ResponseEntity<Study> getStudy(@PathVariable("uid") UUID studyId) {
        return this.studyRepository.findById(studyId).map(ResponseEntity::ok).orElseThrow(() -> new StudyNotFoundException(studyId));

    }

    @ApiOperation(value = "Get Results", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("studies/{uid}/results")
    public ResponseEntity<Set<Result>> getStudyResults(@PathVariable("uid") UUID studyId) {
        return this.studyRepository.findById(studyId).map((study)->ResponseEntity.ok(study.getResults())).orElseThrow(() -> new StudyNotFoundException(studyId));

    }

    @ApiOperation(value = "Add Results", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("studies/{uid}/results")
    public ResponseEntity<Set<Result>> addStudyResults(@PathVariable("uid") UUID studyId, @RequestBody @ApiParam(required = true) @Valid Set<Result> results) {

        Study study=this.studyRepository.findById(studyId).orElseThrow(() -> new StudyNotFoundException(studyId));

        study.getResults().addAll(results);
        this.studyRepository.save(study);
        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
        return ResponseEntity.created(selfLink).body(results);
    }

    @ApiOperation(value = "Create a Study", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("studies/study")
    public ResponseEntity<Study> createStudy(@RequestBody @ApiParam(value = "Study", required = true) @Valid Study p) {
        Study study = this.studyRepository.save(p);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(study.getId()).toUri();
        return ResponseEntity.created(uri).body(study);
    }


    @ApiOperation(value = "Create or Update (idempotent) an existing Study", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("studies/{uid}")
    public ResponseEntity<Study> updateStudy(@PathVariable("uid") UUID studyId, @RequestBody @ApiParam(value = "Study", required = true) @Valid Study p) {
        return this.studyRepository
                .findById(studyId)
                .map(existing -> {
                    p.setId(studyId);
                    Study study = this.studyRepository.save(p);
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                    return ResponseEntity.created(selfLink).body(study);
                }).orElseThrow(() -> new StudyNotFoundException(studyId));
    }


    @ApiOperation(value = "Delete a Study", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("studies/{uid}")
    public ResponseEntity<?> removeStudy(@PathVariable("uid") UUID studyId) {
        return this.studyRepository.findById(studyId)
                .map(c -> {
                    studyRepository.delete(c);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new StudyNotFoundException(studyId));
    }
}