package com.cts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.entity.AuditBenchmark;
import com.cts.repository.AuditBenchmarkRepository;

public class AuditBenchMarkServiceTest {
	@Mock
	AuditBenchmarkRepository auditBenchmarkRepo;

	@InjectMocks
	AuditBenchmarkServiceImpl auditBenchmarkServiceImpl;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	AuditBenchmark benchmark = new AuditBenchmark(1, "Internal", 3);

	// Test for End point GetBenchmark
	@Test
	public void testGetBenchmark() {
		when(auditBenchmarkRepo.findAll()).thenReturn(Arrays.asList(benchmark));
		assertEquals(Arrays.asList(benchmark), auditBenchmarkServiceImpl.getBenchmarks());
	}
}
