package com.globits.cms.service;

import com.globits.core.service.GenericService;
import com.globits.cms.domain.CmsArticle;
import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsSearchDto;
import com.globits.cms.dto.SearchDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface CmsArticleService extends GenericService<CmsArticle, UUID>{
	// NMDuc start
	CmsArticleDto getArticle(UUID id);

	CmsArticleDto saveArticle(CmsArticleDto dto);

	Boolean deleteArticle(UUID id);

	Page<CmsArticleDto> paging(CmsSearchDto dto);
	// NMDuc end

	Page<CmsArticleDto> getPageCmsArticleDto(int pageIndex, int pageSize);
	Page<CmsArticleDto> getPageCmsArticleByCategory(UUID categoryId, int pageIndex, int pageSize);

	CmsArticleDto saveCmsArticle(CmsArticleDto dto, UUID id);

	Boolean deleteCmsArticleById(UUID id);

	Page<CmsArticleDto> searchArticleServiceBySearchDto(SearchDto search);
	
	void seeNews(UUID id);

	Page<CmsArticleDto> getPageCmsArticleOrderByViewDESC(int pageIndex, int pageSize);


	Page<CmsArticleDto> searchArticleServiceBySearchDtoByPublishAPI(SearchDto searchDto);

	Page<CmsArticleDto> searchByDto(SearchDto search);
	
	Boolean checkSlug(UUID id,String slug);
	
	List<CmsArticleDto> saveImportCmsArticleDto(List<CmsArticleDto> list);
	
	List<CmsCategoryDto> getListCategoryAndArticleOfCategory(UUID listIdCategory, int pageIndex, int pageSize);
	
	Page<CmsArticleDto> searchByName(SearchDto search);
}
