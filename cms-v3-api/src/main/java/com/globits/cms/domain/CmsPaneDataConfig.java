package com.globits.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "tbl_cms_pane_data_config")
@XmlRootElement
public class CmsPaneDataConfig extends com.globits.core.domain.BaseObject {

	private static final long serialVersionUID = 1L;
	@Column(name="pane_type")
	private Integer paneType;
	
	@ManyToOne
	@JoinColumn(name="pane_id")
	private CmsPane pane;
	@ManyToOne
	@JoinColumn(name="category_id")
	private CmsCategory category;//Lấy dữ liệu từ Category nào
	
	@ManyToOne
	@JoinColumn(name="article_id")
	private CmsArticle article;

	public CmsCategory getCategory() {
		return category;
	}

	public void setCategory(CmsCategory category) {
		this.category = category;
	}

	public CmsArticle getArticle() {
		return article;
	}

	public void setArticle(CmsArticle article) {
		this.article = article;
	}

	public CmsPane getPane() {
		return pane;
	}

	public void setPane(CmsPane pane) {
		this.pane = pane;
	}

	public Integer getPaneType() {
		return paneType;
	}

	public void setPaneType(Integer paneType) {
		this.paneType = paneType;
	}
	
}
