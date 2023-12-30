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

import com.globits.cms.dto.CmsPageDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsPageService;

@RestController
@RequestMapping("/api/cmsPage")
public class RestCmsPageController {

	@Autowired
	CmsPageService cmsPageService;
	
	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<CmsPageDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<CmsPageDto> page = cmsPageService.searchByPage(searchDto);
		
		return new ResponseEntity<Page<CmsPageDto>>(page, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CmsPageDto> getOne(@PathVariable("id") UUID id) {
		CmsPageDto dto = cmsPageService.getOne(id);
		
		return new ResponseEntity<CmsPageDto>(dto, HttpStatus.OK);
	}
	
	//add
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<CmsPageDto> saveOne(@RequestBody CmsPageDto dto) {
		CmsPageDto result = cmsPageService.saveOneOrUpdate(dto, null);
		
		return new ResponseEntity<CmsPageDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CmsPageDto> updateOne(@RequestBody CmsPageDto dto, @PathVariable("id") UUID id) {
		CmsPageDto result = cmsPageService.saveOneOrUpdate(dto, id);
		
		return new ResponseEntity<CmsPageDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		cmsPageService.deleteById(id);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);			
	}
	
}
