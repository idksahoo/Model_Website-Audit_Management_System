package com.cts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.entity.AuditBenchmark;

@Repository
public interface AuditBenchmarkRepository extends JpaRepository<AuditBenchmark, Integer> {

}
