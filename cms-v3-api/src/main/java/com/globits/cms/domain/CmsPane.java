package com.globits.cms.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_cms_pane")
@XmlRootElement
public class CmsPane extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4415793881276438904L;
	private String code;
	private String name;
	
	@OneToMany(mappedBy = "pane")
	private Set<CmsPaneDataConfig> listData;
	
	@ManyToOne
	@JoinColumn(name="page_id")
	private CmsPage page;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<CmsPaneDataConfig> getListData() {
		return listData;
	}

	public void setListData(Set<CmsPaneDataConfig> listData) {
		this.listData = listData;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CmsPage getPage() {
		return page;
	}

	public void setPage(CmsPage page) {
		this.page = page;
	}
}
