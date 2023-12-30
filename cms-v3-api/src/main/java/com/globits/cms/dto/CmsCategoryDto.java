package com.globits.cms.dto;

import com.globits.cms.Const;
import com.globits.cms.domain.CmsCategory;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
//import com.globits.cms.view.dto.CmsArticleDto;

public class CmsCategoryDto {
	private UUID id;
	private String title;
	private String code;
	private String description;

	private String slug;
	private CmsCategoryDto parent;
	private Boolean isActive;
	private Boolean isShowMenu;
	private Integer positionIndex;
	private boolean ischeck = false;
	private List<CmsCategoryDto> subCategories = new ArrayList<>();
	private Page<CmsArticleDto> articles;// Các bài viết hiển thị trực tiếp
	private List<CmsArticleDto> listArticles;// Các bài viết hiển thị trực tiếp
	private List<CmsArticleDto> allArticles;// Các bài viết hiển thị trực tiếp
	private WebsiteDto website;
	private Boolean showHomePage;
	private Integer categoryType;// =1:Danh sách bài báo, =2:Link
	private Integer displayType;// =1: Hiển thị toán bộ bài viêt; =2: Hiển thị bài viết tiêu điểm
	private String linkUrl;// đường dẫn link
	private Boolean isShowLink = false;
	private Integer pageIndex;
	private Integer pageSize;

	public CmsCategoryDto() {
		super();
	}

	public CmsCategoryDto(CmsCategory entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.description = entity.getDescription();
			this.title = entity.getTitle();
			this.code = entity.getCode();
			this.slug = entity.getSlug();
			this.pageIndex = entity.getPageIndex();
			this.pageSize = entity.getPageSize();
			if (entity.getParent() != null) {
				this.parent = new CmsCategoryDto(entity.getParent());
			}
			if (entity.getWebsite() != null) {
				this.website = new WebsiteDto(entity.getWebsite());
			}
			this.isActive = entity.getIsActive();
			this.isShowMenu = entity.getIsShowMenu();
			this.positionIndex = entity.getPositionIndex();
			this.showHomePage = entity.getShowHomePage();
			this.categoryType = entity.getCategoryType();
			this.displayType = entity.getDisplayType();
			if (entity.getCategoryType() != null
					&& entity.getCategoryType().equals(Const.CategoryType.Link.getValue())) {
				this.isShowLink = true;
			}
			this.linkUrl = entity.getLinkUrl();
		}
	}

	public CmsCategoryDto(CmsCategory entity, boolean getChild) {
		if (entity != null) {
			this.title = entity.getTitle();
			this.slug = entity.getSlug();
			if (getChild && entity.getSubCategories() != null && entity.getSubCategories().size() > 0) {
				for (CmsCategory child : entity.getSubCategories()) {
					this.subCategories.add(new CmsCategoryDto(child, true));
				}
			}
		}
	}

	public CmsCategoryDto(CmsCategory entity, boolean getChild, boolean getParent) {
		if (entity != null) {
			this.id = entity.getId();
			this.description = entity.getDescription();
			this.title = entity.getTitle();
			this.slug = entity.getSlug();
			this.isActive = entity.getIsActive();
			this.isShowMenu = entity.getIsShowMenu();
			this.positionIndex = entity.getPositionIndex();
			this.code = entity.getCode();
			this.showHomePage = entity.getShowHomePage();
			this.pageIndex = entity.getPageIndex();
			this.pageSize = entity.getPageSize();
			if (entity.getWebsite() != null) {
				this.website = new WebsiteDto(entity.getWebsite());
			}
			if (getChild) {
				if (entity.getSubCategories() != null) {
					List<CmsCategoryDto> subCategories = new ArrayList<CmsCategoryDto>();
					for (CmsCategory cat : entity.getSubCategories()) {
						subCategories.add(new CmsCategoryDto(cat, true, true));
					}
					this.setSubCategories(subCategories);
				}
			}
			if (getParent) {
				if (entity.getParent() != null) {
					this.parent = new CmsCategoryDto(entity.getParent(), false, true);
				}
			}
			this.categoryType = entity.getCategoryType();
			this.displayType = entity.getCategoryType();
			this.linkUrl = entity.getLinkUrl();
			if (entity.getCategoryType() != null
					&& entity.getCategoryType().equals(Const.CategoryType.Link.getValue())) {
				this.isShowLink = true;
			}
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public CmsCategoryDto getParent() {
		return parent;
	}

	public void setParent(CmsCategoryDto parent) {
		this.parent = parent;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsShowMenu() {
		return isShowMenu;
	}

	public void setIsShowMenu(Boolean isShowMenu) {
		this.isShowMenu = isShowMenu;
	}

	public Integer getPositionIndex() {
		return positionIndex;
	}

	public void setPositionIndex(Integer positionIndex) {
		this.positionIndex = positionIndex;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isIscheck() {
		return ischeck;
	}

	public void setIscheck(boolean ischeck) {
		this.ischeck = ischeck;
	}

	public UUID getParentId() {
		if (this.parent != null) {
			return this.parent.id;
		}
		return null;
	}

	public List<CmsCategoryDto> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(List<CmsCategoryDto> subCategories) {
		this.subCategories = subCategories;
	}

	public Page<CmsArticleDto> getArticles() {
		return articles;
	}

	public void setArticles(Page<CmsArticleDto> articles) {
		this.articles = articles;
	}

	public WebsiteDto getWebsite() {
		return website;
	}

	public void setWebsite(WebsiteDto website) {
		this.website = website;
	}

	public Boolean getShowHomePage() {
		return showHomePage;
	}

	public void setShowHomePage(Boolean showHomePage) {
		this.showHomePage = showHomePage;
	}

	public List<CmsArticleDto> getListArticles() {
		return listArticles;
	}

	public void setListArticles(List<CmsArticleDto> listArticles) {
		this.listArticles = listArticles;
	}

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	public Integer getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public Boolean getIsShowLink() {
		return isShowLink;
	}

	public void setIsShowLink(Boolean isShowLink) {
		this.isShowLink = isShowLink;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<CmsArticleDto> getAllArticles() {
		return allArticles;
	}

	public void setAllArticles(List<CmsArticleDto> allArticles) {
		this.allArticles = allArticles;
	}
}
