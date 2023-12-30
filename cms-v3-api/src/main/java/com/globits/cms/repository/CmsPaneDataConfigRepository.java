package com.globits.cms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.CmsPaneDataConfig;
import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsPaneDataConfigDto;

@Repository
public interface CmsPaneDataConfigRepository extends JpaRepository<CmsPaneDataConfig, UUID> {

	@Query("select new com.globits.cms.dto.CmsPaneDataConfigDto(c) from CmsPaneDataConfig c")
	List<CmsPaneDataConfigDto> getAll();
}
