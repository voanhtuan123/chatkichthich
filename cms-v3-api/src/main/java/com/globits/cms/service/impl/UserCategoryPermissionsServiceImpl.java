package com.globits.cms.service.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globits.cms.domain.CmsCategory;
import com.globits.cms.domain.UserCategoryPermissions;
import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.UserCategoryPermissionsDto;
import com.globits.cms.repository.CmsCategoryRepository;
import com.globits.cms.repository.UserCategoryPermissionsRepository;
import com.globits.cms.service.UserCategoryPermissionsService;
import com.globits.core.service.impl.GenericServiceImpl;

@Service
public class UserCategoryPermissionsServiceImpl extends GenericServiceImpl<UserCategoryPermissions, UUID> implements UserCategoryPermissionsService {

	@Autowired
	private UserCategoryPermissionsRepository userCategoryPermissionsRepository;

	@Autowired
	private CmsCategoryRepository cmsCategoryRepository;

	@Override
	public Hashtable<UUID, UserCategoryPermissionsDto> getNewListOrGetListByUserId(Long userId) {

		Hashtable<UUID, UserCategoryPermissionsDto> hashtableUserCategoryPermissions = new Hashtable<UUID, UserCategoryPermissionsDto>();
		
		List<CmsCategory> listCmsCategory = cmsCategoryRepository.getAllCategoryActive();
		if (listCmsCategory != null && listCmsCategory.size() > 0) {
			
			for (CmsCategory category : listCmsCategory) {
				UserCategoryPermissionsDto item = hashtableUserCategoryPermissions.get(category.getId());
				if (item == null) {
					item = new UserCategoryPermissionsDto();
					item.setCategory(new CmsCategoryDto(category, false,false));
					hashtableUserCategoryPermissions.put(category.getId(), item);
				}
			}
			
			if (userId != null && userId > 0) {
				//lấy ra list đã gán quyền của user trước đó để điền giá trị
				List<UserCategoryPermissions> userCategoryPermissions = userCategoryPermissionsRepository.getByUserId(userId);
				if (userCategoryPermissions != null && userCategoryPermissions.size() > 0) {
					for (UserCategoryPermissions ucp : userCategoryPermissions) {
						
						UserCategoryPermissionsDto item = hashtableUserCategoryPermissions.get(ucp.getCategory().getId());
						if (item != null) {
							item = new UserCategoryPermissionsDto(ucp);
						}
						
					}
				}
			}
		}
		
		return hashtableUserCategoryPermissions;
	}

}
