package com.cts.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *  
 * 	
 *
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auditdetail")
public class AuditDetailModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AuditId")
	private int auditId;
	@Column(name = "AuditType")
	private String auditType;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "auditdate")
	private Date auditDate;

	// private List<QuestionBank> auditQuestions;

	/*
	 * @Temporal(TemporalType.DATE)
	 * 
	 * @Column(name="AuditDate") private Date auditDate;
	 */

}