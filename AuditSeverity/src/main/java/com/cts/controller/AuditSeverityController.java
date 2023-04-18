package com.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.client.AuthClient;
import com.cts.exceptions.AuthorizationException;
import com.cts.model.AuditRequestModel;
import com.cts.model.AuditResponseModel;
import com.cts.pojo.AuditRequest;
import com.cts.service.AuditSeverityService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin()
public class AuditSeverityController {

	@Autowired
	AuditSeverityService severityService;

	@Autowired
	AuthClient authClient;

	/*
	 * This is to check the severity of the audit and it returns the execution
	 * status of the project
	 * 
	 * @Param String requestTokenHeader
	 * 
	 * @return AuditRequest auditRequest throws AuthorizationException
	 */
	@PostMapping("/getStatus")
	public ResponseEntity<AuditResponseModel> projectExecutionStatus(
			@RequestHeader(value = "Authorization", required = true) final String requestTokenHeader,
			@RequestBody AuditRequest auditRequest) {
		log.info(requestTokenHeader);
		if ((Boolean) authClient.validatingAuthorizationToken(requestTokenHeader)) {
			log.info("Inside ProjectExecutionStatus, valid token status:BEGIN");
			AuditRequestModel auditRequestModel = severityService.saveRequest(auditRequest);
			ResponseEntity<AuditResponseModel> responseEntity = new ResponseEntity<AuditResponseModel>(
					severityService.saveResponse(requestTokenHeader, auditRequest), HttpStatus.OK);
			return responseEntity;
		} else {
			log.error("Invalid token, AuthorizationException thrown");
			throw new AuthorizationException("Invalid token");
		}
	}
}
