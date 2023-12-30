package com.globits.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_tour_category")
@XmlRootElement
public class TourCategory extends BaseObject {

	private static final long serialVersionUID = -8361305028832697719L;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@ManyToOne
	@JoinColumn(name="parent_id")
	private TourCategory parent;
	@Column(name="website")
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
	public Website getWebsite() {
		return website;
	}
	public void setWebsite(Website website) {
		this.website = website;
	}
	public TourCategory getParent() {
		return parent;
	}
	public void setParent(TourCategory parent) {
		this.parent = parent;
	}
	
	
}
