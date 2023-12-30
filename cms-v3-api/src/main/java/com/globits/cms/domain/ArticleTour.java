package com.globits.cms.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
@Entity
@Table(name = "tbl_article_tour")
@XmlRootElement
public class ArticleTour extends BaseObject {
	@ManyToOne
	@JoinColumn(name="article_id")
	private CmsArticle article;
	@ManyToOne
	@JoinColumn(name="tour_id")
	private Tour tour;
	public CmsArticle getArticle() {
		return article;
	}
	public void setArticle(CmsArticle article) {
		this.article = article;
	}
	public Tour getTour() {
		return tour;
	}
	public void setTour(Tour tour) {
		this.tour = tour;
	}
}
