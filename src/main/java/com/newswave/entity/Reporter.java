package com.newswave.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="reporter")
@Data
public class Reporter {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "reporter_id")
	private long reporterid;
	
	@Column(name = "reporter_name")
	private String reporterName;
	
	@Column(name = "reporter_unique_id", nullable = false, unique = true)
	private String uniqueId;
	
	@Column(name="reporter_password")
	private String password;
	
	@Column(name="reporter_is_approved")
	private String isApproved;
	
	
}
