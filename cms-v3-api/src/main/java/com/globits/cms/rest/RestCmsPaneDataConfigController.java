package com.globits.cms.rest;

import java.util.List;
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
import com.globits.cms.dto.CmsPaneDataConfigDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.repository.CmsPaneDataConfigRepository;
import com.globits.cms.service.CmsPaneDataConfigService;
@RestController
@RequestMapping("/api/cmsPaneDataConfig")
public class RestCmsPaneDataConfigController {
	@Autowired
	private CmsPaneDataConfigService cmsPaneDataConfigService;
	
	@Autowired
	private CmsPaneDataConfigRepository cmsPaneDataConfigRepository;
	
	@RequestMapping(value="/getall", method = RequestMethod.GET)
	public List<CmsPaneDataConfigDto> getAll() {
		return cmsPaneDataConfigRepository.getAll();
	}
	@RequestMapping(value = "/searchByPage", method=RequestMethod.POST)
	public ResponseEntity<Page<CmsPaneDataConfigDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<CmsPaneDataConfigDto> page = cmsPaneDataConfigService.searchByPage(searchDto);
		
		return new ResponseEntity<Page<CmsPaneDataConfigDto>>(page, HttpStatus.OK);
	}
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<CmsPaneDataConfigDto> getOne(@PathVariable("id") UUID id) {
		CmsPaneDataConfigDto dto = cmsPaneDataConfigService.getOne(id);
		
		return new ResponseEntity<CmsPaneDataConfigDto>(dto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<CmsPaneDataConfigDto> saveOne(@RequestBody CmsPaneDataConfigDto dto) {
		CmsPaneDataConfigDto result = cmsPaneDataConfigService.saveOrUpdate(dto, null);
		
		return new ResponseEntity<CmsPaneDataConfigDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CmsPaneDataConfigDto> updateOne(@RequestBody CmsPaneDataConfigDto dto, @PathVariable("id") UUID id) {
		CmsPaneDataConfigDto result = cmsPaneDataConfigService.saveOrUpdate(dto, id);
		
		return new ResponseEntity<CmsPaneDataConfigDto>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteById(@PathVariable("id") UUID id) {
		cmsPaneDataConfigService.deleteById(id);
		
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);			
	}
}
