package com.globits.cms.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Type;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_cms_map_object")
@XmlRootElement
public class CmsMapObject extends BaseObject {
	private static final long serialVersionUID = 1L;
	@Column(name="slug",unique = true)
	private String slug;
	@Column(name="object_type")
	private String objectType;
	@Type(type = "uuid-char")
	@Column(name="object_id")
	private UUID objectId;
	
	@OneToOne(mappedBy = "mapObject", optional = true, fetch = FetchType.EAGER)
	private CmsArticle article;

	@OneToOne(mappedBy = "mapObject",optional = true, fetch = FetchType.EAGER)
	private CmsCategory category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="website_id")
	private Website website;
	
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
	public CmsArticle getArticle() {
		return article;
	}
	public void setArticle(CmsArticle article) {
		this.article = article;
	}
	public CmsCategory getCategory() {
		return category;
	}
	public void setCategory(CmsCategory category) {
		this.category = category;
	}
	public Website getWebsite() {
		return website;
	}
	public void setWebsite(Website website) {
		this.website = website;
	}
	
	
}
