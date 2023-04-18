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
public class AuditRequest {
	private String projectName;
	private String projectManagerName;
	private String applicationOwnerName;
	private AuditDetails auditdetails = new AuditDetails();

}
