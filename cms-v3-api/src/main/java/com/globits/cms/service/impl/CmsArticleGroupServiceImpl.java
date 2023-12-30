package com.globits.cms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.cms.domain.CmsArticleGroup;
import com.globits.cms.domain.Website;
import com.globits.cms.dto.CmsArticleGroupDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.repository.CmsArticleGroupRepository;
import com.globits.cms.repository.WebsiteRepository;
import com.globits.cms.service.CmsArticleGroupService;
import com.globits.core.service.impl.GenericServiceImpl;

@Service
public class CmsArticleGroupServiceImpl extends GenericServiceImpl<CmsArticleGroup, UUID> implements CmsArticleGroupService{

	@Autowired
	CmsArticleGroupRepository cmsArticleGroupRepository;
	@Autowired
	WebsiteRepository websiteRepository;
	@Override
	public Page<CmsArticleGroupDto> searchByPage(SearchDto dto) {
		if (dto == null) {
			return null;
		}

		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		String whereClause = "";
		
		String orderBy = " ORDER BY entity.createDate DESC";
		
		String sqlCount = "select count(entity.id) from CmsArticleGroup as entity where (1=1)";
		String sql = "select new com.globits.cms.dto.CmsArticleGroupDto(entity) from CmsArticleGroup as entity where (1=1)";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.slug LIKE :text )";
		}

		if(dto.getWebsiteId() != null) {
			whereClause += " AND ( entity.website.id =:websiteId )";
		}
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CmsArticleGroupDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if(dto.getWebsiteId() != null) {
			whereClause += " AND ( entity.website.id =:websiteId )";
			q.setParameter("websiteId", dto.getWebsiteId());
			qCount.setParameter("websiteId",dto.getWebsiteId());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<CmsArticleGroupDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<CmsArticleGroupDto> result = new PageImpl<CmsArticleGroupDto>(entities, pageable, count);
		return result;
	}

	@Override
	public CmsArticleGroupDto getOne(UUID id) {
		CmsArticleGroup entity = cmsArticleGroupRepository.getOne(id);
		
		if (entity != null) {
			return new CmsArticleGroupDto(entity);
		}
		
		return null;
	}

	@Override
	public CmsArticleGroupDto saveOrUpdate(CmsArticleGroupDto dto, UUID id) {
		if (dto != null) {
			CmsArticleGroup entity = null;
			if (id != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = cmsArticleGroupRepository.getOne(id);
			}
			if (entity == null) {
				entity = new CmsArticleGroup();
			}
			Website website = null;
			if(dto.getWebsite() != null && dto.getWebsite().getId() != null) {
				website = websiteRepository.getOne(dto.getWebsite().getId());
			}
			entity.setWebsite(website);
			/* Set all the values */
			entity.setDescription(dto.getDescription());
			entity.setName(dto.getName());
			entity.setPositionIndex(dto.getPositionIndex());
			entity.setSlug(dto.getSlug());
			entity.setCode(dto.getCode());
			if (dto.getAvatar() != null) {
				entity.setAvatar(dto.getAvatar());				
			}
			
			entity = cmsArticleGroupRepository.save(entity);
			if (entity != null) {
				return new CmsArticleGroupDto(entity);
			}
		}
		return null;
	}

	@Override
	public void deleteById(UUID id) {
		cmsArticleGroupRepository.deleteById(id);
	}

	
}
