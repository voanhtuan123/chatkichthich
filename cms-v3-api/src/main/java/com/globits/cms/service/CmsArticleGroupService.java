package com.globits.cms.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.cms.domain.CmsArticleGroup;
import com.globits.cms.dto.CmsArticleGroupDto;
import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;

public interface CmsArticleGroupService extends GenericService<CmsArticleGroup, UUID>{

	Page<CmsArticleGroupDto> searchByPage(SearchDto dto);
	
	CmsArticleGroupDto getOne(UUID id);
	
	CmsArticleGroupDto saveOrUpdate(CmsArticleGroupDto dto, UUID id);
	
	void deleteById(UUID id);

}
