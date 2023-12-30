package com.globits.cms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.cms.domain.UserCategoryPermissions;
import com.globits.cms.dto.UserCategoryPermissionsDto;

@Repository
public interface UserCategoryPermissionsRepository extends JpaRepository<UserCategoryPermissions, UUID> {

	@Query("select ucp from UserCategoryPermissions ucp where ucp.user.id = ?1 ")
	List<UserCategoryPermissions> getByUserId(Long id);
	
}
