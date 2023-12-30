package com.globits.cms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.CmsCategoryArticle;

@Repository
public interface CmsCategoryArticleRepository extends JpaRepository<CmsCategoryArticle, UUID>{

	@Query("select ca from CmsCategoryArticle ca where ca.category is not null and ca.category.id = ?1 and ca.article is not null and ca.article.id = ?2")
	CmsCategoryArticle findByCategoryIdAndArticleId(UUID categoryId, UUID articleId);
	
	@Query("select ca.id from CmsCategoryArticle ca where ca.category.id = ?1 ")
	List<UUID> findByCategoryIdAndArticle(UUID id);

	
}
