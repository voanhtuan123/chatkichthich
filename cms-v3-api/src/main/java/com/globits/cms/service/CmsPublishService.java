package com.globits.cms.service;

import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.CmsSearchDto;
import com.globits.cms.dto.home.ArticleViewDto;
import com.globits.cms.dto.home.CategoryViewDto;
import com.globits.cms.dto.home.HomePageDto;
import com.globits.cms.dto.home.MenuHeaderDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CmsPublishService {
    List<MenuHeaderDto> getMenuHeader(); // menu header

    List<CategoryViewDto> getCategoryBySlug(String slug);

    Page<ArticleViewDto> getPageArticleBySlug(CmsSearchDto dto);

    CmsArticleDto getArticleBySlug(String slug);

    HomePageDto getHomePage();
}
