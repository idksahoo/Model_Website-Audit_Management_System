package com.cts.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditBenchmark {
	private int benchmarkId;
	private String auditType;
	private int benchmarkNoAnswers;
}
