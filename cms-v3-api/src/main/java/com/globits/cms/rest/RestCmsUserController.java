package com.globits.cms.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.dto.CmsUserDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsUserService;
import com.globits.security.dto.UserDto;

@RestController
@RequestMapping("/api/cmsUser")
public class RestCmsUserController {
	
	@Autowired
	private CmsUserService service;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<CmsUserDto> getOne(@PathVariable("id") Long id) {
		CmsUserDto dto = service.getById(id);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/searchByPage")
	public ResponseEntity<?> searchByPage(@RequestBody SearchDto userSearchDto) {
		Page<UserDto> userPage = service.searchByPage(userSearchDto);

		return new ResponseEntity<>(userPage, userPage != null? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
