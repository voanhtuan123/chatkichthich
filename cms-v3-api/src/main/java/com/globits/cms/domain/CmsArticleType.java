package com.globits.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_cms_article_type")
@XmlRootElement
public class CmsArticleType extends BaseObject {
	
	@Column(name = "name")
	private String name;// Tên loại bài báo
	@Column(name = "code")
	private String code;// Mã loại bài báo
	@Column(name="description", columnDefinition="TEXT")
	private String description;// Mô tả
	@Column(name = "priority")
	private int priority;// Độ ưu tiên
	@ManyToOne
	@JoinColumn(name="website_id")
	private Website website;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

}
