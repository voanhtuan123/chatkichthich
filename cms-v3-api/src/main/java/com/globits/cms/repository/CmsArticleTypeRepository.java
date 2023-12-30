package com.globits.cms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.CmsArticleType;
import com.globits.cms.dto.CmsArticleTypeDto;

@Repository
public interface CmsArticleTypeRepository extends JpaRepository<CmsArticleType, UUID> {
	@Query("select u from CmsArticleType u where u.id = ?1")
	CmsArticleType findById(Long id);
	@Query("select new com.globits.cms.dto.CmsArticleTypeDto(cs) from CmsArticleType cs  ")
	Page<CmsArticleTypeDto> getListArticleType(Pageable pageable);
	@Query("select u from CmsArticleType u where u.code = ?1")
	List<CmsArticleType> findListByCode(String code);
	
	@Query("select count(entity.id) from CmsArticle entity where entity.primaryCategory.id =?1")
	Long findByArticle(UUID id );
}
