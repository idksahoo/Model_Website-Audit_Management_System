package com.cts.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Question_Bank")
public class QuestionBank {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int questionId;

	private String auditType;
	private String question;
	private String response;
}
