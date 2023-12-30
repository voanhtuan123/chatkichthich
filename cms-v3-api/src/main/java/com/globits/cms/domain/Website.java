package com.globits.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.Organization;
@Entity
@Table(name = "tbl_website")
@XmlRootElement
public class Website extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@Column(name="domain", unique = true)
	private String domain;
	@Column(name="template")
	private String template;
	@Column(name="logo_path")
	private String logoPath;
	@ManyToOne
	@JoinColumn(name="org_id")
	private Organization org;
	@ManyToOne
	@JoinColumn(name="parent_id")
	private Website parent;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	public Website getParent() {
		return parent;
	}
	public void setParent(Website parent) {
		this.parent = parent;
	}
	
	
}
