package com.newswave.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.newswave.entity.ReporterNews;

@Repository
public interface NewsRepositry extends JpaRepository<ReporterNews, Integer>{
	
	@Query("select n from ReporterNews n where n.district=?1")
	List<ReporterNews> findBydistrict(String district);
	

}
