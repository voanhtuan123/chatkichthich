package com.globits.cms.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SearchDto {
	private String keyword;
	private String title;
	private String description;
	private String summary;
	private List<UUID> ids;
	private UUID cmsArticleTypeId;
	private UUID articleId;
	private UUID categoryId;
	private int pageIndex;
	private int pageSize;
	private Date toDate;
	private Date since;
	private Integer status;
	private UUID websiteId;
	private Boolean isActive;
	private Boolean getOnlyParent;
	private String slug;
	private UUID id;
	private UUID parentId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Boolean getGetOnlyParent() {
		return getOnlyParent;
	}

	public void setGetOnlyParent(Boolean getOnlyParent) {
		this.getOnlyParent = getOnlyParent;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public UUID getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(UUID categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<UUID> getIds() {
		return ids;
	}

	public void setIds(List<UUID> ids) {
		this.ids = ids;
	}

	public UUID getCmsArticleTypeId() {
		return cmsArticleTypeId;
	}

	public void setCmsArticleTypeId(UUID cmsArticleTypeId) {
		this.cmsArticleTypeId = cmsArticleTypeId;
	}

	public UUID getArticleId() {
		return articleId;
	}

	public void setArticleId(UUID articleId) {
		this.articleId = articleId;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public UUID getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(UUID websiteId) {
		this.websiteId = websiteId;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
