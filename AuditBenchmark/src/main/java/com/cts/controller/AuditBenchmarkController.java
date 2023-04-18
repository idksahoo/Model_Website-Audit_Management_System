package com.cts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.AuditBenchmark;
import com.cts.service.AuditBenchmarkService;

import feign.Response;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin()
public class AuditBenchmarkController {
	@Autowired
	AuditBenchmarkService service;

	/*
	 * End point to retrieve AuditBenchmark
	 * 
	 * @Param None
	 * 
	 * @return ResponseEntity<List<AuditBenchmark>>
	 */
	@GetMapping("/AuditBenchmark")
	public ResponseEntity<List<AuditBenchmark>> getBenchmaks() {
		log.info("inside getbenchmaks status:BEGIN");
		List<AuditBenchmark> list = new ArrayList<>();
		list = service.getBenchmarks();
		ResponseEntity<List<AuditBenchmark>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
		return responseEntity;
	}
}
