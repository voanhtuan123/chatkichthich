package com.globits.cms.rest;

import java.util.UUID;

import com.globits.cms.dto.CmsSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.dto.SearchDto;
import com.globits.cms.dto.WebsiteDto;
import com.globits.cms.service.WebsiteService;

@RestController
@RequestMapping("/api/cms/website")
public class RestWebsiteController {
	@Autowired
	private WebsiteService websiteService;

	// NDuc start
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public WebsiteDto getWebsite(@PathVariable("id") UUID id) {
		return websiteService.getWebsiteById(id);
	}

	@RequestMapping(value="/save", method = RequestMethod.POST)
	public WebsiteDto saveWebsite(@RequestBody WebsiteDto dto) {
		return websiteService.saveWebsite(dto);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteWebsite(@PathVariable("id") UUID id) {
		websiteService.deleteWebsite(id);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);	
	}

	@RequestMapping(value="/check-domain", method = RequestMethod.GET)
	public Boolean duplicateDomain(@RequestParam("domainName") String domainName) {
		return websiteService.duplicateDomain(domainName);
	}

	@RequestMapping(value="/paging", method = RequestMethod.POST)
	public Page<WebsiteDto> paging(@RequestBody CmsSearchDto dto) {
		return websiteService.paging(dto);
	}
	// NDuc end
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteCheckById(@PathVariable("id") UUID id) {
		websiteService.deleteWebsite(id);
		
	}


	@RequestMapping(value = "/checkDomain",method = RequestMethod.GET)
	public ResponseEntity<Boolean> checkDuplicateCode(@RequestParam(value = "id", required=false) UUID id, @RequestParam("domain") String domain) {
		Boolean result = websiteService.checkDuplicateDomain(id, domain);
		return new ResponseEntity<Boolean>(result, (result != null) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
}
