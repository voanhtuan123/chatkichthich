package com.globits.cms.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;

@Entity
@Table(name = "tbl_cms_page")
@XmlRootElement
public class PageMenuCms extends BaseObject {
	private static final long serialVersionUID = -7509002866849481728L;
	@Column(name = "code")
	private String code;
	@Column(name = "name")
	private String name;
	@Column(name = "link_url", columnDefinition = "TEXT")
	private String linkUrl;

	@Column(name = "type")
	private String type;// Artice bai viet,Category danh muc, Link URL

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private PageMenuCms parent;

	@OneToMany(mappedBy = "parent")
	private Set<PageMenuCms> subPageMenu = new HashSet<>();

	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "website_id")
	private Website website;
	@Column(name = "position_index")
	private Integer positionIndex;// thu tu

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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public PageMenuCms getParent() {
		return parent;
	}

	public void setParent(PageMenuCms parent) {
		this.parent = parent;
	}

	public Set<PageMenuCms> getSubPageMenu() {
		return subPageMenu;
	}

	public void setSubPageMenu(Set<PageMenuCms> subPageMenu) {
		this.subPageMenu = subPageMenu;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPositionIndex() {
		return positionIndex;
	}

	public void setPositionIndex(Integer positionIndex) {
		this.positionIndex = positionIndex;
	}

}
