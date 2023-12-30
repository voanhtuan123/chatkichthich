package com.globits.cms.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.cms.domain.CmsPage;
import com.globits.cms.domain.CmsPane;
import com.globits.cms.domain.Website;
import com.globits.cms.dto.CmsPageDto;
import com.globits.cms.dto.CmsPaneDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.repository.WebsiteRepository;
import com.globits.cms.repository.CmsPageRepository;
import com.globits.cms.repository.CmsPaneReponsitory;
import com.globits.cms.service.CmsPageService;
import com.globits.core.service.impl.GenericServiceImpl;

@Service
public class CmsPageServiceImpl extends GenericServiceImpl<CmsPage, UUID> implements CmsPageService {

	@Autowired
	private CmsPageRepository cmsPageRepository;
	@Autowired
	private CmsPaneReponsitory cmsPaneRepository;
	@Autowired
	private WebsiteRepository websiteRepository;

	@Override
	public Page<CmsPageDto> searchByPage(SearchDto dto) {
		// TODO Auto-generated method stub
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

		String orderBy = " ORDER BY entity.name DESC";

		String sqlCount = "select count(entity.id) from CmsPage as entity where (1=1)";
		String sql = "select new com.globits.cms.dto.CmsPageDto(entity) from CmsPage as entity where (1=1)";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}

		if (dto.getWebsiteId() != null) {
			whereClause += " AND ( entity.website.id =:websiteId )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CmsPageDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}

		if (dto.getWebsiteId() != null) {
			q.setParameter("websiteId", dto.getWebsiteId());
			qCount.setParameter("websiteId", dto.getWebsiteId());
		}

		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<CmsPageDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<CmsPageDto> result = new PageImpl<CmsPageDto>(entities, pageable, count);
		return result;
	}

	@Override
	public CmsPageDto getOne(UUID id) {
		CmsPage entity = cmsPageRepository.getOne(id);

		if (entity != null) {
			return new CmsPageDto(entity);
		}

		return null;
	}

	@Override
	public CmsPageDto saveOneOrUpdate(CmsPageDto dto, UUID id) {
		if (dto != null) {
			CmsPage entity = null;
			if (id != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = cmsPageRepository.getOne(id);
			}
			if (entity == null) {
				entity = new CmsPage();
			}

			/* Set all the values */
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());
			entity.setTemplatePath(dto.getTemplatePath());
			if (dto.getWebsite() != null && dto.getWebsite().getId() != null) {
				Website w = websiteRepository.getOne(dto.getWebsite().getId());
				entity.setWebsite(w);
			}
			Set<CmsPane> cps = new HashSet<CmsPane>();
			if (dto.getPanes() != null) {
				for (CmsPaneDto cpDto : dto.getPanes()) {
					CmsPane cp = null;
					if (cpDto.getId() != null) {
						cp = cmsPaneRepository.getOne(cpDto.getId());
					}
					if (cp == null) {
						cp = new CmsPane();
					}
					cp.setCode(cpDto.getCode());
					cp.setName(cpDto.getName());

					cp.setPage(entity);
					cps.add(cp);
				}
			}

			if (entity.getPanes() == null) {
				entity.setPanes(cps);
			} else {
				entity.getPanes().clear();
				entity.getPanes().addAll(cps);
			}

			entity = cmsPageRepository.save(entity);
			if (entity != null) {
				return new CmsPageDto(entity);
			}
		}

		return null;
	}

	@Override
	public void deleteById(UUID id) {
		// TODO Auto-generated method stub
		cmsPageRepository.deleteById(id);

	}

	@Override
	public CmsPageDto getPageByTemplatePath(String templatePath) {
		// TODO Auto-generated method stub
		List<CmsPageDto> listPage = cmsPageRepository.getPageByTemplatePath(templatePath);
		if (listPage != null && listPage.size() > 0) {
			System.out.println(listPage.get(0).getName());
			return listPage.get(0);
		}
		return null;
	}

}
