package com.cts.pojo;

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
public class AuditResponse {
	private int auditId;
	private String projectExecutionStatus;
	private String remedialActionDuration;

}
