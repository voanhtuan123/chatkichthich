package com.globits.cms.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CmsSearchDto {
    private Integer pageIndex;
    private Integer pageSize;
    private String textSearch;
    private UUID parentId;
    private UUID categoryId;
    private Date fromDate;
    private Date toDate;
    private Integer status;
    private Integer websiteId;
    private String slug;
    private List<String> listSlug = new ArrayList<>();
    private Boolean isActive;
    private Boolean showOnSlider;
    private Boolean ShowOnHomePage;

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

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWebsiteId() {
        return websiteId;
    }

    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    public String getTextSearch() {
        return textSearch;
    }

    public void setTextSearch(String textSearch) {
        this.textSearch = textSearch;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<String> getListSlug() {
        return listSlug;
    }

    public void setListSlug(List<String> listSlug) {
        this.listSlug = listSlug;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getShowOnSlider() {
        return showOnSlider;
    }

    public void setShowOnSlider(Boolean showOnSlider) {
        this.showOnSlider = showOnSlider;
    }

    public Boolean getShowOnHomePage() {
        return ShowOnHomePage;
    }

    public void setShowOnHomePage(Boolean showOnHomePage) {
        ShowOnHomePage = showOnHomePage;
    }

}
