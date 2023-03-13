package com.newswave.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.criteria.Path;

@Component
public class FileUploader {

	public final String uploaddir="‪D:\\Testing";
	
	public boolean uploadFile(MultipartFile file) {
		boolean respo = false;
		
		try {
			//read
			InputStream is = file.getInputStream();
			byte data[] = new byte[is.available()];
			
			//write
			FileOutputStream fos = new FileOutputStream(uploaddir+File.separator+file.getOriginalFilename());
			
			fos.write(data);
			fos.flush();
			fos.close();
			respo = true;
			return respo;
		}
		catch(Exception e) {
		return false;
		}
	}
}
