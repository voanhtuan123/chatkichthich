package com.globits.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.FileDescription;

/*Nhóm tin*/
@Entity
@Table(name = "tbl_cms_article_group")
@XmlRootElement
public class CmsArticleGroup extends BaseObject {

	@Column(name = "name")
	private String name;// Tên

	@Column(name = "code")
	private String code; // mô tả

	@Column(name = "description", columnDefinition = "TEXT")
	private String description; // mô tả

	@Column(name = "slug")
	private String slug;

	@Column(name = "position_index")
	private Integer positionIndex; // Vị trí (0: Trên tiêu điểm - 1: Cuối trang) Constant

	@ManyToOne
	@JoinColumn(name = "avatar_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private FileDescription avatar; // Ảnh đại diện

	@ManyToOne
	@JoinColumn(name = "website_id")
	private Website website;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public Integer getPositionIndex() {
		return positionIndex;
	}

	public void setPositionIndex(Integer positionIndex) {
		this.positionIndex = positionIndex;
	}

	public FileDescription getAvatar() {
		return avatar;
	}

	public void setAvatar(FileDescription avatar) {
		this.avatar = avatar;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
