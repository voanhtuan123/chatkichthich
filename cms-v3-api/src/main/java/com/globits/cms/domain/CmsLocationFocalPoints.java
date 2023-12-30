package com.globits.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;

/*Vị trí cắm tiêu điểm*/
@Entity
@Table(name = "tbl_cms_location_focal_points")
@XmlRootElement
public class CmsLocationFocalPoints extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "name")
	private String name;// Tên
	
	@Column(name = "code")
	private String code;// Mã 
	
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
	
}
