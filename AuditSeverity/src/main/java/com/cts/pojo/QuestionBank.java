package com.cts.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class QuestionBank {

	private int questionId;
	private String auditType;
	private String question;
	private String response;

}
