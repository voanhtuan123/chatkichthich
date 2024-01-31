package com.globits.cms.dto;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.globits.cms.domain.CmsMapObject;

public class CmsMapObjectDto extends BaseObjectDto {
	private String slug;
	private String objectType;
	private UUID objectId;
	private Page<CmsArticleDto> articles;
	private List<CmsCategoryDto> listSubCategory;
	private CmsCategoryDto category;
	private CmsArticleDto article;
	private WebsiteDto website;
	
	
	
	public List<CmsCategoryDto> getListSubCategory() {
		return listSubCategory;
	}
	public void setListSubCategory(List<CmsCategoryDto> listSubCategory) {
		this.listSubCategory = listSubCategory;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public UUID getObjectId() {
		return objectId;
	}
	public void setObjectId(UUID objectId) {
		this.objectId = objectId;
	}
	
	public Page<CmsArticleDto> getArticles() {
		return articles;
	}
	public void setArticles(Page<CmsArticleDto> articles) {
		this.articles = articles;
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
	
	public WebsiteDto getWebsite() {
		return website;
	}
	public void setWebsite(WebsiteDto website) {
		this.website = website;
	}
	public CmsMapObjectDto() {
		
	}
	public CmsMapObjectDto(CmsMapObject entity) {
		if(entity.getWebsite()!=null) {
			this.website = new WebsiteDto();
			this.website.setId(entity.getWebsite().getId());
			this.website.setCode(entity.getWebsite().getCode());
			this.website.setName(entity.getWebsite().getName());
			this.website.setDomain(entity.getWebsite().getDomain());
		}
		if(entity.getArticle()!=null) {
			this.article = new CmsArticleDto();
			this.article.setId(entity.getArticle().getId());
			this.article.setContent(entity.getArticle().getContent());
			this.article.setNote(entity.getArticle().getNote());
		}
		if(entity.getCategory()!=null) {
			this.category = new CmsCategoryDto();
			this.category.setId(entity.getCategory().getId());
			this.category.setCode(entity.getCategory().getCode());
		}
	}
}
