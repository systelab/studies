package com.systelab.studies.repository;

import com.systelab.studies.model.study.StudyResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudyResultRepository extends JpaRepository<StudyResult, Long> {
    Optional<StudyResult>  findByStudyIdAndResultId(UUID studyId, Long resultId);
    Optional<Page<StudyResult>>  findByStudyId(UUID studyId, Pageable pageable);
}