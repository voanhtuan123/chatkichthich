package com.globits.cms.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_cms_category")
@XmlRootElement
public class CmsCategory extends BaseObject{
	private static final long serialVersionUID = -7509002866849481728L;
	@Column(name="title")
	private String title;
	@Column(name="code")
	private String code;
	@Column(name="description", columnDefinition="TEXT")
	private String description;
	
	@Column(name="slug")
	private String slug;
	@ManyToOne
	@JoinColumn(name="parent_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private CmsCategory parent;
	@Column(name="is_active")
	private Boolean isActive;
	@Column(name="show_menu")
	private Boolean isShowMenu;
	@Column(name="position_index")
	private Integer positionIndex;
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name="map_object_id")
	private CmsMapObject mapObject;
	
	@OneToMany(mappedBy = "parent")
	@OrderBy(value = "positionIndex ASC")
	private Set<CmsCategory> subCategories;
	
	@ManyToOne
	@JoinColumn(name="website_id")
	private Website website;
	@Column(name="show_home_page")
	private Boolean showHomePage;
	@Column(name="category_type")
	private Integer categoryType =1;// Const.CategoryType
	@Column(name="display_type")
	private Integer displayType = 1;
	@Column(name="link_url")
	private String linkUrl;//đường dẫn link
	@Column(name="page_index")
	private Integer pageIndex;
	@Column(name="page_size")
	private Integer pageSize;
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
	public CmsCategory getParent() {
		return parent;
	}
	public void setParent(CmsCategory parent) {
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
	public CmsMapObject getMapObject() {
		return mapObject;
	}
	public void setMapObject(CmsMapObject mapObject) {
		this.mapObject = mapObject;
	}
	public Set<CmsCategory> getSubCategories() {
		return subCategories;
	}
	public void setSubCategories(Set<CmsCategory> subCategories) {
		this.subCategories = subCategories;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Website getWebsite() {
		return website;
	}
	public void setWebsite(Website website) {
		this.website = website;
	}
	
	public Boolean getShowHomePage() {
		return showHomePage;
	}
	public void setShowHomePage(Boolean showHomePage) {
		this.showHomePage = showHomePage;
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
	
}
