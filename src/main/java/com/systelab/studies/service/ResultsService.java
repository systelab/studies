package com.systelab.studies.service;

import com.systelab.studies.model.study.Result;
import com.systelab.studies.repository.ResultNotFoundException;
import com.systelab.studies.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResultsService {
    private final ResultRepository resultsRepository;

    @Autowired
    public ResultsService(ResultRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    public Page<Result> getAllResults(Pageable pageable) {
        return resultsRepository.findAll(pageable);
    }

    public Result getResult(Long resultId) {
        return this.resultsRepository.findById(resultId).orElseThrow(() -> new ResultNotFoundException(resultId));

    }

    public Result createResult(Result r) {
        return this.resultsRepository.save(r);
    }

    public Result updateResult(Long resultId, Result r) {
        return this.resultsRepository.findById(resultId)
                .map(existing -> {
                    r.setId(resultId);
                    Result result = this.resultsRepository.save(r);
                    return result;
                }).orElseThrow(() -> new ResultNotFoundException(resultId));
    }

    public Result removeResult(Long resultId) {
        return this.resultsRepository.findById(resultId)
                .map(result -> {
                    resultsRepository.delete(result);
                    return result;
                }).orElseThrow(() -> new ResultNotFoundException(resultId));
    }
}
