package com.globits.cms.rest;

import com.globits.core.domain.FileDescription;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.core.service.FileDescriptionService;
import com.globits.cms.Const;
import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.CmsSearchDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.UUID;

@RestController
@RequestMapping("/api/cms/article")
public class RestCmsArticleController {
	@Autowired
	private CmsArticleService cmsArticleService;
	
	@Autowired
	private FileDescriptionService fileService;

	// NMDuc start
	@RequestMapping(value="/{articleId}", method = RequestMethod.GET)
	public CmsArticleDto getArticle(@PathVariable("articleId") UUID id) {
		return cmsArticleService.getArticle(id);
	}

	@RequestMapping(value="/save", method = RequestMethod.POST)
	public CmsArticleDto saveArticle(@RequestBody CmsArticleDto dto) {
		return cmsArticleService.saveArticle(dto);
	}

	@RequestMapping(value="/{articleId}", method = RequestMethod.DELETE)
	public Boolean deleteArticle(@PathVariable("articleId") UUID id) {
		return cmsArticleService.deleteArticle(id);
	}

	@RequestMapping(value="/paging", method = RequestMethod.POST)
	public Page<CmsArticleDto> paging(@RequestBody CmsSearchDto dto) {
		return cmsArticleService.paging(dto);
	}
	// NMDuc end

	@RequestMapping(value="/page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CmsArticleDto> getPageCmsArticleDto(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		return cmsArticleService.getPageCmsArticleDto(pageIndex, pageSize);
	}
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public CmsArticleDto createCmsArticleDto(@RequestBody CmsArticleDto dto) {
		return cmsArticleService.saveCmsArticle(dto, null);
	}
	
	@RequestMapping(value="/update/{articleId}", method = RequestMethod.PUT)
	public CmsArticleDto updateCmsArticleDto(@RequestBody CmsArticleDto dto, @PathVariable("articleId")UUID id) {
		return cmsArticleService.saveCmsArticle(dto, id);
	}
	
	@RequestMapping(value="/delete/{articleId}", method = RequestMethod.DELETE)
	public Boolean deleteCmsArticleById(@PathVariable("articleId")UUID id) {
		return cmsArticleService.deleteCmsArticleById(id);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public Page<CmsArticleDto> searchArticle(@RequestBody SearchDto search) {
		return cmsArticleService.searchArticleServiceBySearchDto(search);
	}
	
	@RequestMapping(value = "/searchbydto", method = RequestMethod.POST)
	public Page<CmsArticleDto> searchByDto(@RequestBody SearchDto search) {
		return cmsArticleService.searchByDto(search);
	}
	
	
	@RequestMapping(value = "/uploadattachment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FileDescriptionDto> uploadAttachment(@RequestParam("uploadfile") MultipartFile uploadfile) {
		FileDescriptionDto result = null;
		
		try {
			String filePath = uploadfile.getOriginalFilename().toLowerCase().replaceAll("[^a-zA-Z0-9_]", "_");
			filePath =Const.PathFileImage+filePath;
			FileOutputStream stream = new FileOutputStream(filePath);
			try {
			    stream.write(uploadfile.getBytes());
			} finally {
			    stream.close();
			}
			
			FileDescription file = new FileDescription();
			file.setContentSize(uploadfile.getSize());
			file.setContentType(uploadfile.getContentType());
			file.setName(uploadfile.getOriginalFilename());
			file.setFilePath(filePath);
			file = fileService.save(file);
			FileDescriptionDto fileDto = new FileDescriptionDto(file);
			result = fileDto;	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FileDescriptionDto>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<FileDescriptionDto> getFileById(@PathVariable("id") UUID fileId) {
		FileDescriptionDto result = null;
		
		try {
			FileDescription file = null;
			file = fileService.findById(fileId);
			if(file == null) {
				return new ResponseEntity<FileDescriptionDto>(new FileDescriptionDto(new FileDescription()) ,HttpStatus.BAD_REQUEST);
			}
			file = fileService.save(file);
			FileDescriptionDto fileDto = new FileDescriptionDto(file);
			result = fileDto;	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<FileDescriptionDto>(result,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/checkSlug",method = RequestMethod.POST)
	public ResponseEntity<Boolean> checkDuplicateCode(@RequestBody SearchDto dto) {
		Boolean result = cmsArticleService.checkSlug(dto.getId(), dto.getSlug());
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
}
