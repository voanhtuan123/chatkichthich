package com.globits.cms.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsMapObjectDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsArticleService;
import com.globits.cms.service.CmsCategoryService;
import com.globits.cms.service.CmsMapObjectService;

@RestController
@RequestMapping("/public/cms/mapobject")
public class RestCmsMapObjectController {
	
	@Autowired
	private CmsMapObjectService cmsMapObjectService;
	@Autowired
	private CmsCategoryService cmsCategoryService;
	@Autowired
	private CmsArticleService cmsArticleService;
	@RequestMapping(value = "/{key}", method=RequestMethod.GET)
	public CmsMapObjectDto getCmsMapByKey(@PathVariable String key) {
		return cmsMapObjectService.getCmsMapByKey(key);
	}
	@RequestMapping(method=RequestMethod.GET)
	public CmsMapObjectDto getHomePage() {
		return cmsMapObjectService.getHomePage();
	}
	
	@RequestMapping(value = "/home/{websiteId}", method=RequestMethod.GET)
	public CmsMapObjectDto getHomePageById(@PathVariable UUID websiteId) {
		return cmsMapObjectService.getHomePage(websiteId);
	}
	@RequestMapping(value = "/{key}/{pageIndex}/{pageSize}", method=RequestMethod.GET)
	public CmsMapObjectDto getPageCmsMapByKey(
												@PathVariable String key,
												@PathVariable int pageIndex, 
												@PathVariable int pageSize
												) { 
		/*
		 * CmsMapObjectDto dto =
		 * cmsMapObjectService.getPageCmsMapByKey(key,pageIndex,pageSize);
		 */
		return cmsMapObjectService.getPageCmsMapByKey(key,pageIndex,pageSize);
	}
	
	/*
	 * @RequestMapping(value="/all/parentisnull", method = RequestMethod.GET) public
	 * List<CmsCategoryDto> getAllByParentNull() { return
	 * cmsCategoryService.getAllByParentNull(); }
	 */
	@RequestMapping(value = "/search/{search}/{pageIndex}/{pageSize}", method=RequestMethod.GET)
	public CmsMapObjectDto getSearchResultByKeyword(
													@PathVariable String search,
													@PathVariable int pageIndex,
													@PathVariable int pageSize
													) { 
		SearchDto dto = new SearchDto();
		CmsMapObjectDto mapObjDto = new CmsMapObjectDto();
		dto.setKeyword(search);
		dto.setPageIndex(pageIndex);
		dto.setPageSize(pageSize);
		Page<CmsArticleDto> data = cmsArticleService.searchByName(dto);
		mapObjDto.setArticles(data);
		return mapObjDto;
	}
	
	
}
