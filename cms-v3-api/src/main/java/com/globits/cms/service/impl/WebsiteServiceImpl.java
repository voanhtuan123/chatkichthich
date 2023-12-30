package com.globits.cms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
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

import com.globits.cms.domain.Website;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.dto.WebsiteDto;
import com.globits.cms.repository.WebsiteRepository;
import com.globits.cms.service.WebsiteService;
import com.globits.core.domain.Organization;
import com.globits.core.repository.OrganizationRepository;
import com.globits.core.service.impl.GenericServiceImpl;

@Service
public class WebsiteServiceImpl extends GenericServiceImpl<Website, UUID> implements WebsiteService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WebsiteServiceImpl.class);
	@Autowired
	private WebsiteRepository websiteRepository;
	
	@Resource
	private OrganizationRepository organizationRepository;

	@Override
	public WebsiteDto getWebsiteById(UUID id) {
		if(id != null) {
			Website website = websiteRepository.findById(id).orElse(null);
			if(website != null) {
				return new WebsiteDto(website);
			}
		}
		return null;
	}

	@Override
	public WebsiteDto saveWebsite(WebsiteDto dto) {
		if(dto == null) {
			return null;
		}
		Website website = null;
		if(dto.getId() != null) {
			website = websiteRepository.findById(dto.getId()).orElse(null);
		} 
		
		if (website == null){
			if (dto.getDomain() != null) {
				if (this.duplicateDomain(dto.getDomain())) {
					return null;
				}
			}
			website = new Website();
		}
		
		
		website.setName(dto.getName());
		website.setCode(dto.getCode());
		website.setDomain(dto.getDomain());
		website.setTemplate(dto.getTemplate());
		website.setLogoPath(dto.getLogoPath());
		Website parent = null;
		if(dto.getParent() != null && dto.getParent().getId() != null) {
			parent = websiteRepository.findById(dto.getParent().getId()).orElse(null);
		}
		website.setParent(parent);
		website = websiteRepository.save(website);
		return new WebsiteDto(website);
	}

	@Override
	public Boolean deleteWebsite(UUID id) {
		if(id != null) {
			Website website = websiteRepository.findById(id).orElse(null);
			if(website != null) {
				List<UUID> childrenIds = websiteRepository.getListByParentId(id);
				if(childrenIds != null && childrenIds.size() > 0) {
					for(UUID childrenId : childrenIds) {
						websiteRepository.deleteById(childrenId);
					}
				}
				websiteRepository.deleteById(id);
			}
		}
		return null;
	}

	@Override
	public Boolean duplicateDomain(String domain) {
		if(domain != null) {
			domain = domain.replace("www.", "");
			Long number = websiteRepository.countDuplicate(domain);
			if(number > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Page<WebsiteDto> paging(CmsSearchDto dto) {
		if(dto == null) {
			return null;
		}
		int pageIndex = dto.getPageIndex() != null ? dto.getPageIndex() : 0;
		pageIndex = pageIndex > 0 ? pageIndex - 1 : 0;
		int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10;
		String hql = "select new com.globits.cms.dto.WebsiteDto(e) from Website e ";
		String hqlCount = "select count(e) from Website e ";
		String whereClause = " where (1=1) ";
		String orderBy = " order by e.modifyDate";
		if(dto.getTextSearch() != null) {
			whereClause += " and (e.name like :textSearch or e.code like :textSearch) ";
		}
		if(dto.getParentId() != null) {
			whereClause += " and (e.parent.id = :parentId) ";
		} else {
			whereClause += " and (e.parent is null) ";
		}
		hql += whereClause + orderBy;
		hqlCount += whereClause;
		Query query = manager.createQuery(hql, WebsiteDto.class);
		Query queryCount = manager.createQuery(hqlCount);
		if(dto.getTextSearch() != null) {
			query.setParameter("textSearch", '%' + dto.getTextSearch() + '%');
			queryCount.setParameter("textSearch", '%' + dto.getTextSearch() + '%');
		}
		if(dto.getParentId() != null) {
			query.setParameter("parentId", dto.getParentId());
			queryCount.setParameter("parentId", dto.getParentId());
		}
		List<WebsiteDto> result = query.getResultList();
		long count = (long) queryCount.getSingleResult();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return new PageImpl<>(result, pageable, count);
	}

	@Override
	public WebsiteDto getWebsite(String domainName) {
		domainName = domainName.replace("www.", "");
		return websiteRepository.getWebsite(domainName);
	}

	@Override
	public Boolean checkDuplicateDomain(UUID id, String domain) {
		if (domain != null && StringUtils.hasText(domain)) {
			WebsiteDto dto = this.getWebsite(domain);
			if (dto != null) {
				if (id != null && dto.getId().equals(id)) {
					return false;
				}
				return true;
			}
			return false;
		}
		return null;
	}
	
}
