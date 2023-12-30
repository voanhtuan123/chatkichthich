package com.globits.cms.dto.home;

import com.globits.cms.domain.PageMenuCms;
import com.globits.cms.util.VNCharacterUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuHeaderDto {
    private String name;
    private String slug;
    private String type; // 1: url, 2: category, 3: article
    private List<MenuHeaderDto> children = new ArrayList<>();

    private String linkUrl;

    public MenuHeaderDto() {

    }

    public MenuHeaderDto(PageMenuCms entity, boolean getFullMenu) {
        if(entity != null) {
            this.name = entity.getName();
            this.slug = entity.getName() != null ?
                    VNCharacterUtils.replaceAndRemoveSpace(entity.getName().toLowerCase()) : null;
            this.type = entity.getType();
            this.linkUrl = entity.getLinkUrl();
            if(getFullMenu && entity.getSubPageMenu() != null && entity.getSubPageMenu().size() > 0) {
                for(PageMenuCms child : entity.getSubPageMenu()) {
                    this.children.add(new MenuHeaderDto(child, false));
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MenuHeaderDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuHeaderDto> children) {
        this.children = children;
    }

    public String getLinkUrl() { return linkUrl; }

    public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }
}
