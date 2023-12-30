package com.globits.cms.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.cms.domain.CmsPaneDataConfig;
import com.globits.cms.dto.CmsPaneDataConfigDto;
import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;

public interface CmsPaneDataConfigService extends GenericService<CmsPaneDataConfig, UUID>{

	Page<CmsPaneDataConfigDto> searchByPage(SearchDto dto);
	
	CmsPaneDataConfigDto getOne(UUID id);
	
	CmsPaneDataConfigDto saveOrUpdate(CmsPaneDataConfigDto dto, UUID id);
	
	void deleteById(UUID id);
}
