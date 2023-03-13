package com.newswave.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newswave.controller.MainController;
import com.newswave.entity.Reporter;
import com.newswave.repositry.ReporterRepositry;

@Service
public class ReporterService {
	private static Logger logg = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private ReporterRepositry reporterRepositry;

	public List<Reporter> getAllReporter(){
		return reporterRepositry.findAll();
	}

	public List<Reporter> getAllReporterById(String uniqueid){
		return reporterRepositry.findByuniqueId(uniqueid);
	}


	public String updateReporterStatus(String status,String reporterid) {
		List<Reporter> reporterList = getAllReporterById(reporterid);
		if(reporterList.size()>0) {
			Reporter reporter = reporterList.get(0);
			if(reporter!=null) {
				reporter.setIsApproved(status);
			}
			reporterRepositry.save(reporter);
		}
		return "";
	}

	public HashMap<String,Object> addReporter(Reporter reporter) {
		HashMap<String, Object> response = new HashMap<>();
		List<Reporter> repo = reporterRepositry.findByuniqueId(reporter.getUniqueId());

		if(repo.size()>0) {
			response.put("status", "Reporter with this unique id is already present");
		}
		else {
			response.put("status", "200");
			response.put("reporter",reporterRepositry.save(reporter));
		}
		return response;
	}
}
