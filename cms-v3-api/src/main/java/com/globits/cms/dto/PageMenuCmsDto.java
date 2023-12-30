package com.globits.cms.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.globits.cms.domain.PageMenuCms;
import com.globits.cms.domain.Website;

public class PageMenuCmsDto extends BaseObjectDto {
    private static final long serialVersionUID = -7509002866849481728L;

    private String code;
    private String name;
    private String linkUrl;
    private PageMenuCmsDto parent;
    private List<PageMenuCmsDto> subPageMenu = new ArrayList<>();
    private Website website;
    private String type;
    private Integer positionIndex;// thu tu

    public PageMenuCmsDto() {
    }

    public PageMenuCmsDto(PageMenuCms entity) {
        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();
        linkUrl = entity.getLinkUrl();
        type = entity.getType();
        this.positionIndex = entity.getPositionIndex();
    }

    public PageMenuCmsDto(PageMenuCms entity, boolean getChild, boolean getParent) {
        this.id = entity.getId();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.type = entity.getType();
        this.linkUrl = entity.getLinkUrl();
        this.website = entity.getWebsite();
        this.positionIndex = entity.getPositionIndex();
        if (getChild)
            if (entity.getSubPageMenu() != null) {
                List<PageMenuCmsDto> subPage = new ArrayList<>();
                for (PageMenuCms cat : entity.getSubPageMenu()) {
                    subPage.add(new PageMenuCmsDto(cat));
                }
                Comparator<PageMenuCmsDto> c = (s1, s2) -> s1.getCode().compareTo(s2.getCode());
                subPage.sort(c);
                setSubPageMenu(subPage);
            }
        if (getParent && entity.getParent() != null) {
            this.parent = new PageMenuCmsDto(entity.getParent(), false, true);
        }

    }

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

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public PageMenuCmsDto getParent() {
        return parent;
    }

    public void setParent(PageMenuCmsDto parent) {
        this.parent = parent;
    }

    public List<PageMenuCmsDto> getSubPageMenu() {
        return subPageMenu;
    }

    public void setSubPageMenu(List<PageMenuCmsDto> subPageMenu) {
        this.subPageMenu = subPageMenu;
    }

    public Website getWebsite() {
        return website;
    }

    public void setWebsite(Website website) {
        this.website = website;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(Integer positionIndex) {
        this.positionIndex = positionIndex;
    }

}
