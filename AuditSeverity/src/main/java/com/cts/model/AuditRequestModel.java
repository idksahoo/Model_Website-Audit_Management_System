package com.cts.model;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "auditrequest")
public class AuditRequestModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RequestId")
	private int requestId;
	@OneToOne(cascade = CascadeType.ALL)
	private AuditDetailModel auditDetail;
	@Column(name = "ProjectName")
	private String projectName;
	@Column(name = "ManagerName")
	private String managerName;
	@Column(name = "OwnerName")
	private String ownerName;

}