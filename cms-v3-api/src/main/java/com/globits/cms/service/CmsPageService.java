package com.globits.cms.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.cms.domain.CmsPage;
import com.globits.cms.dto.CmsPageDto;
import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;

public interface CmsPageService extends GenericService<CmsPage, UUID>{

	public Page<CmsPageDto> searchByPage(SearchDto searchDto);

	public CmsPageDto getOne(UUID id);
	

	public CmsPageDto saveOneOrUpdate(CmsPageDto dto, UUID id);

	public void deleteById(UUID id);
	
	
	public CmsPageDto getPageByTemplatePath(String templatePath);
}
