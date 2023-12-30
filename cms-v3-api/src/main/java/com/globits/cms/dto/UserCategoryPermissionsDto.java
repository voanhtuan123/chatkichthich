package com.globits.cms.dto;

import com.globits.cms.domain.UserCategoryPermissions;
import com.globits.security.dto.UserDto;

public class UserCategoryPermissionsDto extends BaseObjectDto {

	private UserDto user; // người dùng
	private CmsCategoryDto category; // Danh mục

	private Boolean isEditorialPermissions = false; // là quyền biên tập
	private Boolean isApprovalPermissions = false; // là quyền duyệt tin
	private Boolean isPostPermissions = false; // là quyền đăng tin
	private Boolean isPublishPermissions = false; // là quyền xuất bản

	public boolean isEditorialPermissions() {
		return isEditorialPermissions;
	}

	public void setEditorialPermissions(boolean isEditorialPermissions) {
		this.isEditorialPermissions = isEditorialPermissions;
	}

	public boolean isApprovalPermissions() {
		return isApprovalPermissions;
	}

	public void setApprovalPermissions(boolean isApprovalPermissions) {
		this.isApprovalPermissions = isApprovalPermissions;
	}

	public boolean isPostPermissions() {
		return isPostPermissions;
	}

	public void setPostPermissions(boolean isPostPermissions) {
		this.isPostPermissions = isPostPermissions;
	}

	public boolean isPublishPermissions() {
		return isPublishPermissions;
	}

	public void setPublishPermissions(boolean isPublishPermissions) {
		this.isPublishPermissions = isPublishPermissions;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public CmsCategoryDto getCategory() {
		return category;
	}

	public void setCategory(CmsCategoryDto category) {
		this.category = category;
	}

	public UserCategoryPermissionsDto() {
		super();
	}

	public UserCategoryPermissionsDto(UserCategoryPermissions entity) {
		super();
		if (entity != null) {
			this.setId(entity.getId());
			this.isEditorialPermissions = entity.isEditorialPermissions();
			this.isApprovalPermissions = entity.isApprovalPermissions();
			this.isPostPermissions = entity.isPostPermissions();
			this.isPublishPermissions = entity.isPublishPermissions();

			if (entity.getUser() != null) {
				this.user = new UserDto();
				this.user.setId(entity.getUser().getId());
			}
			if (entity.getCategory() != null) {
				this.category = new CmsCategoryDto(entity.getCategory(), false,false);
			}
		}
	}

}
