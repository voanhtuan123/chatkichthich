package com.globits.cms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsSearchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.cms.domain.PageMenuCms;
import com.globits.cms.domain.Website;
import com.globits.cms.dto.PageMenuCmsDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.repository.PageMenuCmsRepository;
import com.globits.cms.repository.WebsiteRepository;
import com.globits.cms.service.PageMenuCmsService;
import com.globits.core.service.impl.GenericServiceImpl;

@Service
public class PageMenuCmsServiceImpl extends GenericServiceImpl<PageMenuCms, UUID> implements PageMenuCmsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsArticleServiceImpl.class);

	@Autowired
	private PageMenuCmsRepository pageMenuCmsRepository;

	@Autowired
	private WebsiteRepository websiteRepository;

	@Override
	public PageMenuCmsDto getPageMenu(UUID id) {
		if (id != null) {
			PageMenuCms pageMenuCms = pageMenuCmsRepository.findById(id).orElse(null);
			if (pageMenuCms != null) {
				return new PageMenuCmsDto(pageMenuCms, true, true);
			}
		}
		return null;
	}

	@Override
	public PageMenuCmsDto savePageMenu(PageMenuCmsDto dto) {
		if (dto == null) {
			return null;
		}
		PageMenuCms pageMenuCms = null;
		if (dto.getId() != null) {
			pageMenuCms = pageMenuCmsRepository.findById(dto.getId()).orElse(null);
		}
		if (pageMenuCms == null) {
			if (this.checkDuplicateCode(dto.getCode())) {
				return null;
			}
			pageMenuCms = new PageMenuCms();
		}
		pageMenuCms.setCode(dto.getCode());
		pageMenuCms.setName(dto.getName());
		pageMenuCms.setLinkUrl(dto.getLinkUrl());
		pageMenuCms.setType(dto.getType());
		pageMenuCms.setPositionIndex(dto.getPositionIndex());
		PageMenuCms parent = null;
		if (dto.getParent() != null && dto.getParent().getId() != null) {
			parent = pageMenuCmsRepository.findById(dto.getParent().getId()).orElse(null);
		}
		pageMenuCms.setParent(parent);
		Website website = null;
		if (dto.getWebsite() != null && dto.getWebsite().getId() != null) {
			website = websiteRepository.findById(dto.getWebsite().getId()).orElse(null);
		}
		pageMenuCms.setWebsite(website);
		pageMenuCms = pageMenuCmsRepository.save(pageMenuCms);
		return new PageMenuCmsDto(pageMenuCms, true, true);
	}

	@Override
	public Boolean deletePageMenu(UUID id) {
		if (id != null) {
			PageMenuCms pageMenuCms = pageMenuCmsRepository.findById(id).orElse(null);
			if (pageMenuCms != null) {
				try {
					if (pageMenuCms.getSubPageMenu().size() > 0) {
						for (PageMenuCms children : pageMenuCms.getSubPageMenu()) {
							pageMenuCmsRepository.deleteById(children.getId());
						}
					}
					pageMenuCmsRepository.deleteById(id);
					return true;
				} catch (Exception e) {
					LOGGER.info("Failed to delete page menu");
					return false;
				}
			}
		}
		return false;
	}

	@Override
	public Page<PageMenuCmsDto> paging(CmsSearchDto dto) {
		if (dto == null) {
			return null;
		}
		int pageIndex = dto.getPageIndex() != null ? dto.getPageIndex() : 0;
		pageIndex = pageIndex > 0 ? pageIndex - 1 : 0;
		int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10;
		String hql = "select new com.globits.cms.dto.PageMenuCmsDto(e) from PageMenuCms e ";
		String hqlCount = "select count(e) from PageMenuCms e ";
		String whereClause = " where (1=1) ";
		String orderBy = " order by e.code asc";
		if (dto.getParentId() != null) {
			whereClause += " and (e.parent.id = :parentId) ";
		} else {
			whereClause += " and (e.parent is null) ";
		}
		if (dto.getTextSearch() != null) {
			whereClause += " and (e.code like :textSearch or e.name like :textSearch) ";
		}
		if (dto.getWebsiteId() != null) {
			whereClause += " and (e.website.id like :websiteId) ";
		}
		hql += whereClause + orderBy;
		hqlCount += whereClause;
		Query query = manager.createQuery(hql, PageMenuCmsDto.class);
		Query queryCount = manager.createQuery(hqlCount);
		if (dto.getParentId() != null) {
			query.setParameter("parentId", dto.getParentId());
			queryCount.setParameter("parentId", dto.getParentId());
		}
		if (dto.getTextSearch() != null) {
			query.setParameter("textSearch", '%' + dto.getTextSearch() + '%');
			queryCount.setParameter("textSearch", '%' + dto.getTextSearch() + '%');
		}
		if (dto.getWebsiteId() != null) {
			query.setParameter("websiteId", dto.getWebsiteId());
			queryCount.setParameter("websiteId", dto.getWebsiteId());
		}
		List<PageMenuCmsDto> result = query.getResultList();
		long count = (long) queryCount.getSingleResult();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return new PageImpl<>(result, pageable, count);
	}

	@Override
	public Boolean checkDuplicateCode(String code) {
		if (code != null) {
			Long countDuplicate = pageMenuCmsRepository.countDuplicate(code);
			if (countDuplicate > 0) {
				return true;
			}
		}
		return false;
	}
}
