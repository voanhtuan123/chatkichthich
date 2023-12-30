package com.globits.cms.service;

import java.util.List;
import java.util.UUID;

import com.globits.cms.domain.CmsMapObject;
import com.globits.cms.dto.CmsMapObjectDto;
import com.globits.core.service.GenericService;

public interface CmsMapObjectService extends GenericService<CmsMapObject, UUID>{
	CmsMapObjectDto getCmsMapByKey(String key);

	CmsMapObjectDto getHomePage();

	CmsMapObjectDto getHomePage(UUID websiteId);
	
	public CmsMapObjectDto getPageCmsMapByKey(String key,int pageIndex,int pageSize);

	//CmsMapObjectDto getSearchResultByKeyword(String keyword);
}
