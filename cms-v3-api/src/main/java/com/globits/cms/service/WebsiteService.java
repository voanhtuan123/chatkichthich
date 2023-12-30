package com.globits.cms.service;

import java.util.UUID;

import com.globits.cms.dto.CmsSearchDto;
import org.springframework.data.domain.Page;

import com.globits.cms.domain.Website;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.dto.WebsiteDto;
import com.globits.core.service.GenericService;

public interface WebsiteService extends GenericService<Website, UUID> {
    // NDuc start
    WebsiteDto getWebsiteById(UUID id);

    WebsiteDto saveWebsite(WebsiteDto dto);

    Boolean deleteWebsite(UUID id);

    Boolean duplicateDomain(String domain);

    Page<WebsiteDto> paging(CmsSearchDto dto);

    // NDuc end
    WebsiteDto getWebsite(String domainName);

    Boolean checkDuplicateDomain(UUID id, String code);
}
