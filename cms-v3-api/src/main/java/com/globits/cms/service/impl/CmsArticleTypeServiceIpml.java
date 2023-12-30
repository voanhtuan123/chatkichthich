package com.globits.cms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.cms.domain.CmsArticleType;
import com.globits.cms.domain.Website;
import com.globits.cms.dto.CmsArticleGroupDto;
import com.globits.cms.dto.CmsArticleTypeDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.repository.CmsArticleTypeRepository;
import com.globits.cms.repository.WebsiteRepository;
import com.globits.cms.service.CmsArticleTypeService;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;

@Service
public class CmsArticleTypeServiceIpml extends GenericServiceImpl<CmsArticleType, UUID>
		implements CmsArticleTypeService {
	@Autowired
	CmsArticleTypeRepository articleTypeRepository;
	@Autowired
	WebsiteRepository websiteRepository;

	@Override
	public Page<CmsArticleTypeDto> getListArticleType(int pageIndex, int pageSize) {
		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable =PageRequest.of(pageIndex, pageSize);
		return articleTypeRepository.getListArticleType(pageable);
	}

	@Override
	public CmsArticleTypeDto addArticleType(CmsArticleTypeDto dto) {
		CmsArticleType at = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (dto.getId() != null) {
			at = articleTypeRepository.getOne(dto.getId());
		}

		if (at == null) {
			at = new CmsArticleType();
			at.setCreateDate(currentDate);
			at.setCreatedBy(currentUserName);
		}
		if (dto.getName() != null) {
			at.setName(dto.getName());
		}
		if (dto.getCode() != null) {
			at.setCode(dto.getCode());
		}
		if (dto.getDescription() != null) {
			at.setDescription(dto.getDescription());
		}
//		if()
		Website website = null;
		if (dto.getWebsite() != null && dto.getWebsite().getId() != null) {
			website = websiteRepository.getOne(dto.getWebsite().getId());
		}
		at.setWebsite(website);
		at.setPriority(dto.getPriority());

		at = articleTypeRepository.save(at);

		return new CmsArticleTypeDto(at);
	}

	@Override
	public boolean removeList(List<CmsArticleTypeDto> dtos) {
		if (dtos != null && dtos.size() > 0) {
			for (CmsArticleTypeDto item : dtos) {
				remove(item.getId());
			}
		}
		return false;
	}

	@Override
	public CmsArticleTypeDto remove(UUID id) {
		CmsArticleType at = null;
		if (id != null) {
			Boolean result = this.deleteCheckById(id);
			if(result) {
				at = articleTypeRepository.getOne(id);
				if (at != null) {
					articleTypeRepository.delete(at);
					return new CmsArticleTypeDto(at);
				}
			}			
		}
		return null;
	}

	@Override
	public CmsArticleTypeDto checkDuplicateCode(String code) {

		CmsArticleTypeDto viewCheckDuplicateCodeDto = new CmsArticleTypeDto();
		CmsArticleType au = null;
		List<CmsArticleType> list = articleTypeRepository.findListByCode(code);
		if (list != null && list.size() > 0) {
			au = list.get(0);
		}
		if (list == null) {
			viewCheckDuplicateCodeDto.setDuplicate(false);
		} else if (list != null && list.size() > 0) {
			viewCheckDuplicateCodeDto.setDuplicate(true);
			viewCheckDuplicateCodeDto.setDupCode(au.getCode());
			viewCheckDuplicateCodeDto.setDupName(au.getName());
		}
		return viewCheckDuplicateCodeDto;
	}

	@Override
	public Boolean deleteCheckById(UUID id) {
		if (id != null) {
			Long article;
			article = articleTypeRepository.findByArticle(id);
			if (article == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Page<CmsArticleTypeDto> searchByPage(SearchDto dto) {
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

		String sqlCount = "select count(entity.id) from CmsArticleType as entity where (1=1)";
		String sql = "select new com.globits.cms.dto.CmsArticleTypeDto(entity) from CmsArticleType as entity where (1=1)";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}

		if (dto.getWebsiteId() != null) {
			whereClause += " AND ( entity.website.id =:websiteId )";
		}

		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CmsArticleTypeDto.class);
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
		List<CmsArticleTypeDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<CmsArticleTypeDto> result = new PageImpl<CmsArticleTypeDto>(entities, pageable, count);
		return result;
	}

}
