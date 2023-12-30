package com.globits.cms.service.impl;

import java.util.List;
import java.util.UUID;

import javax.persistence.Query;

import com.globits.cms.domain.CmsArticle;
import com.globits.cms.domain.CmsCategory;
import com.globits.cms.domain.CmsPaneDataConfig;
import com.globits.cms.dto.CmsPaneDataConfigDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.repository.CmsArticleRepsitory;
import com.globits.cms.repository.CmsCategoryRepository;
import com.globits.cms.repository.CmsPaneDataConfigRepository;
import com.globits.cms.repository.CmsPaneReponsitory;
import com.globits.cms.service.CmsPaneDataConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;



import com.globits.core.service.impl.GenericServiceImpl;
@Service
public class CmsPaneDataConfigServiceImpl extends GenericServiceImpl<CmsPaneDataConfig, UUID> implements CmsPaneDataConfigService {

	@Autowired
	CmsPaneDataConfigRepository cmsPaneDataConfigRepository;
	@Autowired
	CmsCategoryRepository cmsCategoryRepository;
	@Autowired
	CmsArticleRepsitory cmsArticleRepsitory;
	@Autowired
	CmsPaneReponsitory cmspaneRepository;
	@Override
	public Page<CmsPaneDataConfigDto> searchByPage(SearchDto dto) {
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
		
		String sqlCount = "select count(entity.id) from CmsPaneDataConfig as entity where (1=1)";
		String sql = "select new com.globits.cms.dto.CmsPaneDataConfigDto(entity) from CmsPaneDataConfig as entity where (1=1)";

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			whereClause += " AND ( entity.pane_type LIKE :text)";
		}

		if(dto.getArticleId() != null) {
			whereClause += " AND ( entity.article.id =:articleId )";
		}
		if(dto.getCategoryId() != null) {
			whereClause += " AND ( entity.categoty.id =:categoryId )";
		}
//		if(dto.getPaneId() != null) {
//			whereClause += " AND ( entity.pane.id =:paneId )";
//		}
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, CmsPaneDataConfigDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())) {
			q.setParameter("text", '%' + dto.getKeyword() + '%');
			qCount.setParameter("text", '%' + dto.getKeyword() + '%');
		}
		if(dto.getArticleId() != null) {
			whereClause += " AND ( entity.article.id =:articleId )";
			q.setParameter("articleId", dto.getArticleId());
			qCount.setParameter("articleId",dto.getArticleId());
		}
		if(dto.getCategoryId() != null) {
			whereClause += " AND ( entity.category.id =:categoryId )";
			q.setParameter("categoryId", dto.getCategoryId());
			qCount.setParameter("categoryId",dto.getCategoryId());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<CmsPaneDataConfigDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		Page<CmsPaneDataConfigDto> result = new PageImpl<CmsPaneDataConfigDto>(entities, pageable, count);
		return result;
	}

	@Override
	public CmsPaneDataConfigDto getOne(UUID id) {
		CmsPaneDataConfig entity = cmsPaneDataConfigRepository.getOne(id);
		if(entity != null) {
			return new CmsPaneDataConfigDto(entity);
		}
		
		return null;
	}

	@Override
	public CmsPaneDataConfigDto saveOrUpdate(CmsPaneDataConfigDto dto, UUID id) {
		if(dto!=null) {
			CmsPaneDataConfig entity = null;
			if(id!= null) {
				if(dto.getId() != null && !dto.getId().equals(id)) {
					return null;
				}
				entity = cmsPaneDataConfigRepository.getOne(id);
			}
			if(entity == null) {
				entity = new CmsPaneDataConfig();
			}
			entity.setPaneType(dto.getPaneType());
			//CmsPane cmsPane = null;
//			if(dto.getPane()!= null && dto.getPane().getId() !=null) {
//				
//			}
			CmsCategory category = null;
			if(dto.getCategory()!= null && dto.getCategory().getId() !=null) {
				category = cmsCategoryRepository.getOne(dto.getCategory().getId());
			}
			entity.setCategory(category);
			CmsArticle article = null;
			if(dto.getArticle()!= null && dto.getArticle().getId()!= null) {
				article = cmsArticleRepsitory.getOne(dto.getArticle().getId());
			}
			entity.setArticle(article);
			
			entity = cmsPaneDataConfigRepository.save(entity);
			if (entity != null) {
				return new CmsPaneDataConfigDto(entity);
			}
		}
		return null;
	}

	@Override
	public void deleteById(UUID id) {
		cmsPaneDataConfigRepository.deleteById(id);
		
	}

}
