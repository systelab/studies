package com.systelab.studies.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResultNotFoundException extends RuntimeException {

    private final String id;

    public ResultNotFoundException(Long id) {
        super("result-not-found-" + id.toString());
        this.id = id.toString();
    }

    public String getResultId() {
        return id;
    }
}