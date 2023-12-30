package com.globits.cms.dto;

import com.globits.cms.domain.CmsPermissions;

public class CmsPermissionsDto extends BaseObjectDto {
	private String code;// Mã
	private String name;// Tên
	private String description;// mô tả
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CmsPermissionsDto() {
		super();
	}

	public CmsPermissionsDto(CmsPermissions entity) {
		super();
		if (entity != null) {
			this.setId(entity.getId());
			this.code = entity.getCode();
			this.name = entity.getName();
			this.description = entity.getDescription();
		}
	}
}
