package com.globits.cms.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_page")
@XmlRootElement
public class CmsPage extends BaseObject {
	private static final long serialVersionUID = 3201873479918374036L;
	@Column(name = "template_path")
	private String templatePath;
	@Column(name = "code", nullable = false, unique = true)
	private String code;// Code cũng chính là URL của hệ thống
	@Column(name = "name")
	private String name;
	@OneToMany(mappedBy = "page", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CmsPane> panes;
	@ManyToOne
	@JoinColumn(name = "website_id")
	private Website website;

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CmsPane> getPanes() {
		return panes;
	}

	public void setPanes(Set<CmsPane> panes) {
		this.panes = panes;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

}
