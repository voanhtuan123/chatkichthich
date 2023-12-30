package com.globits.cms.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.cms.domain.CmsArticleType;
import com.globits.cms.dto.CmsArticleTypeDto;
import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;

public interface CmsArticleTypeService extends GenericService<CmsArticleType, UUID> {

	Page<CmsArticleTypeDto> getListArticleType(int pageIndex, int pageSize);

	CmsArticleTypeDto addArticleType(CmsArticleTypeDto dto);

	//CmsArticleType findById(UUID id);

	boolean removeList(List<CmsArticleTypeDto> dtos);
	
	CmsArticleTypeDto remove(UUID id);

	CmsArticleTypeDto checkDuplicateCode(String code);
	Boolean deleteCheckById(UUID id);
	Page<CmsArticleTypeDto> searchByPage(SearchDto dto);
}
