package com.globits.cms.dto;

import com.globits.cms.domain.UserArticle;
import com.globits.security.dto.UserDto;

public class UserArticleDto extends BaseObjectDto {
	private CmsArticleDto article;	
	private UserDto user;	
	private int type; //loại ( cấu hình trong Constant )
	public CmsArticleDto getArticle() {
		return article;
	}
	public void setArticle(CmsArticleDto article) {
		this.article = article;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public UserArticleDto() {
		super();
	}

	public UserArticleDto(UserArticle entity) {
		super();
		if (entity != null) {

			this.setId(entity.getId());
			this.type = entity.getType();
			
			if (entity.getArticle() != null) {
				this.article = new CmsArticleDto(entity.getArticle(), false);
			}
			if (entity.getUser() != null) {
				this.user = new UserDto(entity.getUser());
			}
		}
	}

}
