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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit_benchmark")
public class AuditBenchmark {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int benchmarkId;
	private String auditType;
	private int benchmarkNoAnswers;
}
