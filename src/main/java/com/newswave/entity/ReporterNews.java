package com.newswave.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="reporter_news")
@Data
public class ReporterNews {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="news_id")
	private long id;
	
	@Column(name="news_reporter_id")
	private String reporterid;
	
	@Column(name="news_reporter_name")
	private String newsReporterName;
	
	@Column(name="news_title")
	private String title;
	
	@Column(name="news_district")
	private String district;
	
	@Column(name="news_content" , length = 3000)
	private String newsContent;
	
	@Column(name="file_uploaded1")
	private String filePath;
	
	@Column(name="logged_date")
	private LocalDateTime loggedDate;	
	
	
}
