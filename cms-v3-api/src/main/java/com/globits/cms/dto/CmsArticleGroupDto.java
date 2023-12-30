package com.globits.cms.dto;

import com.globits.cms.domain.CmsArticleGroup;
import com.globits.cms.domain.Website;
import com.globits.core.domain.FileDescription;

public class CmsArticleGroupDto extends BaseObjectDto {
	private String name;
	private String description;
	private String slug;
	private Integer positionIndex;
	private FileDescription avatar;
	private WebsiteDto website;
	private String code;

	public CmsArticleGroupDto(CmsArticleGroup entity) {
		super();
		if (entity != null) {
			this.setId(entity.getId());
			this.avatar = entity.getAvatar();
			this.description = entity.getDescription();
			this.name = entity.getName();
			this.slug = entity.getSlug();
			this.positionIndex = entity.getPositionIndex();
			if (entity.getWebsite() != null) {
				this.website = new WebsiteDto(entity.getWebsite());
			}
		}
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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Integer getPositionIndex() {
		return positionIndex;
	}

	public void setPositionIndex(Integer positionIndex) {
		this.positionIndex = positionIndex;
	}

	public FileDescription getAvatar() {
		return avatar;
	}

	public void setAvatar(FileDescription avatar) {
		this.avatar = avatar;
	}

	public CmsArticleGroupDto() {

	}

	public WebsiteDto getWebsite() {
		return website;
	}

	public void setWebsite(WebsiteDto website) {
		this.website = website;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
