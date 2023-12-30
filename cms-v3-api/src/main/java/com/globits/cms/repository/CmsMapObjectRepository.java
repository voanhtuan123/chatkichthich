package com.globits.cms.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.CmsMapObject;
import com.globits.cms.dto.CmsMapObjectDto;

@Repository
public interface CmsMapObjectRepository extends JpaRepository<CmsMapObject, UUID>{
	@Query("from CmsMapObject a where a.slug = ?1  ")
	CmsMapObject getCmsMapByKey(String slug);

	@Query("select new com.globits.cms.dto.CmsMapObjectDto(a) from CmsMapObject a where a.category!=null")
	List<CmsMapObjectDto> getListCmsCategoryMapDto();

	@Query("from CmsMapObject a where a.category!=null")
	List<CmsMapObject> getListCmsCategoryMap();

	@Query("from CmsMapObject a where a.category.website.id=?1")
	List<CmsMapObject> getListCmsCategoryMap(UUID websiteId);

	@Query("SELECT COUNT(a.id) from CmsMapObject a where a.slug=?1 AND (a.id<>?2 OR ?2 is null)")
	Long countBySlug(String slug,UUID id);
	
	
}
