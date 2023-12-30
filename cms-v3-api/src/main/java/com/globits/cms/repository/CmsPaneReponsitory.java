package com.globits.cms.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.CmsPane;
import com.globits.cms.dto.CmsPaneDto;

@Repository
public interface CmsPaneReponsitory extends JpaRepository<CmsPane, UUID>{

	@Query("select cp from CmsPane cp where cp.id = ?1")
	CmsPane getById(UUID id);
}
