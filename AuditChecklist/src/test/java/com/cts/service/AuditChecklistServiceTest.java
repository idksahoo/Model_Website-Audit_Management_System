package com.cts.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.entity.QuestionBank;
import com.cts.exceptions.InvalidAuditTypeException;
import com.cts.repository.QuestionBankRepository;
import static org.mockito.ArgumentMatchers.any;

public class AuditChecklistServiceTest {
	@Mock
	QuestionBankRepository questionBankRepo;

	@InjectMocks
	ChecklistServiceImpl checklistServiceImpl;

	@SuppressWarnings("deprecation")
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	ArrayList<QuestionBank> list = new ArrayList<QuestionBank>();

	@BeforeEach
	public void setup() {
		list.add(new QuestionBank(1, "Internal", "Have all Change requests followed SDLC before PROD move?", "Yes"));
		list.add(new QuestionBank(2, "Internal", "Is the SIT and UAT sign-off available?", "No"));
	}

	// Test for FindByType
	@Test
	public void testFindByType() {
		when(questionBankRepo.findByAuditType("Internal")).thenReturn(list);
		assertTrue((checklistServiceImpl.findByType("Internal")).equals(list));
	}

	// Test for invalid FindByType
	@Test
	public void testInvalidFindByType() {
		when(questionBankRepo.findByAuditType(any())).thenThrow(InvalidAuditTypeException.class);
		assertThrows(InvalidAuditTypeException.class, () -> {
			checklistServiceImpl.findByType("mokjijoih");
		});
	}

	// Test for Save Responses End Point
	@Test
	public void testSaveResponse() {
		when(questionBankRepo.saveAll(list)).thenReturn(list);
		assertTrue((checklistServiceImpl.saveResponse(list)).equals(list));
	}
}
