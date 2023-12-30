package com.globits.cms.dto;

import java.util.UUID;

import com.globits.cms.domain.CmsCategoryArticle;

public class CmsCategoryArticleDto {
    private UUID id;
    private UUID categoryId;
    private CmsCategoryDto category;
    private CmsArticleDto article;
    private boolean isCheck = false;

    public CmsCategoryArticleDto() {
        super();
    }

    public CmsCategoryArticleDto(CmsCategoryArticle entity) {
        this.id = entity.getId();
        this.article = new CmsArticleDto(entity.getArticle(), false);
        this.category = new CmsCategoryDto(entity.getCategory(), false, false);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CmsCategoryDto getCategory() {
        return category;
    }

    public void setCategory(CmsCategoryDto category) {
        this.category = category;
    }

    public CmsArticleDto getArticle() {
        return article;
    }

    public void setArticle(CmsArticleDto article) {
        this.article = article;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
