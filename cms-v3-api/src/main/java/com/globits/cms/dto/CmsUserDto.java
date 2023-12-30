package com.globits.cms.dto;

import java.util.Set;

import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;

public class CmsUserDto extends UserDto {
	Set<UserCategoryPermissionsDto> userCategoryPermissions;

	public Set<UserCategoryPermissionsDto> getUserCategoryPermissions() {
		return userCategoryPermissions;
	}

	public void setUserCategoryPermissions(Set<UserCategoryPermissionsDto> userCategoryPermissions) {
		this.userCategoryPermissions = userCategoryPermissions;
	}

	public CmsUserDto() {
		super();
	}

	public CmsUserDto(User entity) {
		super();
	}
	
}
