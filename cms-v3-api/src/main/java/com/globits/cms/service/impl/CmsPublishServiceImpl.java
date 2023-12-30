package com.globits.cms.service.impl;

import com.globits.cms.Const;
import com.globits.cms.domain.CmsArticle;
import com.globits.cms.domain.CmsCategory;
import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.CmsSearchDto;
import com.globits.cms.dto.home.ArticleViewDto;
import com.globits.cms.dto.home.CategoryViewDto;
import com.globits.cms.dto.home.HomePageDto;
import com.globits.cms.dto.home.MenuHeaderDto;
import com.globits.cms.repository.CmsArticleRepsitory;
import com.globits.cms.repository.CmsCategoryRepository;
import com.globits.cms.repository.PageMenuCmsRepository;
import com.globits.cms.service.CmsArticleService;
import com.globits.cms.service.CmsPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CmsPublishServiceImpl implements CmsPublishService {
    @Autowired
    private CmsArticleService articleService;
    @Autowired
    private PageMenuCmsRepository pageMenuCmsRepository;

    @Autowired
    private CmsCategoryRepository categoryRepository;

    @Autowired
    private CmsArticleRepsitory articleRepository;

    @PersistenceContext
    public EntityManager manager;

    public CmsPublishServiceImpl() {
    }

    @Override
    public List<MenuHeaderDto> getMenuHeader() {
        return pageMenuCmsRepository.getListMenuHeader();
    }

    @Override
    public List<CategoryViewDto> getCategoryBySlug(String slug) {
        if (slug != null) {
            CmsCategory category = categoryRepository.getCategoryBySlug(slug);
            if (category != null) {
                if (category.getParent() == null) {
                    return categoryRepository.getAllCategoryBySlug(slug);
                } else if (category.getParent().getParent() == null) {
                    return categoryRepository.getAllCategoryBySlug(slug);
                }
            }
        }
        return null;
    }

    @Override
    public Page<ArticleViewDto> getPageArticleBySlug(CmsSearchDto dto) {
        if (dto != null && dto.getSlug() != null) {
            int pageIndex = dto.getPageIndex() != null ? dto.getPageIndex() : 0;
            pageIndex = pageIndex > 0 ? pageIndex - 1 : 0;
            int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10;
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            CmsCategory category = categoryRepository.getCategoryBySlug(dto.getSlug());
            List<UUID> articleIds = this.getArticleByCategory(category);
            if (articleIds != null && articleIds.size() > 0) {
                String hql = "select new com.globits.cms.dto.home.ArticleViewDto(e, true) from CmsArticle e ";
                String hqlCount = "select count(e) from CmsArticle e ";
                String whereClause = " where (e.id in (:articleIds)) ";
                String orderBy = " order by e.publishDate ";
                hql += whereClause + orderBy;
                hqlCount += whereClause;
                Query query = manager.createQuery(hql, ArticleViewDto.class);
                Query queryCount = manager.createQuery(hqlCount);
                query.setParameter("articleIds", articleIds);
                queryCount.setParameter("articleIds", articleIds);
                List<ArticleViewDto> result = query.getResultList();
                long count = (long) queryCount.getSingleResult();
                return new PageImpl<>(result, pageable, count);
            }
        }
        return null;
    }

    @Override
    public CmsArticleDto getArticleBySlug(String slug) {
        try {
            String hql = "select new com.globits.cms.dto.CmsArticleDto(e, true) from CmsArticle e ";
            String whereClause = " where (e.slug in (:slug)) ";
            hql += whereClause;
            Query query = manager.createQuery(hql, CmsArticleDto.class);
            query.setParameter("slug", slug);
            CmsArticleDto result = (CmsArticleDto) query.getSingleResult();
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public HomePageDto getHomePage() {
        HomePageDto result = new HomePageDto();
        // bai viet slider
        List<ArticleViewDto> listSlides = articleRepository.getArticleHomepageSlider();
        result.setArticleSlider(listSlides);

        // categories
        List<CategoryViewDto> listCategory = new ArrayList<>();
        List<CmsCategory> listCategories = categoryRepository.getHomePageCategory();
        if (listCategories != null && listCategories.size() > 0) {
            for (CmsCategory category : listCategories) {
                CategoryViewDto categoryView = new CategoryViewDto(category);
                List<CmsArticleDto> listArticle = new ArrayList<>();
                CmsSearchDto searchDto = new CmsSearchDto();
                searchDto.setPageIndex(1);
                searchDto.setPageSize(5);
                searchDto.setCategoryId(categoryView.getId());
                searchDto.setIsActive(true);

                Page<CmsArticleDto> pageArticle = articleService.paging(searchDto);
                if (pageArticle != null && pageArticle.getContent() != null && pageArticle.getContent().size() > 0) {
                    listArticle = pageArticle.getContent();
                }
                if (listArticle != null && listArticle.size() > 0) {
                    List<ArticleViewDto> listViewArticle = new ArrayList<>();
                    for (CmsArticleDto article : listArticle) {
                        listViewArticle.add(new ArticleViewDto(article));
                    }

                    categoryView.setArticles(listViewArticle);
                }

                listCategory.add(categoryView);
            }

        }

        result.setListCategory(listCategory);

        // articles
        List<ArticleViewDto> listHomeArticles = articleRepository.getArticleDisplayHomePage();
        result.setListArticle(listHomeArticles);
        return result;
    }

    private List<UUID> getArticleByCategory(CmsCategory category) {
        if (category != null && category.getId() != null) {
            UUID idCategory = category.getId();
            // String hql = "select ca.article.id from CmsCategoryArticle ca " +
            // " where (ca.category.id = :categoryId or ca.category.parent.id =
            // :categoryId)";
            // Query query = manager.createQuery(hql);
            // query.setParameter("categoryId", idCategory);

            // (ca.id = "40c843a6-9232-4e15-ac59-95e75866cec6" OR ca.parent_id =
            // "40c843a6-9232-4e15-ac59-95e75866cec6");

            String sql = "select ca.article_id from tbl_cms_category_article ca join tbl_cms_category c " +
                    "on (ca.category_id = c.id) and (c.id = :categoryId or c.parent_id = :categoryId)";
            Query query = manager.createNativeQuery(sql);
            query.setParameter("categoryId", idCategory.toString());

            List<String> result = (List<String>) query.getResultList();
            return result.stream().map(e -> UUID.fromString(e)).collect(Collectors.toList());
        }
        return null;
    }

    private CategoryViewDto getArticleByCategorySlug(CmsCategory category, Integer limit) {
        CategoryViewDto result = new CategoryViewDto();
        List<UUID> articleIds = this.getArticleByCategory(category);
        if (articleIds != null && articleIds.size() > 0) {
            result.setName(category.getTitle());
            String hql = "select new com.globits.cms.dto.home.ArticleViewDto(ca, false) " +
                    " from CmsArticle ca where ca.id in :articleIds ";
            Query query = manager.createQuery(hql, ArticleViewDto.class);
            query.setParameter("articleIds", articleIds);
            if (limit != null) {
                query.setMaxResults(limit);
            }
            result.getArticles().addAll(query.getResultList());
            return result;
        }
        return null;
    }
}
