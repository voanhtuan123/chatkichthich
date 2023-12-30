package com.globits.cms.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsLocationFocalPointsDto;
import com.globits.cms.dto.CmsLocationFocalPointsSearchDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.repository.CmsLocationFocalPointsRepository;

import com.globits.cms.service.CmsLocationFocalPointsService;


@RestController
@RequestMapping("/api/cms/locationfocalpoints")
public class RestCmsLocationFocalPointsController {
	@Autowired
	CmsLocationFocalPointsService cmsLocationFocalPointsService;
	

	@RequestMapping(value = "/searchByPage", method = RequestMethod.POST)
	public ResponseEntity<Page<CmsLocationFocalPointsDto>> searchByPage(@RequestBody CmsLocationFocalPointsSearchDto searchDto) {
		Page<CmsLocationFocalPointsDto> page = cmsLocationFocalPointsService.searchByPage(searchDto);
		
		return new ResponseEntity<Page<CmsLocationFocalPointsDto>>(page, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<CmsLocationFocalPointsDto> getOne(@PathVariable("id") UUID id) {
		CmsLocationFocalPointsDto dto = cmsLocationFocalPointsService.getOne(id);
		
		return new ResponseEntity<CmsLocationFocalPointsDto>(dto, HttpStatus.OK);
	}
	

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<CmsLocationFocalPointsDto> saveOne(@RequestBody CmsLocationFocalPointsDto dto) {
		CmsLocationFocalPointsDto result = cmsLocationFocalPointsService.saveOneOrUpdate(dto, null);
		
		return new ResponseEntity<CmsLocationFocalPointsDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CmsLocationFocalPointsDto> updateOne(@RequestBody CmsLocationFocalPointsDto dto, @PathVariable("id") UUID id) {
		CmsLocationFocalPointsDto result = cmsLocationFocalPointsService.saveOneOrUpdate(dto, id);
		
		return new ResponseEntity<CmsLocationFocalPointsDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		cmsLocationFocalPointsService.deleteById(id);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);			
	}
	


	
}
