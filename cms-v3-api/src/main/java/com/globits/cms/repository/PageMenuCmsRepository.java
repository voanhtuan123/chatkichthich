package com.globits.cms.repository;

import com.globits.cms.domain.PageMenuCms;
import com.globits.cms.dto.home.MenuHeaderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PageMenuCmsRepository extends JpaRepository<PageMenuCms, UUID> {
	@Query("select count(entity) from PageMenuCms entity WHERE entity.code like :code")
	Long countDuplicate(String code);

	@Query(value = "select new com.globits.cms.dto.home.MenuHeaderDto(e, true) from PageMenuCms e where e.parent is null order by e.positionIndex")
	List<MenuHeaderDto> getListMenuHeader();
}
