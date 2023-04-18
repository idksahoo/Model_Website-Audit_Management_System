package com.cts.service;

import java.util.ArrayList;
import java.util.List;


import com.cts.entity.QuestionBank;
import com.cts.exceptions.InvalidAuditTypeException;

public interface ChecklistService {
	ArrayList<QuestionBank> findByType(String auditType) throws InvalidAuditTypeException;

	List<QuestionBank> saveResponse(List<QuestionBank> resp);
}
