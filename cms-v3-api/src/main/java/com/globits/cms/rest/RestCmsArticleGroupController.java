package com.globits.cms.rest;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.dto.CmsArticleGroupDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsArticleGroupService;
import com.globits.core.service.FileDescriptionService;

@RestController
@RequestMapping("/api/cms/articleGroup")
public class RestCmsArticleGroupController {
	@Autowired
	private CmsArticleGroupService cmsArticleGroupService;
	
	@Autowired
	private FileDescriptionService fileService;

	@RequestMapping(value = "/searchByPage", method=RequestMethod.POST)
	public ResponseEntity<Page<CmsArticleGroupDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<CmsArticleGroupDto> page = cmsArticleGroupService.searchByPage(searchDto);
		
		return new ResponseEntity<Page<CmsArticleGroupDto>>(page, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<CmsArticleGroupDto> getOne(@PathVariable("id") UUID id) {
		CmsArticleGroupDto dto = cmsArticleGroupService.getOne(id);
		
		return new ResponseEntity<CmsArticleGroupDto>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<CmsArticleGroupDto> saveOne(@RequestBody CmsArticleGroupDto dto) {
		CmsArticleGroupDto result = cmsArticleGroupService.saveOrUpdate(dto, null);
		
		return new ResponseEntity<CmsArticleGroupDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CmsArticleGroupDto> updateOne(@RequestBody CmsArticleGroupDto dto, @PathVariable("id") UUID id) {
		CmsArticleGroupDto result = cmsArticleGroupService.saveOrUpdate(dto, id);
		
		return new ResponseEntity<CmsArticleGroupDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		cmsArticleGroupService.deleteById(id);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);			
	}
}
