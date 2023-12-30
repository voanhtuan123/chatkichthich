package com.globits.cms.dto;

import java.util.UUID;

import com.globits.cms.domain.CmsArticleType;
import com.globits.cms.domain.Website;

public class CmsArticleTypeDto {
	private UUID id;
	private String name;// Tên loại bài báo
	private String code;// Mã loại bài báo
	private String description;// Mô tả
	private int priority;
	
	private boolean isDuplicate;
	private String dupName;
	private String dupCode;
	private WebsiteDto website;

	public boolean isDuplicate() {
		return isDuplicate;
	}

	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	public String getDupName() {
		return dupName;
	}

	public void setDupName(String dupName) {
		this.dupName = dupName;
	}

	public String getDupCode() {
		return dupCode;
	}

	public void setDupCode(String dupCode) {
		this.dupCode = dupCode;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public WebsiteDto getWebsite() {
		return website;
	}

	public void setWebsite(WebsiteDto website) {
		this.website = website ;
	}

	public CmsArticleTypeDto() {
		super();
	}
	
	public CmsArticleTypeDto(CmsArticleType entity) {
		if(entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.priority = entity.getPriority();
			if(entity.getWebsite() != null) {
				this.website = new  WebsiteDto(entity.getWebsite());
			}
		}
	}
	
	public CmsArticleType converCmsArticleTypeDto(CmsArticleTypeDto dto) {
		CmsArticleType at = new CmsArticleType();
		at.setId(dto.getId());
		at.setCode(dto.getCode());
		at.setName(dto.getName());
		at.setDescription(dto.getDescription());
		at.setPriority(dto.getPriority());
//		if(dto.getWebsite() != null) {
//			at.setWebsite(dto.getWebsite());
//		}
		return at;
	}
}
