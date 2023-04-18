package com.cts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cts.client.AuditBenchMarkClient;
import com.cts.client.AuditCheckListClient;
import com.cts.client.AuthClient;
import com.cts.model.AuditDetailModel;
import com.cts.model.AuditRequestModel;
import com.cts.model.AuditResponseModel;
import com.cts.pojo.AuditDetails;
import com.cts.pojo.AuditRequest;
import com.cts.pojo.AuditResponse;
import com.cts.pojo.QuestionBank;
import com.cts.repository.RequestRepository;
import com.cts.repository.ResponseRepository;
import com.cts.service.AuditSeverityService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = AuditSeverityController.class)
public class AuditSeverityControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuditSeverityService auditSeverityService;

	@MockBean
	AuthClient authClient;

	@Mock
	ResponseRepository responseRepo;

	@Mock
	RequestRepository requestRepo;

	@Mock
	AuditCheckListClient checklistClient;

	@Mock
	AuditBenchMarkClient benchmarkClient;

	// Test Authorising client not null
	@Test
	@DisplayName("Test Authorising client")
	void testAuthClientNotNull() {
		assertThat(authClient).isNotNull();
	}

	// Test Checklist client not null
	@Test
	@DisplayName("Test checklist client")
	void testChecklistClientNotNull() {
		assertThat(checklistClient).isNotNull();
	}

	// Test Benchmark client not null
	@Test
	@DisplayName("Test Benchmark client")
	void testBenchmarkClientNotNull() {
		assertThat(benchmarkClient).isNotNull();
	}

	// Test Mock Mvc client not null
	@Test
	@DisplayName("Test Mock MVC client")
	void testMockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	// Test ChecklistService client not null
	@Test
	@DisplayName("Test ChecklistService client")
	void testServiceNotNull() {
		assertThat(auditSeverityService).isNotNull();
	}

	ArrayList<QuestionBank> auditQuestions = new ArrayList<>();
	AuditDetails auditDetails = new AuditDetails();
	AuditRequest auditRequest = new AuditRequest();
	Date date = new Date();
	AuditResponse auditResponse = new AuditResponse(1, "projectExecutionStatus", "remedialActionDuration");
	AuditDetailModel auditDetailModel = new AuditDetailModel(0, "Internal", new Date(2021, 8, 12));
	AuditRequestModel auditRequestModel = new AuditRequestModel(0, auditDetailModel, "projectName",
			"projectManagerName", "applicationOwnerName");
	AuditResponseModel auditResponseModel = new AuditResponseModel(0, "RED", "Action to be taken in 2 weeks");

	@BeforeEach
	public void setUp() {
		auditQuestions.add(new QuestionBank(1, "Internal", "ABC", "No"));
		auditQuestions.add(new QuestionBank(2, "Internal", "DEF", "no"));
		auditQuestions.add(new QuestionBank(3, "Internal", "PQR", "no"));
		auditQuestions.add(new QuestionBank(4, "Internal", "STU", "Yes"));
		auditQuestions.add(new QuestionBank(5, "Internal", "QWF", "no"));
		date = new Date(2021, 8, 12);
		auditDetails = new AuditDetails("Internal", date, auditQuestions);
		auditRequest = new AuditRequest("projectName", "projectManagerName", "applicationOwnerName", auditDetails);
	}

	// Test ProjectExecutionStatus of AuditSeverityController with Valid token
	@Test
	@DisplayName("Test ProjectExecutionStatus of AuditSeverityController with Valid token")
	public void testProjectExecutionStatusValidToken() throws Exception {
		when(authClient.validatingAuthorizationToken("rightToken")).thenReturn(true);
		when(auditSeverityService.saveRequest(auditRequest)).thenReturn(auditRequestModel);
		this.mockMvc.perform(post("/getStatus").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(auditRequest)).header("Authorization", "rightToken"))
				.andExpect(status().isOk());
	}

	// Test ProjectExecutionStatus of AuditSeverityController with InValid token
	@Test
	@DisplayName("Test ProjectExecutionStatus of AuditSeverityController with InValid token")
	public void testProjectExecutionStatusInValidToken() throws Exception {
		when(authClient.validatingAuthorizationToken("wrongToken")).thenReturn(false);
		this.mockMvc.perform(post("/getStatus").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(auditRequest)).header("Authorization", "wrongToken"))
				.andExpect(status().isForbidden());
	}

	// Test ProjectExecutionStatus of AuditSeverityController without token
	@Test
	@DisplayName("Test ProjectExecutionStatus of AuditSeverityController without token")
	public void testProjectExecutionStatusWithoutToken() throws Exception {
		this.mockMvc.perform(post("/getStatus")).andExpect(status().isBadRequest());
	}

}
