package com.globits.cms.service;

import java.util.Hashtable;
import java.util.UUID;

import com.globits.cms.domain.UserCategoryPermissions;
import com.globits.cms.dto.UserCategoryPermissionsDto;
import com.globits.core.service.GenericService;

public interface UserCategoryPermissionsService extends GenericService<UserCategoryPermissions, UUID> {

	Hashtable<UUID, UserCategoryPermissionsDto> getNewListOrGetListByUserId(Long userId);

}
