package com.globits.cms.rest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.globits.cms.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.service.CmsArticleService;
import com.globits.cms.service.CmsCategoryService;

import com.globits.core.domain.FileDescription;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.core.service.FileDescriptionService;

@RestController
@RequestMapping("/api/cmsfile")
public class RestCmsFileController {
	@Autowired
	private Environment env;
	@Autowired
	FileDescriptionService fileDescriptionService;
	@Autowired
	CmsCategoryService cmsCategoryService;

	@Autowired
	CmsArticleService cmsArticleService;

	@RequestMapping(value = "/image", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FileDescriptionDto> uploadAttachment(@RequestParam("uploadfile") MultipartFile uploadfile) {
		FileDescriptionDto result = null;
		String path = "";
		if (env.getProperty("cms.file.folder") != null) {
			path = env.getProperty("cms.file.folder");
		}
		try { 
//			String extension = uploadfile.getOriginalFilename().split("\\.(?=[^\\.]+$)")[1];
//			UUID randamCode = UUID.randomUUID();
			
			
			
			String pattern = "MM_dd_yyyy_HH_mm_ss_SSS";
			DateFormat df = new SimpleDateFormat(pattern);
			Date today = Calendar.getInstance().getTime();        
			String todayAsString = df.format(today);
			String filename = "";
			String[] parts = uploadfile.getOriginalFilename().split("\\.");
			if(parts != null && parts.length > 0) {
				for(int i = 0; i < parts.length; i++) {
					if(i == parts.length-1) {
						filename = filename.substring(0, filename.length() - 1) + "_" + todayAsString + "." + parts[i];
					} else {
						filename = filename + parts[i] + ".";
					}
				}
			}
			
			
			//String filename = randamCode + "." + extension; 
			String filePath = path;
			try {
				File fileToBeSaved = new File(filePath, filename);
				uploadfile.transferTo(fileToBeSaved); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			FileDescription file = new FileDescription();
			file.setContentSize(uploadfile.getSize());
			file.setContentType(uploadfile.getContentType());
			file.setName(filename);
			file.setFilePath(filePath);
			file = fileDescriptionService.save(file);

			result = new FileDescriptionDto(file);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FileDescriptionDto>(result, HttpStatus.OK);
	}

	// import danh sách
	@RequestMapping(value = "/importCmsArticle", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importCmsArticle(@RequestParam("uploadfile") MultipartFile uploadfile) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
			List<CmsArticleDto> list = ExcelUtil.importCmsArticle(bis);
			if (list != null && list.size() > 0) {
				for (CmsArticleDto dto : list) {
					CmsArticleDto cmsArticleDto = cmsArticleService.saveCmsArticle(dto, null);
				}
			}
		} catch (Exception e) {
			System.out.println('a');
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
