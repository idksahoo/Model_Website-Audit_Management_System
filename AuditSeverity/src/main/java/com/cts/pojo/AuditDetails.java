package com.cts.pojo;

import java.util.Date;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuditDetails {
	private String auditType;
	private Date auditDate;
	private List<QuestionBank> auditQuestions;
}
