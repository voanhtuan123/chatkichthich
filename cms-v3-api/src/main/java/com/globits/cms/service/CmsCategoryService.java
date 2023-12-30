package com.globits.cms.service;

import java.util.List;
import java.util.UUID;

import com.globits.cms.dto.CmsSearchDto;
import org.springframework.data.domain.Page;

import com.globits.cms.domain.CmsCategory;
import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.SearchDto;
import com.globits.core.service.GenericService;

public interface CmsCategoryService extends GenericService<CmsCategory, UUID> {
    //	NDuc start
    CmsCategoryDto getCmsCategory(UUID id);

    CmsCategoryDto saveCmsCategory(CmsCategoryDto dto);

    Boolean deleteCmsCategory(UUID id);

    Page<CmsCategoryDto> paging(CmsSearchDto dto);

    Page<CmsCategoryDto> pagingParentAndChild(CmsSearchDto dto);


    // NDuc end

    List<CmsCategoryDto> getAllByParentNull(Boolean getIsHoverArticle);
}
