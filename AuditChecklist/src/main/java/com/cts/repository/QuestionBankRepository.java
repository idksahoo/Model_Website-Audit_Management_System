package com.cts.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.entity.QuestionBank;

@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank, Integer> {
	// Retrieves the data from the database by searching using the auditType
	ArrayList<QuestionBank> findByAuditType(String auditType);
}
