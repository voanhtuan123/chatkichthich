package com.globits.cms.service;

import com.globits.core.service.GenericService;
import com.globits.cms.domain.PageMenuCms;
import com.globits.cms.dto.CmsSearchDto;
import com.globits.cms.dto.PageMenuCmsDto;
import com.globits.cms.dto.SearchDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PageMenuCmsService extends GenericService<PageMenuCms, UUID> {
    // NDuc start
    PageMenuCmsDto getPageMenu(UUID id);

    PageMenuCmsDto savePageMenu(PageMenuCmsDto dto);

    Boolean deletePageMenu(UUID id);

    Page<PageMenuCmsDto> paging(CmsSearchDto dto);

    Boolean checkDuplicateCode(String code);
    // NDuc end
}
