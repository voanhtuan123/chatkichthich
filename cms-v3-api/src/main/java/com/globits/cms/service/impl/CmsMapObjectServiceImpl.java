package com.globits.cms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.cms.domain.CmsArticle;
import com.globits.cms.domain.CmsMapObject;
import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsMapObjectDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.repository.CmsArticleRepsitory;
import com.globits.cms.repository.CmsCategoryRepository;
import com.globits.cms.repository.CmsMapObjectRepository;
import com.globits.cms.service.CmsArticleService;
import com.globits.cms.service.CmsMapObjectService;
import com.globits.core.service.impl.GenericServiceImpl;

@Service
public class CmsMapObjectServiceImpl extends GenericServiceImpl<CmsMapObject, UUID> implements CmsMapObjectService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CmsMapObjectServiceImpl.class);
	@Autowired
	private CmsMapObjectRepository cmsMapObjectRepository;
	@Autowired
	private CmsArticleService cmsArticleService;
	@Autowired
	private CmsArticleRepsitory cmsArticleRepsitory;
	@Autowired
	private CmsCategoryRepository cmsCategoryRepository;
	
	
	@Autowired
	private CmsArticleServiceImpl cmsArticleServiceImpl;
	
	

	@Override
	public CmsMapObjectDto getHomePage(UUID websiteId) {
		List<CmsMapObject> entities = cmsMapObjectRepository.getListCmsCategoryMap(websiteId);
		CmsMapObjectDto result = new CmsMapObjectDto();
		result.setCategory(new CmsCategoryDto());
		result.getCategory().setTitle("Home");
		result.getCategory().setSubCategories(new ArrayList<CmsCategoryDto>());
		if (entities != null) {
			Page<CmsArticleDto> articles = cmsArticleService.getPageCmsArticleDto(1, 10);// Tạm thời chỉ lấy 10 bản ghi
																							// của trang đầu tiên
			for (CmsMapObject entity : entities) {
				if (entity.getCategory() != null) {
					CmsCategoryDto category = new CmsCategoryDto(entity.getCategory());
					// Tạm thời gán mọi category có các bài báo giống nhau để show phía client đã
					Page<CmsArticleDto> catArticles = cmsArticleService.getPageCmsArticleByCategory(category.getId(), 1,
							10);// Tạm thời lấy về 10  
					if(catArticles.getContent().size()==0) {
						  catArticles = cmsArticleServiceImpl.getPageCmsArticleByCategoryAndSubCategory(category.getId(), 1,
								10);// Tạm thời lấy về 10
					}
					category.setArticles(catArticles);
					result.getCategory().getSubCategories().add(category);
				}
			}

			result.setArticles(articles);// Set home page article
		}
		return result;
	}

	@Override
	public CmsMapObjectDto getHomePage() {
		List<CmsMapObject> entities = cmsMapObjectRepository.getListCmsCategoryMap();
		CmsMapObjectDto result = new CmsMapObjectDto();
		result.setCategory(new CmsCategoryDto());
		result.getCategory().setTitle("Home");
		result.getCategory().setSubCategories(new ArrayList<CmsCategoryDto>());
		if (entities != null) {
			Page<CmsArticleDto> articles = cmsArticleService.getPageCmsArticleDto(1, 10);// Tạm thời chỉ lấy 10 bản ghi
																							// của trang đầu tiên
			for (CmsMapObject entity : entities) {
				if (entity.getCategory() != null) {
					CmsCategoryDto category = new CmsCategoryDto(entity.getCategory());
					// Tạm thời gán mọi category có các bài báo giống nhau để show phía client đã
					Page<CmsArticleDto> catArticles = cmsArticleService.getPageCmsArticleByCategory(category.getId(), 1,
							10);// Tạm thời lấy về 10
					category.setArticles(catArticles);
					result.getCategory().getSubCategories().add(category);
				}
			}

			result.setArticles(articles);// Set home page article
		}
		return result;
	}

	@Override
	public CmsMapObjectDto getCmsMapByKey(String key) {
		CmsMapObjectDto result = new CmsMapObjectDto();
		CmsMapObject cmsMap = cmsMapObjectRepository.getCmsMapByKey(key);
		if (cmsMap != null) {
			if (cmsMap.getArticle() != null) {
				CmsArticleDto article = new CmsArticleDto(cmsMap.getArticle());
				CmsArticle art =cmsArticleRepsitory.getOne(article.getId());
				if(art.getView() != null) {
					art.setView(art.getView()+1);
				}else {
					art.setView(1);
				}
				cmsArticleRepsitory.save(art);
				result.setArticle(article);
			} else if (cmsMap.getCategory() != null) {// Trả về danh sách các bài báo thuộc chuyên mục đó, chưa tính đến
														// các vấn đề tiêu điềm, ...
				SearchDto searchDto = new SearchDto();
				searchDto.setPageIndex(1);// Cần thay đổi được giá trị mặc định
				searchDto.setPageSize(10);// Cần thay đổi giá trị mặc định
				searchDto.setCategoryId(cmsMap.getCategory().getId());
				Page<CmsArticleDto> page = cmsArticleService.searchArticleServiceBySearchDtoByPublishAPI(searchDto);
				result.setCategory(new CmsCategoryDto(cmsMap.getCategory()));
				result.setArticles(page);
			}
		}
		return result;
	}

	@Override
	public CmsMapObjectDto getPageCmsMapByKey(String key, int pageIndex, int pageSize) {
		CmsMapObjectDto result = new CmsMapObjectDto();
		CmsMapObject cmsMap = cmsMapObjectRepository.getCmsMapByKey(key); 
		if(cmsMap!=null) {
			if(cmsMap.getArticle()!=null) {
				CmsArticleDto article = new CmsArticleDto(cmsMap.getArticle());
				CmsArticle art =cmsArticleRepsitory.getOne(article.getId());

				List<CmsArticleDto> articlesFromPrimaryCategory = cmsCategoryRepository.getListArticle(article.getPrimaryCategory().getId());
				if(art.getView() != null) {
					art.setView(art.getView()+1);
				}else {
					art.setView(1);
				}
				cmsArticleRepsitory.save(art);
				result.setArticle(article);
				result.getArticle().getPrimaryCategory().setListArticles(articlesFromPrimaryCategory);
			}else if(cmsMap.getCategory()!=null){//Trả về danh sách các bài báo thuộc chuyên mục đó, chưa tính đến các vấn đề tiêu điềm, ...
				SearchDto searchDto = new SearchDto();
				searchDto.setPageIndex(pageIndex);//
				searchDto.setPageSize(pageSize);
				searchDto.setCategoryId(cmsMap.getCategory().getId());
				Page<CmsArticleDto> page = cmsArticleService.searchArticleServiceBySearchDtoByPublishAPI(searchDto);
				List<CmsCategoryDto> listCate =  cmsArticleService.getListCategoryAndArticleOfCategory(cmsMap.getCategory().getId(),pageIndex,pageSize);
				result.setListSubCategory(listCate);
				result.setCategory(new CmsCategoryDto(cmsMap.getCategory()));
				result.setArticles(page);
			}
		}
		return result;
	}

}
