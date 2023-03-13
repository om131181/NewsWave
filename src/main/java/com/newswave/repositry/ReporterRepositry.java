package com.newswave.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.newswave.entity.Reporter;

@Repository
public interface ReporterRepositry extends JpaRepository<Reporter, Integer>{
	
	List<Reporter> findByuniqueId(String uniqueId);
	
}
