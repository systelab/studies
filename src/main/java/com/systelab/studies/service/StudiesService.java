package com.systelab.studies.service;

import com.systelab.studies.model.study.Result;
import com.systelab.studies.model.study.Study;
import com.systelab.studies.model.study.StudyResult;
import com.systelab.studies.repository.StudyNotFoundException;
import com.systelab.studies.repository.StudyRepository;
import com.systelab.studies.repository.StudyResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudiesService {
    private final StudyRepository studyRepository;
    private final StudyResultRepository studyResultRepository;

    @Autowired
    public StudiesService(StudyRepository studyRepository, StudyResultRepository studyResultRepository) {
        this.studyRepository = studyRepository;
        this.studyResultRepository = studyResultRepository;
    }

    public Page<Study> getAllStudies(Pageable pageable) {
        return studyRepository.findAll(pageable);
    }


    public Study getStudy(UUID studyId) {
        return this.studyRepository.findById(studyId).orElseThrow(() -> new StudyNotFoundException(studyId));

    }

    public Study createStudy(Study s) {
        return this.studyRepository.save(s);
    }

    public Study updateStudy(UUID studyId, Study s) {
        return this.studyRepository.findById(studyId)
                .map(existing -> {
                    s.setId(studyId);
                    Study study = this.studyRepository.save(s);
                    return study;
                }).orElseThrow(() -> new StudyNotFoundException(studyId));
    }

    public Study removeStudy(UUID studyId) {
        return this.studyRepository.findById(studyId)
                .map(study -> {
                    studyRepository.delete(study);
                    return study;
                }).orElseThrow(() -> new StudyNotFoundException(studyId));
    }

    public StudyResult createResultStudy(UUID studyId, Long resultId, boolean isOnitted, String comments) {
        Study study = this.studyRepository.findById(studyId).orElseThrow(() -> new StudyNotFoundException(studyId));
        Result result = new Result();
        result.setId(resultId);
        StudyResult resultForStudy = new StudyResult(study, result);
        resultForStudy.setOmmited(isOnitted);
        resultForStudy.setComments(comments);
        resultForStudy = studyResultRepository.save(resultForStudy);
        return resultForStudy;
    }

    public StudyResult removeResultStudy(UUID studyId, Long resultId) {
        return this.studyResultRepository.findByStudyIdAndResultId(studyId, resultId)
                .map(result -> {
                    studyResultRepository.delete(result);
                    return result;
                }).orElseThrow(() -> new StudyNotFoundException(studyId));
    }

    public Page<StudyResult> getResultsStudy(UUID studyId, Pageable pageable) {
        return this.studyResultRepository.findByStudyId(studyId, pageable).orElseThrow(() -> new StudyNotFoundException(studyId));

    }
}
