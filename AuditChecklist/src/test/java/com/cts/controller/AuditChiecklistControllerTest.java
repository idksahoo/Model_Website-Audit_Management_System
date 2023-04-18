package com.cts.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MissingRequestHeaderException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.cts.client.AuthClient;
import com.cts.entity.QuestionBank;
import com.cts.exceptions.InvalidAuditTypeException;
import com.cts.service.ChecklistService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(value = ChecklistController.class)
public class AuditChiecklistControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ChecklistService checklistService;

	@MockBean
	AuthClient authClient;

	ArrayList<QuestionBank> list = new ArrayList<QuestionBank>();

	@BeforeEach
	public void setup() {
		list.add(new QuestionBank(1, "Internal", "Have all Change requests followed SDLC before PROD move?", "Yes"));
		list.add(new QuestionBank(2, "Internal", "Is the SIT and UAT sign-off available?", "No"));
	}

	// test for authClient NotNull
	@Test
	@DisplayName("Test Authorising client")
	void testClientNotNull() {
		assertThat(authClient).isNotNull();
	}

	// test for Mock MVC client NotNull
	@Test
	@DisplayName("Test Mock MVC client")
	void testMockMvcNotNull() {
		assertThat(mockMvc).isNotNull();
	}

	// test for ChecklistService client NotNull
	@Test
	@DisplayName("Test ChecklistService client")
	void testServiceNotNull() {
		assertThat(checklistService).isNotNull();
	}

	// Test getQuestions of ChecklistController
	@Test
	@DisplayName("Test getQuestions of ChecklistController")
	public void testGetQuestions() throws InvalidAuditTypeException, Exception {
		when(authClient.validatingAuthorizationToken("token")).thenReturn(true);
		when(checklistService.findByType("Internal")).thenReturn(list);
		this.mockMvc.perform(get("/AuditCheckListQuestions/{auditType}", "Internal").header("Authorization", "token"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	// Test getQuestions of ChecklistController with invalid audit type
	@Test
	@DisplayName("Test getQuestions of ChecklistController with invalid audit type ")
	public void testInvalidGetQuestions() throws InvalidAuditTypeException, Exception {
		when(authClient.validatingAuthorizationToken("token")).thenReturn(true);
		when(checklistService.findByType("zjjsgju"))
				.thenThrow(new InvalidAuditTypeException("Resquest AuditType doesn't exists"));
		this.mockMvc.perform(get("/AuditCheckListQuestions/{auditType}", "zjjsgju").header("Authorization", "token"))
				.andExpect(status().isNotFound());
	}

	// Test getQuestions of ChecklistController with invalid token
	@Test
	@DisplayName("Test getQuestions of ChecklistController with invalid token")
	public void testGetQuestionsInvalidToken() throws InvalidAuditTypeException, Exception {
		when(authClient.validatingAuthorizationToken("wrongtoken")).thenReturn(false);
		this.mockMvc
				.perform(get("/AuditCheckListQuestions/{auditType}", "Internal").header("Authorization", "wrongtoken"))
				.andExpect(status().isForbidden());
	}

	// Test getQuestions of ChecklistController without token
	@Test
	@DisplayName("Test getQuestions of ChecklistController without token")
	public void testGetQuestionsWithoutToken() throws InvalidAuditTypeException, Exception {
		this.mockMvc.perform(get("/AuditCheckListQuestions/{auditType}", "Internal"))
				.andExpect(status().isBadRequest());
	}

	/*
	 * -------------------------test ---------------------------------
	 * saveResponses--------------------------------------------
	 */
	// Test saveResponses of ChecklistController
	@Test
	@DisplayName("Test saveResponses of ChecklistController")
	public void testSaveResponses() throws Exception {
		ArrayList<QuestionBank> list1 = new ArrayList<QuestionBank>();
		list1.add(new QuestionBank(3, "Internal", "Have all Change requests followed SDLC before PROD move?", "Yes"));
		list1.add(new QuestionBank(4, "Internal", "Is the SIT and UAT sign-off available?", "No"));
		when(authClient.validatingAuthorizationToken("token")).thenReturn(true);
		when(checklistService.saveResponse(list1)).thenReturn(list1);
		this.mockMvc
				.perform(post("/saveResponse").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(list1)).header("Authorization", "token"))
				.andExpect(status().isOk());
	}

	// Test saveResponses of ChecklistController with invalid token
	@Test
	@DisplayName("Test saveResponses of ChecklistController with invalid token")
	public void testSaveResponsesInvalidToken() throws Exception {
		when(authClient.validatingAuthorizationToken("wrongtoken")).thenReturn(false);
		this.mockMvc
				.perform(post("/saveResponse").contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(list)).header("Authorization", "wrongtoken"))
				.andExpect(status().isForbidden());
	}

	// Test SaveResponses of ChecklistController without token
	@Test
	@DisplayName("Test SaveResponses of ChecklistController without token")
	public void testSaveResponsesWithoutToken() throws MissingRequestHeaderException, Exception {
		this.mockMvc.perform(post("/saveResponse")).andExpect(status().isBadRequest());
	}

}
