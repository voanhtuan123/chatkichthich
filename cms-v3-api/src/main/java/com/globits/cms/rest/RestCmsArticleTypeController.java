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

import com.globits.cms.domain.CmsArticleType;
import com.globits.cms.dto.CmsArticleGroupDto;
import com.globits.cms.dto.CmsArticleTypeDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsArticleTypeService;

@RestController
@RequestMapping("/api/cms/articletype")
public class RestCmsArticleTypeController {
	@Autowired
	private CmsArticleTypeService cmsArticleTypeService;

	@RequestMapping(value = "/{ArticleTypeId}", method = RequestMethod.GET)
	public CmsArticleTypeDto getArticleTypeById(@PathVariable("ArticleTypeId") UUID articleTypeId) {
		CmsArticleType at = cmsArticleTypeService.findById(articleTypeId);
		return new CmsArticleTypeDto(at);
	}

	@RequestMapping(value = "/page/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<CmsArticleTypeDto> getArticleType(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return cmsArticleTypeService.getListArticleType(pageIndex, pageSize);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public CmsArticleTypeDto addArticleType(@RequestBody CmsArticleTypeDto dto) {
		return cmsArticleTypeService.addArticleType(dto);
	}

	@RequestMapping(value = "/removeList", method = RequestMethod.DELETE)
	public boolean removeArticleType(@RequestBody List<CmsArticleTypeDto> dtos) {
		boolean at = cmsArticleTypeService.removeList(dtos);
		return at;
	}


	@RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteCheckById(@PathVariable("id") UUID id) {
		Boolean result = cmsArticleTypeService.deleteCheckById(id);
		if(result) {
			cmsArticleTypeService.delete(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}else {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/checkCode/{code}", method = RequestMethod.GET)
	public CmsArticleTypeDto checkDuplicateCode(@PathVariable("code") String code) {
		return cmsArticleTypeService.checkDuplicateCode(code);
	}
	
	@RequestMapping(value = "/searchByPage", method=RequestMethod.POST)
	public ResponseEntity<Page<CmsArticleTypeDto>> searchByPage(@RequestBody SearchDto searchDto) {
		Page<CmsArticleTypeDto> page = cmsArticleTypeService.searchByPage(searchDto);
		
		return new ResponseEntity<Page<CmsArticleTypeDto>>(page, HttpStatus.OK);
	}
}
