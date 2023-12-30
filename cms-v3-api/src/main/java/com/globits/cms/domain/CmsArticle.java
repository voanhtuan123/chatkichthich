package com.globits.cms.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.globits.core.domain.BaseObject;
import com.globits.core.domain.Person;

@Entity
@Table(name = "tbl_cms_article")
@XmlRootElement
public class CmsArticle extends BaseObject {
	private static final long serialVersionUID = 7697226998038807928L;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	@Column(name = "title")
	private String title;// Tiêu đề bài báo

	@Column(name = "summary")
	private String summary;// Tóm tắt nội dung

	@Column(name = "title_image_url")
	private String titleImageUrl;// Đường dẫn đến File ảnh tiêu đề bài báo (nếu có)

	@Column(name = "publish_date")
	private Date publishDate; // Ngày đăng tin

	@Column(name = "view_detail")
	private Integer view; // lượt xem

	@OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CmsCategoryArticle> categories = new HashSet<>();// Danh sách các chủ đề mà bài báo này thuộc về

	@ManyToOne
	@JoinColumn(name = "article_type_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private CmsArticleType articleType;// Loại bài viết (tin nóng, tin thông thường, ...)

	@Column(name = "subtitle")
	private String subtitle;// Tiêu đề phụ

	@Column(name = "slug")
	private String slug;
	@Column(name = "language")
	private String language;
	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private CmsCategory primaryCategory; // Danh mục chính

	@Column(name = "note_avatar_image")
	private String noteAvatarImage; // Ghi chú ảnh đại diện

	@ManyToOne
	@JoinColumn(name = "blogger_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Person blogger; // Blogger

	@Column(name = "real_author")
	private String realAuthor; // Tác giả thực (Người viết thực - Chấm nhuận bút)

	@Column(name = "plug_in_the_focus")
	private Boolean plugInTheFocus;// Cắm tin tiêu điểm

	@Column(name = "plug_in_the_item")
	private Boolean plugInTheItem;// Cắm tiêu điểm mục

	@ManyToOne
	@JoinColumn(name = "location_focal_points_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private CmsLocationFocalPoints locationFocalPoints; // Vị trí cắm

	@Column(name = "not_show_home")
	private Boolean notShowHome;// Không hiển thị trang chủ

	@Column(name = "not_show_category")
	private Boolean notShowCategory;// Không hiển thị trang chuyên mục

	@Column(name = "not_show_ads")
	private Boolean notShowAds;// Không hiển thị quảng cáo

	@Column(name = "note")
	private String note; // Ghi chú

	@Column(name = "tags")
	private String tags; // tags

	@Column(name = "source")
	private String source; // Nguồn

	@Column(name = "type_customer")
	private String typeCustomer; // 1. bài viết thường 2. link: thêm 1 trường url (giống category) 3. Sự kiện:
									// thêm ngày bắt đầu và ngày kết thúc

	@Column(name = "type_customer_url")
	private String typeCustomerUrl; // url. TypeCustomer

	@Column(name = "type_customer_from_date")
	private Date typeCustomerFromDate; // from date TypeCustomer

	@Column(name = "type_customer_end_date")
	private Date typeCustomerEndDate; // end TypeCustomer

	@Column(name = "type_customer_location")
	private String typeCustomerLocation; // Location

	@Column(name = "position_index")
	private Integer positionIndex;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@ManyToOne
	@JoinColumn(name = "article_group_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private CmsArticleGroup articleGroup; // Nhóm tin
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "map_object_id")
	private CmsMapObject mapObject;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="send_to_user_id")
	 * 
	 * @NotFound(action=NotFoundAction.IGNORE)
	 * private User sendToUser; //Chuyển tin cho
	 */
	@OneToMany(mappedBy = "article", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserArticle> userArticles;// Danh sách các user liên quan đến article theo loại ( người đăng - người tạo
											// - người gửi - người nhận )

	@Column(name = "royalties")
	private Double royalties; // Nhuận bút

	@Column(name = "status")
	private Integer status; // Trạng thái

	@ManyToOne
	@JoinColumn(name = "website_id")
	private Website website;
	@Column(name = "is_active")
	private Boolean isActive;

	public String getTypeCustomerUrl() {
		return typeCustomerUrl;
	}

	public void setTypeCustomerUrl(String typeCustomerUrl) {
		this.typeCustomerUrl = typeCustomerUrl;
	}

	public Date getTypeCustomerFromDate() {
		return typeCustomerFromDate;
	}

	public void setTypeCustomerFromDate(Date typeCustomerFromDate) {
		this.typeCustomerFromDate = typeCustomerFromDate;
	}

	public Date getTypeCustomerEndDate() {
		return typeCustomerEndDate;
	}

	public void setTypeCustomerEndDate(Date typeCustomerEndDate) {
		this.typeCustomerEndDate = typeCustomerEndDate;
	}

	public String getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(String typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	public Set<UserArticle> getUserArticles() {
		return userArticles;
	}

	public void setUserArticles(Set<UserArticle> userArticles) {
		this.userArticles = userArticles;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getNotShowHome() {
		return notShowHome;
	}

	public void setNotShowHome(Boolean notShowHome) {
		this.notShowHome = notShowHome;
	}

	public Boolean getNotShowCategory() {
		return notShowCategory;
	}

	public void setNotShowCategory(Boolean notShowCategory) {
		this.notShowCategory = notShowCategory;
	}

	public Boolean getNotShowAds() {
		return notShowAds;
	}

	public void setNotShowAds(Boolean notShowAds) {
		this.notShowAds = notShowAds;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitleImageUrl() {
		return titleImageUrl;
	}

	public void setTitleImageUrl(String titleImageUrl) {
		this.titleImageUrl = titleImageUrl;
	}

	public Set<CmsCategoryArticle> getCategories() {
		return categories;
	}

	public void setCategories(Set<CmsCategoryArticle> categories) {
		this.categories = categories;
	}

	public CmsArticleType getArticleType() {
		return articleType;
	}

	public void setArticleType(CmsArticleType articleType) {
		this.articleType = articleType;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getView() {
		return view;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public String getNoteAvatarImage() {
		return noteAvatarImage;
	}

	public void setNoteAvatarImage(String noteAvatarImage) {
		this.noteAvatarImage = noteAvatarImage;
	}

	public Person getBlogger() {
		return blogger;
	}

	public void setBlogger(Person blogger) {
		this.blogger = blogger;
	}

	public String getRealAuthor() {
		return realAuthor;
	}

	public void setRealAuthor(String realAuthor) {
		this.realAuthor = realAuthor;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public CmsCategory getPrimaryCategory() {
		return primaryCategory;
	}

	public void setPrimaryCategory(CmsCategory primaryCategory) {
		this.primaryCategory = primaryCategory;
	}

	public Boolean getPlugInTheFocus() {
		return plugInTheFocus;
	}

	public void setPlugInTheFocus(Boolean plugInTheFocus) {
		this.plugInTheFocus = plugInTheFocus;
	}

	public Boolean getPlugInTheItem() {
		return plugInTheItem;
	}

	public void setPlugInTheItem(Boolean plugInTheItem) {
		this.plugInTheItem = plugInTheItem;
	}

	public CmsLocationFocalPoints getLocationFocalPoints() {
		return locationFocalPoints;
	}

	public void setLocationFocalPoints(CmsLocationFocalPoints locationFocalPoints) {
		this.locationFocalPoints = locationFocalPoints;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public CmsArticleGroup getArticleGroup() {
		return articleGroup;
	}

	public void setArticleGroup(CmsArticleGroup articleGroup) {
		this.articleGroup = articleGroup;
	}

	/*
	 * public User getSendToUser() {
	 * return sendToUser;
	 * }
	 * public void setSendToUser(User sendToUser) {
	 * this.sendToUser = sendToUser;
	 * }
	 */
	public Double getRoyalties() {
		return royalties;
	}

	public void setRoyalties(Double royalties) {
		this.royalties = royalties;
	}

	public CmsMapObject getMapObject() {
		return mapObject;
	}

	public void setMapObject(CmsMapObject mapObject) {
		this.mapObject = mapObject;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getTypeCustomerLocation() {
		return typeCustomerLocation;
	}

	public void setTypeCustomerLocation(String typeCustomerLocation) {
		this.typeCustomerLocation = typeCustomerLocation;
	}

	public Integer getPositionIndex() {
		return positionIndex;
	}

	public void setPositionIndex(Integer positionIndex) {
		this.positionIndex = positionIndex;
	}

}
