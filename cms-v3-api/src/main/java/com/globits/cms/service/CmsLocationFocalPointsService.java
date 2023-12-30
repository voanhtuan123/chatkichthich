package com.globits.cms.service;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.cms.domain.CmsLocationFocalPoints;
import com.globits.cms.dto.CmsLocationFocalPointsDto;
import com.globits.cms.dto.CmsLocationFocalPointsSearchDto;
import com.globits.core.service.GenericService;


public interface CmsLocationFocalPointsService extends GenericService<CmsLocationFocalPoints, UUID> {

	public Page<CmsLocationFocalPointsDto> searchByPage(CmsLocationFocalPointsSearchDto searchDto);

	public CmsLocationFocalPointsDto getOne(UUID id);

	public CmsLocationFocalPointsDto saveOneOrUpdate(CmsLocationFocalPointsDto dto, UUID id);

	public void deleteById(UUID id);

}
