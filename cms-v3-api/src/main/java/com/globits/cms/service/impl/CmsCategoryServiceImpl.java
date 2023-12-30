package com.globits.cms.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.cms.Const;
import com.globits.cms.domain.CmsCategory;
import com.globits.cms.domain.CmsMapObject;
import com.globits.cms.domain.Website;
import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsSearchDto;
import com.globits.cms.repository.CmsArticleRepsitory;
import com.globits.cms.repository.CmsCategoryRepository;
import com.globits.cms.repository.CmsMapObjectRepository;
import com.globits.cms.repository.WebsiteRepository;
import com.globits.cms.service.CmsCategoryService;
import com.globits.cms.util.VNCharacterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CmsCategoryServiceImpl extends GenericServiceImpl<CmsCategory, UUID> implements CmsCategoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsCategoryServiceImpl.class);
	@Autowired
	private CmsCategoryRepository cmsCategoryRepository;
	@Autowired
	private CmsArticleRepsitory cmsArticleRepsitory;
	@Autowired
	private CmsMapObjectRepository cmsMapObjectRepository;
	@Autowired
	WebsiteRepository websiteRepository;

	@Override
	public CmsCategoryDto getCmsCategory(UUID id) {
		if(id != null) {
			CmsCategory category = cmsCategoryRepository.findById(id).orElse(null);
			if(category != null) {
				return new CmsCategoryDto(category);
			}
		}
		return null;
	}

	@Override
	public CmsCategoryDto saveCmsCategory(CmsCategoryDto dto) {
		if(dto == null) {
			return null;
		}
		CmsCategory category = null;
		boolean isNew = false;
		if(dto.getId() != null) {
			category = cmsCategoryRepository.findById(dto.getId()).orElse(null);
		}
		if(category == null) {
			category = new CmsCategory();
			isNew = true;
		}
		category.setTitle(dto.getTitle());
		category.setCode(dto.getCode());
		category.setDescription(dto.getDescription());
		if(dto.getSlug() != null) {
			CmsMapObject mapObjectBySlug = cmsMapObjectRepository.getCmsMapByKey(dto.getSlug().toLowerCase());
			if(isNew && mapObjectBySlug != null) {
				String slugDuplicate = VNCharacterUtils.replaceAndRemoveSpace(mapObjectBySlug.getSlug() + " " + System.currentTimeMillis());
				category.setSlug(slugDuplicate);
			} else {
				category.setSlug(dto.getSlug().toLowerCase());
			}
		}
		category.setIsActive(dto.getIsActive());
		category.setIsShowMenu(dto.getIsShowMenu());
		category.setPositionIndex(dto.getPositionIndex());
		category.setShowHomePage(dto.getShowHomePage());
		category.setCategoryType(dto.getCategoryType());
		category.setDisplayType(dto.getDisplayType());
		category.setLinkUrl(dto.getLinkUrl());
		category.setPageIndex(dto.getPageIndex());
		category.setPageSize(dto.getPageSize());
		CmsCategory parent = null;
		if(dto.getParentId() != null) {
			parent = cmsCategoryRepository.findById(dto.getParent().getId()).orElse(null);
		}
		category.setParent(parent);
		Website website = null;
		if(dto.getWebsite() != null && dto.getWebsite().getId() != null) {
			website = websiteRepository.findById(dto.getWebsite().getId()).orElse(null);
		}
		category.setWebsite(website);
		if(category.getTitle() != null) {
			CmsMapObject mapObject = category.getMapObject() != null ? category.getMapObject() : new CmsMapObject();
			String slug = VNCharacterUtils.replaceAndRemoveSpace(category.getTitle().toLowerCase());
			mapObject.setObjectType(Const.SlugTypeEnum.CATEGORY.getKey());
			CmsMapObject cmsMapObjectBySlug = cmsMapObjectRepository.getCmsMapByKey(dto.getSlug().toLowerCase());
			if(isNew && cmsMapObjectBySlug != null && cmsMapObjectBySlug.getSlug() != null) {
				String slugDuplicate = VNCharacterUtils.replaceAndRemoveSpace(cmsMapObjectBySlug.getSlug() + " " + System.currentTimeMillis());
				mapObject.setSlug(slugDuplicate);
			} else {
				mapObject.setSlug(slug);
			}
			mapObject.setCategory(category);
			category.setMapObject(mapObject);
		}
		category = cmsCategoryRepository.save(category);
		return new CmsCategoryDto(category);
	}

	@Override
	public Boolean deleteCmsCategory(UUID id) {
		if(id != null) {
			CmsCategory category = cmsCategoryRepository.findById(id).orElse(null);
			if(category != null) {
				if(category.getSubCategories() != null && category.getSubCategories().size() > 0) {
					for(CmsCategory subCategory : category.getSubCategories()) {
						cmsCategoryRepository.deleteById(subCategory.getId());
					}
				}
				cmsMapObjectRepository.deleteById(category.getMapObject().getId());
				cmsCategoryRepository.deleteById(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public Page<CmsCategoryDto> paging(CmsSearchDto dto) {
		if(dto == null) {
			return null;
		}
		int pageIndex = dto.getPageIndex() != null ? dto.getPageIndex() : 0;
		pageIndex = pageIndex > 0 ? pageIndex - 1 : 0;
		int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10;
		String hql = "select new com.globits.cms.dto.CmsCategoryDto(e) from CmsCategory e ";
		String hqlCount = "select count(e) from CmsCategory e ";
		String whereClause = " where (1=1) ";
		String orderBy = " order by e.title";
		if(dto.getParentId() != null) {
			whereClause += " and (e.parent.id = :parentId) ";
		} else {
			whereClause += " and (e.parent is null) ";
		}
		if(dto.getWebsiteId() != null) {
			whereClause += " and (e.website.id like :websiteId) ";
		}
		if(dto.getTextSearch() != null) {
			whereClause += " and (e.title like :textSearch or e.description like :textSearch or e.slug like :textSearch) ";
		}
		hql += whereClause + orderBy;
		hqlCount += whereClause;
        Query query = manager.createQuery(hql, CmsCategoryDto.class);
        Query queryCount = manager.createQuery(hqlCount);
		if(dto.getParentId() != null) {
			query.setParameter("parentId", dto.getParentId());
			queryCount.setParameter("parentId", dto.getParentId());
		}
		if(dto.getWebsiteId() != null) {
			query.setParameter("websiteId", dto.getWebsiteId());
			queryCount.setParameter("websiteId", dto.getWebsiteId());
		}
		if(dto.getTextSearch() != null) {
			query.setParameter("textSearch", '%' + dto.getTextSearch() + '%');
			queryCount.setParameter("textSearch", '%' + dto.getTextSearch() + '%');
		}
		List<CmsCategoryDto> result = query.getResultList();
		long count = (long) queryCount.getSingleResult();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return new PageImpl<>(result, pageable, count);
	}

	@Override
	public Page<CmsCategoryDto> pagingParentAndChild(CmsSearchDto dto) {
		if(dto == null) {
			return null;
		}
		int pageIndex = dto.getPageIndex() != null ? dto.getPageIndex() : 0;
		pageIndex = pageIndex > 0 ? pageIndex - 1 : 0;
		int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10;
		String hql = "select new com.globits.cms.dto.CmsCategoryDto(e) from CmsCategory e ";
		String hqlCount = "select count(e) from CmsCategory e ";
		String whereClause = " where (1=1) ";
		String orderBy = " order by e.title";
		hql += whereClause + orderBy;
		hqlCount += whereClause;
		Query query = manager.createQuery(hql, CmsCategoryDto.class);
		Query queryCount = manager.createQuery(hqlCount);
		List<CmsCategoryDto> result = query.getResultList();
		long count = (long) queryCount.getSingleResult();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return new PageImpl<>(result, pageable, count);
	}

	@Override
	public List<CmsCategoryDto> getAllByParentNull(Boolean getIsHoverArticle) {
		CmsSearchDto dto = new CmsSearchDto();
		dto.setPageIndex(1);
		dto.setPageSize(Integer.MAX_VALUE);
		List<CmsCategoryDto> categories = this.paging(dto).getContent();
		List<CmsArticleDto> listArticle = cmsArticleRepsitory.getListCmsArticleDto(getIsHoverArticle);
		for(CmsCategoryDto category : categories) {
			this.addChildrenOnHover(category, listArticle);
		}
		return categories;
	}

	private void addChildrenOnHover( CmsCategoryDto parent,List<CmsArticleDto> listArticle) {
		if(parent.getParentId() == null) {
			if(parent.getShowHomePage()!=null && parent.getShowHomePage()) {
				List<CmsArticleDto> listData = new ArrayList<CmsArticleDto>();
				for (CmsArticleDto cmsArticleDto : listArticle) {
					if(parent.getId().equals(cmsArticleDto.getPrimaryCategory().getId()) ) {
						listData.add(cmsArticleDto);
					}
				}
				parent.setListArticles(listData);
			}
		}
		if (null != parent.getSubCategories() && parent.getSubCategories().size() > 0) {
			for (CmsCategoryDto child : parent.getSubCategories()) {
				if(child.getShowHomePage()!=null && child.getShowHomePage()) {
					List<CmsArticleDto> listData = new ArrayList<CmsArticleDto>();
					for (CmsArticleDto cmsArticleDto : listArticle) {
						if(child.getId().equals(cmsArticleDto.getPrimaryCategory().getId()) ) {
							listData.add(cmsArticleDto);
						}
					}
					child.setListArticles(listData);
				}
				addChildrenOnHover(child,listArticle);
			}
		}
	}
}
