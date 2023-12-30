package com.globits.cms.repository;


import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.Website;
import com.globits.cms.dto.WebsiteDto;

@Repository
public interface WebsiteRepository extends JpaRepository<Website, UUID>{

	@Query("select new com.globits.cms.dto.WebsiteDto(entity) from Website entity where entity.domain=?1")
	WebsiteDto getWebsite(String domainName);

	@Query("select count(entity) from Website entity where entity.domain = :domain")
	Long countDuplicate(String domain);

	@Query("select entity.id from Website entity where entity.parent.id = :parentId")
	List<UUID> getListByParentId(UUID parentId);
	

}
