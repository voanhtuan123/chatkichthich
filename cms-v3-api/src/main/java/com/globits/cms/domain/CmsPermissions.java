package com.globits.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

/* Quyền */
@Entity
@Table(name = "tbl_cms_permission")
@XmlRootElement
public class CmsPermissions extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "code")
	private String code;// Mã
	
	@Column(name = "name")
	private String name;// Tên
	
	@Column(name = "description")
	private String description;// mô tả

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
