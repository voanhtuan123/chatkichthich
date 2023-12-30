package com.globits.cms.repository;

import com.globits.cms.domain.CmsCategory;
import com.globits.cms.dto.CmsArticleDto;
import com.globits.cms.dto.home.CategoryViewDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CmsCategoryRepository extends JpaRepository<CmsCategory, UUID> {
	@Query(value = "select new com.globits.cms.dto.home.CategoryViewDto(e) from CmsCategory e where e.slug = :slug")
	List<CategoryViewDto> getAllCategoryBySlug(String slug);

	@Query(value = "select e from CmsCategory e where e.mapObject.slug like :slug")
	CmsCategory getCategoryBySlug(String slug);

	@Query("select c from CmsCategory c Where c.isActive = true order by c.positionIndex ")
	List<CmsCategory> getAllCategoryActive();

	@Query("select c from CmsCategory c Where c.slug = ?1 ")
	List<CmsCategory> findSlug(String slug);

	@Query("select c.id from CmsCategory c Where c.parent.id = ?1 ")
	List<UUID> findListIDCategoryByParentId(UUID uuid);

	@Query("select new com.globits.cms.dto.CmsArticleDto(a) from CmsArticle a where a.primaryCategory.id = ?1 and a.status != 1 order by a.publishDate desc")
	List<CmsArticleDto> getListArticle(UUID categoryId);

	@Query("select c from CmsCategory c Where c.isActive = true and c.showHomePage = true order by c.positionIndex")
	List<CmsCategory> getHomePageCategory();
}
