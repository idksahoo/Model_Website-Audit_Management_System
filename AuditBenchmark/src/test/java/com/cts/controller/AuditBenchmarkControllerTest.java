package com.cts.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.cts.entity.AuditBenchmark;
import com.cts.service.AuditBenchmarkService;

import ch.qos.logback.core.status.Status;

@WebMvcTest(value = AuditBenchmarkController.class)
public class AuditBenchmarkControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuditBenchmarkService auditBenchmarkService;

	AuditBenchmark benchmark = new AuditBenchmark(1, "Internal", 3);

	// Test getbenchmaks of AuditBenchmarkController
	@Test
	@DisplayName("Test getbenchmaks of AuditBenchmarkController")
	public void testGetBenchmaks() throws Exception {
		when(auditBenchmarkService.getBenchmarks()).thenReturn(Arrays.asList(benchmark));
		this.mockMvc.perform(get("/AuditBenchmark")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
	}
}
