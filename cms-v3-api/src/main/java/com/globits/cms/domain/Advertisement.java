package com.globits.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_cms_avertisement")
@XmlRootElement
public class Advertisement extends BaseObject{
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="adv_type")
	private Integer advType;
	@Column(name="url")
	private String url;
	@Column(name="image_path")
	private String imagePath;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAdvType() {
		return advType;
	}
	public void setAdvType(Integer advType) {
		this.advType = advType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	 
}
