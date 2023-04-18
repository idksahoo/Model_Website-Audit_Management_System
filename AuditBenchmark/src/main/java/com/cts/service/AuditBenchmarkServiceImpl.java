package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.entity.AuditBenchmark;
import com.cts.repository.AuditBenchmarkRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditBenchmarkServiceImpl implements AuditBenchmarkService {
	@Autowired
	AuditBenchmarkRepository auditBenchmarkRepo;

	/*
	 * Service for End point getBenchmarks
	 * 
	 * @Param None
	 * 
	 * @return List<AuditBenchmark>
	 */
	@Override
	public List<AuditBenchmark> getBenchmarks() {
		log.info("inside the AuditBenchmarkServiceImpl getBenchmarks service : BEGIN");
		return auditBenchmarkRepo.findAll();
	}
}
