package com.cts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.entity.QuestionBank;
import com.cts.exceptions.InvalidAuditTypeException;
import com.cts.repository.QuestionBankRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChecklistServiceImpl implements ChecklistService {
	@Autowired
	QuestionBankRepository questionBankRepo;

	/*
	 * this method used to find QuestionBank with auditType
	 * 
	 * @Param String auditType
	 * 
	 * @return ArrayList<QuestionBank> throws InvalidAuditTypeException
	 */
	@Override
	public ArrayList<QuestionBank> findByType(String auditType) throws InvalidAuditTypeException {
		log.info("inside the ChecklistService findByType service : BEGIN");
		if (auditType.equals("SOX") || auditType.equals("Internal")) {
			log.info("AuditType is valid");
			return questionBankRepo.findByAuditType(auditType);
		} else {
			log.error("AuditType is invalid, InvalidAuditTypeException thrown");
			throw new InvalidAuditTypeException("Resquest AuditType doesn't exists");
		}
	}

	/*
	 * this methods saves responses
	 * 
	 * @Param List<QuestionBank> response
	 * 
	 * @return List<QuestionBank> response
	 */
	@Override
	public List<QuestionBank> saveResponse(List<QuestionBank> resp) {
		log.info("inside the ChecklistService saveResponse service : BEGIN");
		return questionBankRepo.saveAll(resp);
	}

}
