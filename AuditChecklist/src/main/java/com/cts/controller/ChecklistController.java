package com.cts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cts.client.AuthClient;
import com.cts.entity.QuestionBank;
import com.cts.exceptions.AuthorizationException;
import com.cts.service.ChecklistService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin()
public class ChecklistController {

	@Autowired
	private ChecklistService checkListService;

	@Autowired
	AuthClient authClient;

	/*
	 * End point for retrieving the questions from the DB
	 * 
	 * @Param String requestTokenHeader, String auditType
	 * 
	 * @return ResponseEntity<ArrayList<QuestionBank>> throws AuthorizationException
	 */
	@GetMapping("/AuditCheckListQuestions/{auditType}")
	public ResponseEntity<ArrayList<QuestionBank>> getQuestions(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@PathVariable String auditType) throws AuthorizationException {
		if ((Boolean) authClient.validatingAuthorizationToken(requestTokenHeader)) {
			log.info("inside getQuestions status:BEGIN");
			ArrayList<QuestionBank> list = new ArrayList<QuestionBank>();
			list = checkListService.findByType(auditType);
			ResponseEntity<ArrayList<QuestionBank>> responseEntity = new ResponseEntity<ArrayList<QuestionBank>>(list,
					HttpStatus.OK);
			return responseEntity;
		} else {
			throw new AuthorizationException("Invalid token");
		}
	}

	/*
	 * End point for saving responses
	 * 
	 * @Param String requestTokenHeader,List<QuestionBank>
	 * 
	 * @return ResponseEntity<List<QuestionBank>> throws AuthorizationException
	 */
	@PostMapping("/saveResponse")
	public ResponseEntity<List<QuestionBank>> saveResponses(
			@RequestHeader(value = "Authorization", required = true) String requestTokenHeader,
			@RequestBody List<QuestionBank> list) {
		if ((Boolean) authClient.validatingAuthorizationToken(requestTokenHeader)) {
			log.info("inside saveResponses status:BEGIN");
			List<QuestionBank> list1 = checkListService.saveResponse(list);
			ResponseEntity<List<QuestionBank>> responseEntity = new ResponseEntity<List<QuestionBank>>(list1,
					HttpStatus.OK);
			return responseEntity;
		} else {
			throw new AuthorizationException("Invalid token");
		}
	}
}
