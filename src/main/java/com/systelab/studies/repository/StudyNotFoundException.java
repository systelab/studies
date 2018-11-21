package com.systelab.studies.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudyNotFoundException extends RuntimeException {

    private final String id;

    public StudyNotFoundException(UUID id) {
        super("study-not-found-" + id.toString());
        this.id = id.toString();
    }

    public String getStudentId() {
        return id;
    }
}