package com.cts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.client.AuditBenchMarkClient;
import com.cts.client.AuditCheckListClient;
import com.cts.model.AuditDetailModel;
import com.cts.model.AuditRequestModel;
import com.cts.model.AuditResponseModel;
import com.cts.pojo.AuditBenchmark;
import com.cts.pojo.AuditDetails;
import com.cts.pojo.AuditRequest;
import com.cts.pojo.AuditResponse;
import com.cts.pojo.QuestionBank;
import com.cts.repository.RequestRepository;
import com.cts.repository.ResponseRepository;

@SpringBootTest
public class AuditSeverityServiceTest {
	@Mock
	ResponseRepository responseRepo;

	@Mock
	RequestRepository requestRepo;

	@Mock
	AuditCheckListClient checklistClient;

	@Mock
	AuditBenchMarkClient benchmarkClient;

	@InjectMocks
	AuditSeverityServiceImpl auditSeverityServiceImpl;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
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

	// Test for service method CreateResponseGreenSOX1No
	@Test
	public void testCreateResponseGreenSOX1No() {
		auditQuestions.add(new QuestionBank(1, "SOX", "ABC", "No"));
		auditQuestions.add(new QuestionBank(2, "SOX", "DEF", "Yes"));
		auditQuestions.add(new QuestionBank(3, "SOX", "PQR", "Yes"));
		auditQuestions.add(new QuestionBank(4, "SOX", "STU", "Yes"));
		auditQuestions.add(new QuestionBank(5, "SOX", "QWF", "Yes"));
		auditResponse = new AuditResponse(0, "GREEN", "No action needed");
		assertEquals(auditResponse.getAuditId(),
				auditSeverityServiceImpl.createAuditResponse(1, auditQuestions).getAuditId());
		assertEquals(auditResponse.getProjectExecutionStatus(),
				auditSeverityServiceImpl.createAuditResponse(1, auditQuestions).getProjectExecutionStatus());
		assertEquals(auditResponse.getRemedialActionDuration(),
				auditSeverityServiceImpl.createAuditResponse(1, auditQuestions).getRemedialActionDuration());

	}

	// Test for service method CreateResponseGreenSOX3No
	@Test
	public void testCreateResponseRedSOX3No() {
		auditQuestions.add(new QuestionBank(1, "SOX", "ABC", "No"));
		auditQuestions.add(new QuestionBank(2, "SOX", "DEF", "no"));
		auditQuestions.add(new QuestionBank(3, "SOX", "PQR", "Yes"));
		auditQuestions.add(new QuestionBank(4, "SOX", "STU", "Yes"));
		auditQuestions.add(new QuestionBank(5, "SOX", "QWF", "no"));
		auditResponse = new AuditResponse(0, "RED", "Action to be taken in 1 week");
		assertEquals(auditResponse.getAuditId(),
				auditSeverityServiceImpl.createAuditResponse(1, auditQuestions).getAuditId());
		assertEquals(auditResponse.getProjectExecutionStatus(),
				auditSeverityServiceImpl.createAuditResponse(1, auditQuestions).getProjectExecutionStatus());
		assertEquals(auditResponse.getRemedialActionDuration(),
				auditSeverityServiceImpl.createAuditResponse(1, auditQuestions).getRemedialActionDuration());

	}

	// Test for service method CreateResponseGreenInternal3No
	@Test
	public void testCreateResponseGreenInternal3No() {
		auditQuestions.add(new QuestionBank(1, "Internal", "ABC", "No"));
		auditQuestions.add(new QuestionBank(2, "Internal", "DEF", "no"));
		auditQuestions.add(new QuestionBank(3, "Internal", "PQR", "Yes"));
		auditQuestions.add(new QuestionBank(4, "Internal", "STU", "Yes"));
		auditQuestions.add(new QuestionBank(5, "Internal", "QWF", "no"));
		auditResponse = new AuditResponse(0, "GREEN", "No action needed");
		assertEquals(auditResponse.getAuditId(),
				auditSeverityServiceImpl.createAuditResponse(3, auditQuestions).getAuditId());
		assertEquals(auditResponse.getProjectExecutionStatus(),
				auditSeverityServiceImpl.createAuditResponse(3, auditQuestions).getProjectExecutionStatus());
		assertEquals(auditResponse.getRemedialActionDuration(),
				auditSeverityServiceImpl.createAuditResponse(3, auditQuestions).getRemedialActionDuration());

	}

	// Test for service method CreateResponseGreenInternal3No
	@Test
	public void testCreateResponseRedInternal4No() {
		auditQuestions.add(new QuestionBank(1, "Internal", "ABC", "No"));
		auditQuestions.add(new QuestionBank(2, "Internal", "DEF", "no"));
		auditQuestions.add(new QuestionBank(3, "Internal", "PQR", "no"));
		auditQuestions.add(new QuestionBank(4, "Internal", "STU", "Yes"));
		auditQuestions.add(new QuestionBank(5, "Internal", "QWF", "no"));
		auditResponse = new AuditResponse(0, "RED", "Action to be taken in 2 weeks");
		assertEquals(auditResponse.getAuditId(),
				auditSeverityServiceImpl.createAuditResponse(3, auditQuestions).getAuditId());
		assertEquals(auditResponse.getProjectExecutionStatus(),
				auditSeverityServiceImpl.createAuditResponse(3, auditQuestions).getProjectExecutionStatus());
		assertEquals(auditResponse.getRemedialActionDuration(),
				auditSeverityServiceImpl.createAuditResponse(3, auditQuestions).getRemedialActionDuration());

	}

	// test for service method CountNos
	@Test
	public void testCountNos() {
		auditQuestions.add(new QuestionBank(1, "Internal", "ABC", "No"));
		auditQuestions.add(new QuestionBank(2, "Internal", "DEF", "no"));
		auditQuestions.add(new QuestionBank(3, "Internal", "PQR", "no"));
		auditQuestions.add(new QuestionBank(4, "Internal", "STU", "Yes"));
		auditQuestions.add(new QuestionBank(5, "Internal", "QWF", "no"));
		assertEquals(4, auditSeverityServiceImpl.countNos(auditQuestions));
	}

	// test for service method saveRequest
	@SuppressWarnings("deprecation")
	@Test
	public void testSaveRequest() {
		auditQuestions.add(new QuestionBank(1, "Internal", "ABC", "No"));
		auditQuestions.add(new QuestionBank(2, "Internal", "DEF", "no"));
		auditQuestions.add(new QuestionBank(3, "Internal", "PQR", "no"));
		auditQuestions.add(new QuestionBank(4, "Internal", "STU", "Yes"));
		auditQuestions.add(new QuestionBank(5, "Internal", "QWF", "no"));
		date = new Date(2021, 8, 12);
		auditDetails = new AuditDetails("Internal", date, auditQuestions);
		auditRequest = new AuditRequest("projectName", "projectManagerName", "applicationOwnerName", auditDetails);

		assertEquals(auditRequestModel.getOwnerName(),
				auditSeverityServiceImpl.saveRequest(auditRequest).getOwnerName());
	}

	// test for service method saveResponse
	@Test
	public void testSaveResponse() {
		auditQuestions.add(new QuestionBank(1, "Internal", "ABC", "No"));
		auditQuestions.add(new QuestionBank(2, "Internal", "DEF", "no"));
		auditQuestions.add(new QuestionBank(3, "Internal", "PQR", "no"));
		auditQuestions.add(new QuestionBank(4, "Internal", "STU", "Yes"));
		auditQuestions.add(new QuestionBank(5, "Internal", "QWF", "no"));
		ResponseEntity<List<QuestionBank>> responseEntity = new ResponseEntity<List<QuestionBank>>(auditQuestions,
				HttpStatus.OK);
		date = new Date(2021, 8, 12);
		auditDetails = new AuditDetails("Internal", date, auditQuestions);
		auditRequest = new AuditRequest("projectName", "projectManagerName", "applicationOwnerName", auditDetails);
		List<AuditBenchmark> benchmarks = new ArrayList<AuditBenchmark>();
		ResponseEntity<List<AuditBenchmark>> resp1 = new ResponseEntity<List<AuditBenchmark>>(benchmarks,
				HttpStatus.OK);
		benchmarks.add(new AuditBenchmark(1, "Internal", 3));
		when(benchmarkClient.getBenchmaks()).thenReturn(resp1);
		when(checklistClient.saveResponses("righttoken", auditQuestions)).thenReturn(responseEntity);
		assertEquals(auditResponseModel, auditSeverityServiceImpl.saveResponse("righttoken", auditRequest));
	}

}
