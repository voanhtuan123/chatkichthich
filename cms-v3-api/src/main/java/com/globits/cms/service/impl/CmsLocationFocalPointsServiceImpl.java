package com.globits.cms.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.cms.domain.CmsLocationFocalPoints;
import com.globits.cms.dto.CmsLocationFocalPointsDto;
import com.globits.cms.dto.CmsLocationFocalPointsSearchDto;
import com.globits.cms.repository.CmsLocationFocalPointsRepository;
import com.globits.cms.service.CmsLocationFocalPointsService;
import com.globits.core.service.impl.GenericServiceImpl;


@Service
public class CmsLocationFocalPointsServiceImpl extends GenericServiceImpl<CmsLocationFocalPoints, UUID> implements CmsLocationFocalPointsService {

	@Autowired
	CmsLocationFocalPointsRepository cmsLocationFocalPointsRepository;
	


	@Override
	public Page<CmsLocationFocalPointsDto> searchByPage(CmsLocationFocalPointsSearchDto dto) {
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

		
		String sqlCount = "select count(entity.id) from CmsLocationFocalPoints as entity where (1=1)";
		String sql = "select new com.globits.cms.dto.CmsLocationFocalPointsDto(entity) from CmsLocationFocalPoints as entity where (1=1)";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
		}



		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CmsLocationFocalPointsDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		


		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<CmsLocationFocalPointsDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<CmsLocationFocalPointsDto> result = new PageImpl<CmsLocationFocalPointsDto>(entities, pageable, count);
		return result;
	}
	
	@Override
	public CmsLocationFocalPointsDto getOne(UUID id) {
		CmsLocationFocalPoints entity = cmsLocationFocalPointsRepository.getOne(id);
		
		if (entity != null) {
			return new CmsLocationFocalPointsDto(entity);
		}
		
		return null;
	}
	
	@Override
	public CmsLocationFocalPointsDto saveOneOrUpdate(CmsLocationFocalPointsDto dto, UUID id) {
		if (dto != null) {
			CmsLocationFocalPoints entity = null;
			if (id != null) {
				if (dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = cmsLocationFocalPointsRepository.getOne(id);
			}
			if (entity == null) {
				entity = new CmsLocationFocalPoints();
			}
			
			/* Set all the values */
			entity.setName(dto.getName());
			entity.setCode(dto.getCode());

			
			entity = cmsLocationFocalPointsRepository.save(entity);
			
			if (entity != null) {
		
				return new CmsLocationFocalPointsDto(entity);
			}
		}
		
		return null;
	}
	
	@Override
	public void deleteById(UUID id) {
		cmsLocationFocalPointsRepository.deleteById(id);
		
	}
	

}

