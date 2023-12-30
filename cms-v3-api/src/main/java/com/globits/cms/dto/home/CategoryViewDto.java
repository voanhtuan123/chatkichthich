package com.globits.cms.dto.home;

import com.globits.cms.domain.CmsCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryViewDto {
    private UUID id;
    private String name;
    private String slug;
    private List<CategoryViewDto> children = new ArrayList<>();
    private List<ArticleViewDto> articles = new ArrayList<>(); // bai viet theo category

    private Number displayType;

    public CategoryViewDto() {

    }

    public CategoryViewDto(CmsCategory entity) {
        if(entity != null) {
            this.id = entity.getId();
            this.name = entity.getTitle();
            this.slug = entity.getSlug();
            this.displayType = entity.getDisplayType();
            if(entity.getSubCategories() != null && entity.getSubCategories().size() > 0) {
                for(CmsCategory child : entity.getSubCategories()) {
                    this.children.add(new CategoryViewDto(child));
                }
            }
        }
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryViewDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryViewDto> children) {
        this.children = children;
    }

    public List<ArticleViewDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleViewDto> articles) {
        this.articles = articles;
    }

    public Number getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Number displayType) {
        this.displayType = displayType;
    }
}
