package com.globits.cms.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;

/*Danh sách các user liên quan đến article theo loại ( người đăng - người tạo - người gửi - người nhận )*/
@Entity
@Table(name = "tbl_cms_user_article")
@XmlRootElement
public class UserArticle extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@ManyToOne
	@JoinColumn(name="article_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private CmsArticle article;	

	@ManyToOne
	@JoinColumn(name="user_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private User user;	

	private int type; //loại ( cấu hình trong Constant )
	
	public CmsArticle getArticle() {
		return article;
	}

	public void setArticle(CmsArticle article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
