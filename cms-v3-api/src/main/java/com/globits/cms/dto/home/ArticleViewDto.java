package com.globits.cms.dto.home;

import com.globits.cms.domain.CmsArticle;
import com.globits.cms.dto.CmsArticleDto;

import java.util.Date;
import java.util.UUID;

public class ArticleViewDto {
    private UUID id;
    private String title;
    private String summary;
    private Date publishDate;
    private String slug;

    private String titleImageUrl;

    public ArticleViewDto() {

    }

    public ArticleViewDto(CmsArticleDto dto) {
        this.id = dto.getId();
        this.title = dto.getTitle();
        this.summary = dto.getSummary();
        this.slug = dto.getSlug();
        this.publishDate = dto.getPublishDate();
        this.titleImageUrl = dto.getTitleImageUrl();

    }

    public ArticleViewDto(CmsArticle entity, boolean notShowHome) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.slug = entity.getMapObject().getSlug();
        this.titleImageUrl = entity.getTitleImageUrl();
        if (notShowHome) {
            this.summary = entity.getSummary();
            this.publishDate = entity.getPublishDate();
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

    public String getTitleImageUrl() {
        return titleImageUrl;
    }

    public void setTitleImageUrl(String titleImageUrl) {
        this.titleImageUrl = titleImageUrl;
    }
}
