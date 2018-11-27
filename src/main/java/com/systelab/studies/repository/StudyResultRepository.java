package com.systelab.studies.repository;

import com.systelab.studies.model.study.StudyResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyResultRepository extends JpaRepository<StudyResult, Long> {
}