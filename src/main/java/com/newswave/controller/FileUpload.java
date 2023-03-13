package com.newswave.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUpload {
	private static Logger logg = LoggerFactory.getLogger(FileUpload.class);
	
	public final String uploaddir="E:\\FREE LANCER PROJECT\\NewsWave\\newswave\\‪images";
	
	public boolean uploadFile(MultipartFile file,String reporterid) {
		boolean respo = false;
		logg.error("Inside uploadfilr-");
		try {
			//read
			InputStream is = file.getInputStream();
			byte data[] = new byte[is.available()];
			File convertFile = new File(uploaddir+File.separator+reporterid+File.separator+file.getOriginalFilename());
//			File dir = new File(uploaddir+File.separator+reporterid);
//			if(!dir.exists()) {
//				logg.error("Inside mkdirs-");
//				dir.mkdirs();
//			}
			
		    convertFile.createNewFile(); 
			//write
			FileOutputStream fos = new FileOutputStream(convertFile);
			fos.write(file.getBytes());
//			fos.write(data);
			fos.flush();
			fos.close();
			respo = true;
			return respo;
		}
		catch(Exception e) {
			logg.error("Inside error",e);
			
		return false;
		}
	}
}
