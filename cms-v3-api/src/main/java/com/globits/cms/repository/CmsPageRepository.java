package com.globits.cms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.CmsMapObject;
import com.globits.cms.domain.CmsPage;
import com.globits.cms.dto.CmsPageDto;

@Repository
public interface CmsPageRepository extends JpaRepository<CmsPage, UUID>{

	@Query("select new com.globits.cms.dto.CmsPageDto(cp) from CmsPage cp where cp.id = ?1")
	CmsPageDto findOneById(UUID id);
	
	@Query("select new com.globits.cms.dto.CmsPageDto(cp) from CmsPage cp where cp.templatePath = ?1")
	List<CmsPageDto> getPageByTemplatePath(String templatePath);
}