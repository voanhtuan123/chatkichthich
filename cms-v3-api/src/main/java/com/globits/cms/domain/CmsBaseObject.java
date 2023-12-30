package com.globits.cms.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.Organization;
@MappedSuperclass
public class CmsBaseObject extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name="org_id")
	private Organization org;

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	
}
