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

/*Phân quyền người dùng với danh mục*/

@Entity
@Table(name = "tbl_cms_user_category_permission")
@XmlRootElement
public class UserCategoryPermissions extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="user_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private User user;		//người dùng
	
	@ManyToOne
	@JoinColumn(name="category_id")
	@NotFound(action=NotFoundAction.IGNORE)
	private CmsCategory category;	//Danh mục 
	
	private boolean isEditorialPermissions;	//là quyền biên tập
	private boolean isApprovalPermissions;	//là quyền duyệt tin
	private boolean isPostPermissions;	//là quyền đăng tin
	private boolean isPublishPermissions;	//là quyền xuất bản
	
	/*@OneToMany(mappedBy = "userCategoryPermission", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CmsPermissions> listPermissions;//Danh sách các quyền được sử dụng ở category
*/
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CmsCategory getCategory() {
		return category;
	}

	public void setCategory(CmsCategory category) {
		this.category = category;
	}

	public boolean isEditorialPermissions() {
		return isEditorialPermissions;
	}

	public void setEditorialPermissions(boolean isEditorialPermissions) {
		this.isEditorialPermissions = isEditorialPermissions;
	}

	public boolean isApprovalPermissions() {
		return isApprovalPermissions;
	}

	public void setApprovalPermissions(boolean isApprovalPermissions) {
		this.isApprovalPermissions = isApprovalPermissions;
	}

	public boolean isPostPermissions() {
		return isPostPermissions;
	}

	public void setPostPermissions(boolean isPostPermissions) {
		this.isPostPermissions = isPostPermissions;
	}

	public boolean isPublishPermissions() {
		return isPublishPermissions;
	}

	public void setPublishPermissions(boolean isPublishPermissions) {
		this.isPublishPermissions = isPublishPermissions;
	}


}
