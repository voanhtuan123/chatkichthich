package com.globits.cms.dto;

import com.globits.cms.Const;
import com.globits.cms.domain.CmsArticle;

import java.util.Date;
import java.util.UUID;

public class CmsArticleViewDto {
    private UUID id;
    private String title;
    private String summary;
    private Date publishDate;
    private String slug;
    private String type;

    public CmsArticleViewDto() {

    }

    public CmsArticleViewDto(CmsArticle entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.summary = entity.getSummary();
        this.publishDate = entity.getPublishDate();
        this.slug = entity.getMapObject() != null ? entity.getMapObject().getSlug() : null;
        this.type = Const.SlugTypeEnum.ARTICLE.getKey();
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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
}
