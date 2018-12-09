package com.systelab.studies.repository;

import com.systelab.studies.model.study.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends JpaRepository<Connection, Long> {
}