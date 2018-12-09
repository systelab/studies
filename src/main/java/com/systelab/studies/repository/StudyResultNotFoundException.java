package com.systelab.studies.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudyResultNotFoundException extends RuntimeException {

    private final String id;

    public StudyResultNotFoundException(Long id) {
        super("studyResult-not-found-" + id.toString());
        this.id = id.toString();
    }

    public String getStudyId() {
        return id;
    }
}