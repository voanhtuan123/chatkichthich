package com.globits.cms.repository;

import java.util.List;
import java.util.UUID;

import com.globits.cms.dto.home.ArticleViewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.CmsArticle;
import com.globits.cms.dto.CmsArticleDto;

@Repository
public interface CmsArticleRepsitory extends JpaRepository<CmsArticle, UUID> {
	@Query(value = "select new com.globits.cms.dto.home.ArticleViewDto(e, true) from CmsArticle e where e.plugInTheFocus is true order by e.positionIndex")
	List<ArticleViewDto> getArticleDisplayHomePage();

	@Query(value = "select new com.globits.cms.dto.home.ArticleViewDto(e, true) from CmsArticle e where e.plugInTheItem is true order by e.positionIndex")
	List<ArticleViewDto> getArticleHomepageSlider();

	@Query("select new com.globits.cms.dto.CmsArticleDto(a, false) from CmsArticle a where a.primaryCategory.id=?1 order by a.publishDate desc")
	Page<CmsArticleDto> getPageCmsArticleByCategory(UUID categoryId, Pageable pageable);

	@Query("select new com.globits.cms.dto.CmsArticleDto(a, false) from CmsArticle  a"
			+ " join CmsCategoryArticle b on a.id=b.article.id where ( a.primaryCategory.id=?1 or b.category.id=?1  ) order by a.publishDate desc")
	Page<CmsArticleDto> getPageCmsArticleByCategoryAndSubCategory(UUID categoryId, Pageable pageable);

	@Query("select new com.globits.cms.dto.CmsArticleDto(a, false) from CmsArticle a order by a.publishDate desc")
	Page<CmsArticleDto> getPageCmsArticleDto(Pageable pageable);

	@Query("select COUNT(a) from CmsArticle a where a.slug = ?1 AND (a.id<>?2 OR ?2 is null)")
	Long countBySlug(String slug, UUID id);

	@Query("select new com.globits.cms.dto.CmsArticleDto(a, false) from CmsArticle a where a.isActive =?1 ")
	List<CmsArticleDto> getListCmsArticleDto(Boolean getIsHoverArticle);

	@Query("select new com.globits.cms.dto.CmsArticleDto(a, false) from CmsArticle a where a.isActive = true and a.primaryCategory.id=?1 ")
	List<CmsArticleDto> getListCmsArticleByCategory(UUID categoryId);
}
