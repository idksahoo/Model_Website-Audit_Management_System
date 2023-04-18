package com.cts.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cts.exceptions.AuthorizationException;
import com.cts.pojo.QuestionBank;

@FeignClient(name = "AuditCheckList", url = "${Checklist.url}")
public interface AuditCheckListClient {
	@GetMapping("/AuditCheckListQuestions/{auditType}")
	public ResponseEntity<ArrayList<QuestionBank>> getQuestions(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable String auditType) throws AuthorizationException;

	@PostMapping("/saveResponse")
	public ResponseEntity<List<QuestionBank>> saveResponses(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody List<QuestionBank> list) throws AuthorizationException;

}
