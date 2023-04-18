package com.cts.service;

import com.cts.model.AuditRequestModel;

import com.cts.model.AuditResponseModel;
import com.cts.pojo.AuditRequest;

public interface AuditSeverityService {
	AuditResponseModel saveResponse(String token, AuditRequest request);

	AuditRequestModel saveRequest(AuditRequest request);
}
