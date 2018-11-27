package com.systelab.studies.repository;

import com.systelab.studies.model.study.StudyTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<StudyTest, Long> {
}