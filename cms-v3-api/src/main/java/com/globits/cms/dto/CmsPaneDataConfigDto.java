package com.globits.cms.dto;

import java.util.UUID;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.globits.cms.domain.CmsArticle;
import com.globits.cms.domain.CmsCategory;
import com.globits.cms.domain.CmsPane;
import com.globits.cms.domain.CmsPaneDataConfig;

public class CmsPaneDataConfigDto extends BaseObjectDto {

	
	private Integer paneType;
	private CmsPaneDto pane;
	private CmsCategoryDto category;
	private CmsArticleDto article;
	
	public Integer getPaneType() {
		return paneType;
	}
	public void setPaneType(Integer paneType) {
		this.paneType = paneType;
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
	public CmsPaneDto getPane() {
		return pane;
	}
	public void setPane(CmsPaneDto pane) {
		this.pane = pane;
	}
	public CmsPaneDataConfigDto() {
		
	}
	public CmsPaneDataConfigDto(CmsPaneDataConfig entity) {
		if(entity != null) {
		this.id = entity.getId();
		this.paneType = entity.getPaneType();
		this.pane = new CmsPaneDto(entity.getPane());
		this.category = new CmsCategoryDto(entity.getCategory());
		this.article = new CmsArticleDto(entity.getArticle());
	
		}
	}
	
	public CmsPaneDataConfigDto(CmsPaneDataConfig entity,boolean check) {
		if(entity != null) {
		this.id = entity.getId();
		this.paneType = entity.getPaneType(); 
		this.category = new CmsCategoryDto(entity.getCategory());
		this.article = new CmsArticleDto(entity.getArticle());
	
		}
	}
	
}
