package com.systelab.studies.repository;

import com.systelab.studies.model.study.Result;
import com.systelab.studies.model.study.StudyResult;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudyResultRepository extends JpaRepository<StudyResult, Long> {
    Optional<StudyResult>  findByStudyIdAndResultId(UUID studyId, Long resultId);
    Optional<Page<StudyResult>>  findByStudyId(UUID studyId, Pageable pageable);
}