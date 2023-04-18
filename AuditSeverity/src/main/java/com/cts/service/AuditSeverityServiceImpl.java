package com.cts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditSeverityServiceImpl implements AuditSeverityService {
	@Autowired
	AuditBenchMarkClient benchmarkClient;

	@Autowired
	AuditCheckListClient checklistClient;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private ResponseRepository responseRepository;

	/*
	 * this method is to create Audit Response
	 * 
	 * @Param int acceptableNo,List<QuestionBank> questions
	 * 
	 * @return AuditResponse
	 */
	public AuditResponse createAuditResponse(int acceptableNo, List<QuestionBank> questions) {
		log.info("Inside createAuditResponse status:BEGIN");
		String auditType = questions.get(0).getAuditType();
		int actualNo = countNos(questions);
		AuditResponse auditResponse = new AuditResponse();
		/*
		 * Here we check actual condition of the severity microservice Determines the
		 * project execution status and the remediation duration detail
		 */
		if (actualNo <= acceptableNo && auditType.equalsIgnoreCase("Internal")) {
			log.info("Checking coditions : actualNo <= acceptableNo && auditType=Internal");
			auditResponse.setProjectExecutionStatus("GREEN");
			auditResponse.setRemedialActionDuration("No action needed");
		} else if (actualNo > acceptableNo && auditType.equalsIgnoreCase("Internal")) {
			log.info("Checking coditions : actualNo > acceptableNo && auditType=Internal");
			auditResponse.setProjectExecutionStatus("RED");
			auditResponse.setRemedialActionDuration("Action to be taken in 2 weeks");
		} else if (actualNo <= acceptableNo && auditType.equalsIgnoreCase("SOX")) {
			log.info("Checking coditions : actualNo <= acceptableNo && auditType=SOX");
			auditResponse.setProjectExecutionStatus("GREEN");
			auditResponse.setRemedialActionDuration("No action needed");
		} else {
			log.info("Checking coditions : actualNo > acceptableNo && auditType=SOX");
			auditResponse.setProjectExecutionStatus("RED");
			auditResponse.setRemedialActionDuration("Action to be taken in 1 week");
		}

		return auditResponse;
	}

	/*
	 * This method is to count the number of No's
	 * 
	 * @Param List<QuestionBank> questions
	 * 
	 * @return int count
	 */
	public int countNos(List<QuestionBank> questions) {
		log.info("Inside countNos status:BEGIN");
		int count = 0;
		for (QuestionBank q : questions) {
			if (q.getResponse().equalsIgnoreCase("No")) {
				log.info("cheking for question response NO");
				count++;
			}
		}
		return count;
	}

	/*
	 * this method is to save request
	 * 
	 * @Param AuditRequest request
	 * 
	 * @return AuditRequestModel
	 */
	@Override
	public AuditRequestModel saveRequest(AuditRequest request) {
		log.info("Inside  AuditSeverity saveRequest service status:BEGIN");
		AuditRequestModel requestModel = new AuditRequestModel();
		AuditDetailModel auditDetailModel = new AuditDetailModel();
		auditDetailModel.setAuditType(request.getAuditdetails().getAuditType());
		auditDetailModel.setAuditDate(request.getAuditdetails().getAuditDate());
		requestModel.setAuditDetail(auditDetailModel);
		requestModel.setProjectName(request.getProjectName());
		requestModel.setManagerName(request.getProjectManagerName());
		requestModel.setOwnerName(request.getApplicationOwnerName());
		requestRepository.save(requestModel);
		return requestModel;
	}

	/*
	 * this method is to save response
	 * 
	 * @Param String token,AuditRequest request
	 * 
	 * @return AuditResponseModel
	 */
	@Override
	public AuditResponseModel saveResponse(String token, AuditRequest request) {
		log.info("Inside  AuditSeverity saveResponse service status:BEGIN");
		int accNoAnswers = 0;
		for (AuditBenchmark benchmark : benchmarkClient.getBenchmaks().getBody()) {
			if (benchmark.getAuditType().equalsIgnoreCase(request.getAuditdetails().getAuditType())) {
				log.info("cheking for AuditType inside saveResponse");
				accNoAnswers = benchmark.getBenchmarkNoAnswers();
			}
		}

		checklistClient.saveResponses(token, request.getAuditdetails().getAuditQuestions());

		AuditResponse response = createAuditResponse(accNoAnswers, request.getAuditdetails().getAuditQuestions());
		AuditResponseModel responseModel = new AuditResponseModel(response.getProjectExecutionStatus(),
				response.getRemedialActionDuration());
		responseRepository.save(responseModel);
		return responseModel;
	}

}
