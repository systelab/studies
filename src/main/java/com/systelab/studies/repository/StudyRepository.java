package com.systelab.studies.repository;

import com.systelab.studies.model.study.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudyRepository extends JpaRepository<Study, UUID> {
}