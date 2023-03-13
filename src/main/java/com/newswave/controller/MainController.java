package com.newswave.controller;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.newswave.entity.Reporter;
import com.newswave.entity.ReporterNews;
import com.newswave.repositry.NewsRepositry;
import com.newswave.repositry.ReporterRepositry;
import com.newswave.service.ReporterService;


@Controller
public class MainController {

	private static Logger logg = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private ReporterService reporterService;

	@Autowired
	private NewsRepositry newsRepositry;

	@Autowired
	private ReporterRepositry reporterRepositry;
	@Autowired
	private FileUpload fileUploader;

	@GetMapping("/reporter")
	public List<Reporter> getReporter() {
		return reporterService.getAllReporter();
	}

	@GetMapping("/admin")
	public String getReporter(Model model) {
		model.addAttribute("reporters", reporterService.getAllReporter());
		return "admin";
	}

	@GetMapping("/register")
	public String registerReporter(Model model) {
		Reporter rep = new Reporter();
		model.addAttribute("reporter", rep);
		return "register";
	}


	@PostMapping("reporter/reject/{id}")
	public String rejectReporter(@PathVariable ("id") String id,Model model) {
		String status = "N";
		reporterService.updateReporterStatus(status, id);
		return "redirect:/admin";
	}

	@PostMapping("reporter/approve/{id}")
	public String approveReporter(@PathVariable ("id") String id,Model model) {
		String status = "Y";
		reporterService.updateReporterStatus(status, id);
		return "redirect:/admin";
	}


	@GetMapping("/reporter/login")
	public String loginReporter(@RequestParam (name="pass",required = false) String pass,@RequestParam (required = false,name="reporterid") String reporterId,Model model) {
		HashMap<String,Object> response = new HashMap<String,Object>();
		//		List<Reporter> rep = reporterService.getAllReporterById(reporterId);
		logg.error("Inside loginreporter");
		//		String reporterId = Objects.toString(model.getAttribute("reporterid"),"");
		//		String reporterId = obj.getReporterid();
		//		String pass = obj.getPassword();
		//		String pass = Objects.toString(model.getAttribute("pass"),"");
		logg.error(reporterId);logg.error(pass);
		List<Reporter> rep = reporterService.getAllReporterById(reporterId);
		if(rep.isEmpty()) {
			logg.error("Inside rep is empty");
			response.put("status", "no such user present");
			model.addAttribute("errorforsignup", "Incorrect id and password");

			return "reporter_login.html";
		}
		else {
			Reporter rep1 = rep.get(0);
			String password = rep1.getPassword();
			logg.error("Inside rep1"+rep1);

			if(pass.equals(password)) {
				response.put("reporter", rep1);
				response.put("status", "success");
				model.addAttribute("reporter",rep1);
				return "reporter_home_page";
			}
			else {
				return "reporter_login.html";
			}
		}

	}

	@GetMapping("/")
	public String getLoginPage(Model model) {
		return "reporter_login";
	}

	@PostMapping("/reporter")
	public String addReporter(@ModelAttribute("reporter") Reporter reporter) {
		HashMap<String,Object> response=  reporterService.addReporter(reporter);
		return "redirect:/";
	}

	@PostMapping("/reporter/publish")
	public ResponseEntity<?> publishNews(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value= "newsContent",required = false) String newsContent,
			@RequestParam(value="conclusion_paragraph",required = false) String conclusion_paragraph,
			@RequestParam(value="headline",required = false) String headline,
			@RequestParam(value="district",required = false) String district,
			@RequestParam(value="reporterid",required = false) String reporterid,
			@RequestParam(value="reportername",required = false) String reportername){

		//	
		//	public String publishNews(@ModelAttribute("news") ReporterNews news,@RequestParam (name="file")MultipartFile file){

		logg.error("inside publish news"+newsContent.toString());
		List<Reporter> rep = reporterService.getAllReporterById(reporterid);
		if(rep.size()>0) {
			if(rep.get(0).getIsApproved().equalsIgnoreCase("N")) 
				return ResponseEntity.ok().body("success");
			//									return "not_allowed";
		}

		if(file.isEmpty())
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File must be there");

		if(!file.getContentType().equals("image/jpeg"))
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Only image files are allowed");

		boolean isuploaded = fileUploader.uploadFile(file,reporterid);
		ReporterNews repnews = new ReporterNews();
		repnews.setDistrict(district);
		repnews.setLoggedDate(LocalDateTime.now());
		repnews.setNewsContent(newsContent);
		repnews.setTitle(headline);
		repnews.setReporterid(reporterid);

		repnews.setNewsReporterName(reportername);
		repnews.setFilePath(fileUploader.uploaddir+File.separator+file.getOriginalFilename());
		newsRepositry.save(repnews);
		return ResponseEntity.ok("successs");
		//			return "register_home_page";

	}
	@GetMapping("/fetch/news/{district}")
	public String getNews(@PathVariable("district") String district,Model model){

		List<ReporterNews> news = newsRepositry.findBydistrict(district);
		logg.error(",ksmslcm sd"+news.toString());
		model.addAttribute("newslist", news);
		return "viewnews.html";

	}
}
