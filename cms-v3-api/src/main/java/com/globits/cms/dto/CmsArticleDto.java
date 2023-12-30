package com.globits.cms.dto;

import com.globits.core.dto.PersonDto;
import com.globits.cms.domain.CmsArticle;
import com.globits.cms.domain.CmsCategoryArticle;
import com.globits.security.dto.UserDto;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CmsArticleDto extends BaseObjectDto {
    private Integer status; // Trạng thái
    private String content;
    private String title;// Tiêu đề bài báo
    private String summary;// Tóm tắt nội dung
    private String titleImageUrl;// Đường dẫn đến File ảnh tiêu đề bài báo (nếu có)
    private String createdBy;
    private List<CmsCategoryArticleDto> categories;// Danh sách các chủ đề mà bài báo này thuộc về
    private CmsArticleTypeDto articleType;// Loại bài báo (tin nóng, tin thông thường, ...)
    private Date publishDate; // Ngày đăng tin
    private Integer view; // lượt xem

    private String subtitle;
    private String slug;
    private Boolean notShowHome;// Không hiển thị trang chủ
    private Boolean notShowCategory;// Không hiển thị trang chuyên mục
    private Boolean notShowAds;// Không hiển thị quảng cáo

    private PersonDto blogger; // Blogger
    private String realAuthor; // Tác giả thực (Người viết thực - Chấm nhuận bút)
    private String note; // Ghi chú
    private String noteAvatarImage; // Ghi chú ảnh đại diện
    private CmsCategoryDto primaryCategory; // Danh mục chính

    private Boolean plugInTheFocus;// Cắm tin tiêu điểm
    private Boolean plugInTheItem;// Cắm tiêu điểm mục
    private String tags; // tags
    private String source; // Nguồn
    private Double royalties; // Nhuận bút
    private CmsLocationFocalPointsDto locationFocalPoints; // Vị trí cắm
    private CmsArticleGroupDto articleGroup; // Nhóm tin
    private UserDto sendToUser; // Chuyển tin cho
    private Set<UserArticleDto> userArticles;// Danh sách các user liên quan đến article theo loại ( người đăng - người
                                             // tạo - người gửi - người nhận )
    private WebsiteDto website;
    private String language;
    private String primaryCategoryCode;
    private Boolean isActive;
    private String modifyDate;
    private String typeCustomer; // 1. bài viết thường 2. link: thêm 1 trường url (giống category) 3. Sự kiện:
                                 // thêm ngày bắt đầu và ngày kết thúc
    private String typeCustomerUrl; // url. TypeCustomer
    private Date typeCustomerFromDate; // from date TypeCustomer
    private Date typeCustomerEndDateEvent; // end TypeCustomer
    private String typeCustomerLocation;

    private Integer positionIndex;

    public CmsArticleDto() {
        super();
    }

    public CmsArticleDto(CmsArticle entity) {
        if (entity != null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
            this.setId(entity.getId());
            this.setModifyDate(entity.getModifyDate().toString(formatter));
            this.status = entity.getStatus();
            this.content = entity.getContent();
            this.summary = entity.getSummary();
            this.title = entity.getTitle();
            this.titleImageUrl = entity.getTitleImageUrl();
            this.articleType = new CmsArticleTypeDto(entity.getArticleType());
            this.categories = new ArrayList<CmsCategoryArticleDto>();
            this.publishDate = entity.getPublishDate();
            this.notShowAds = entity.getNotShowAds();
            this.notShowCategory = entity.getNotShowCategory();
            this.notShowHome = entity.getNotShowHome();
            this.createdBy = entity.getCreatedBy();
            this.subtitle = entity.getSubtitle();
            this.slug = entity.getSlug();
            this.note = entity.getNote();
            this.noteAvatarImage = entity.getNoteAvatarImage();
            this.realAuthor = entity.getRealAuthor();
            this.plugInTheFocus = entity.getPlugInTheFocus();
            this.plugInTheItem = entity.getPlugInTheItem();
            this.tags = entity.getTags();
            this.source = entity.getSource();
            this.royalties = entity.getRoyalties();
            this.language = entity.getLanguage();
            this.isActive = entity.getIsActive();
            this.typeCustomer = entity.getTypeCustomer();
            this.typeCustomerUrl = entity.getTypeCustomerUrl();
            this.typeCustomerFromDate = entity.getTypeCustomerFromDate();
            this.typeCustomerEndDateEvent = entity.getTypeCustomerEndDate();
            this.typeCustomerLocation = entity.getTypeCustomerLocation();
            this.positionIndex = entity.getPositionIndex();
            /*
             * this.locationFocalPoints
             * this.sendToUser
             * this.articleGroup
             */
            if (entity.getWebsite() != null) {
                this.website = new WebsiteDto(entity.getWebsite());
            }
            if (entity.getPrimaryCategory() != null) {
                this.primaryCategory = new CmsCategoryDto(entity.getPrimaryCategory());
            }

            if (entity.getArticleGroup() != null) {
                this.articleGroup = new CmsArticleGroupDto(entity.getArticleGroup());
            }

            if (entity.getLocationFocalPoints() != null) {
                this.locationFocalPoints = new CmsLocationFocalPointsDto(entity.getLocationFocalPoints());
            }

            if (entity.getArticleType() != null) {
                this.articleType = new CmsArticleTypeDto(entity.getArticleType());
            }

            if (entity.getBlogger() != null) {
                this.blogger = new PersonDto(entity.getBlogger());
            }

            if (entity.getView() != null && entity.getView() > 0) {
                this.view = entity.getView();
            } else {
                this.view = 0;
            }
            if (entity.getCategories() != null && entity.getCategories().size() > 0) {
                for (CmsCategoryArticle categoryArticle : entity.getCategories()) {
                    if (categoryArticle != null && categoryArticle.getCategory() != null) {
                        CmsCategoryArticleDto categoryArticleDto = new CmsCategoryArticleDto();
                        categoryArticleDto.setCategory(new CmsCategoryDto(categoryArticle.getCategory()));
                        this.categories.add(categoryArticleDto);
                    }
                }
            }
        }
    }

    public CmsArticleDto(CmsArticle entity, boolean simple) {
        if (entity != null) {
            this.setId(entity.getId());
            DateTimeFormatter formatter = DateTimeFormat.forPattern("MMMM, yyyy");
            this.setModifyDate(entity.getModifyDate().toString(formatter));
            this.status = entity.getStatus();
            this.content = entity.getContent();
            this.summary = entity.getSummary();
            this.title = entity.getTitle();
            this.titleImageUrl = entity.getTitleImageUrl();
            this.articleType = new CmsArticleTypeDto(entity.getArticleType());
            this.categories = new ArrayList<CmsCategoryArticleDto>();
            this.publishDate = entity.getPublishDate();
            this.notShowAds = entity.getNotShowAds();
            this.notShowCategory = entity.getNotShowCategory();
            this.notShowHome = entity.getNotShowHome();
            this.createdBy = entity.getCreatedBy();
            this.subtitle = entity.getSubtitle();
            this.slug = entity.getSlug();
            this.note = entity.getNote();
            this.noteAvatarImage = entity.getNoteAvatarImage();
            this.realAuthor = entity.getRealAuthor();
            this.plugInTheFocus = entity.getPlugInTheFocus();
            this.plugInTheItem = entity.getPlugInTheItem();
            this.tags = entity.getTags();
            this.source = entity.getSource();
            this.royalties = entity.getRoyalties();
            this.language = entity.getLanguage();
            this.isActive = entity.getIsActive();
            this.typeCustomer = entity.getTypeCustomer();
            this.typeCustomerUrl = entity.getTypeCustomerUrl();
            this.typeCustomerFromDate = entity.getTypeCustomerFromDate();
            this.typeCustomerEndDateEvent = entity.getTypeCustomerEndDate();
            this.typeCustomerLocation = entity.getTypeCustomerLocation();
            this.positionIndex = entity.getPositionIndex();
            if (entity.getWebsite() != null) {
                this.website = new WebsiteDto(entity.getWebsite());
            }
            if (entity.getView() != null && entity.getView() > 0) {
                this.view = entity.getView();
            } else {
                this.view = 0;
            }
            if (entity.getPrimaryCategory() != null) {
                this.primaryCategory = new CmsCategoryDto(entity.getPrimaryCategory(), false, true);
            }
        }
    }

    public String getTypeCustomer() {
        return typeCustomer;
    }

    public void setTypeCustomer(String typeCustomer) {
        this.typeCustomer = typeCustomer;
    }

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

    public Date getTypeCustomerEndDateEvent() {
        return typeCustomerEndDateEvent;
    }

    public void setTypeCustomerEndDateEvent(Date typeCustomerEndDateEvent) {
        this.typeCustomerEndDateEvent = typeCustomerEndDateEvent;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    public List<CmsCategoryArticleDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CmsCategoryArticleDto> categories) {
        this.categories = categories;
    }

    public CmsArticleTypeDto getArticleType() {
        return articleType;
    }

    public void setArticleType(CmsArticleTypeDto articleType) {
        this.articleType = articleType;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public PersonDto getBlogger() {
        return blogger;
    }

    public void setBlogger(PersonDto blogger) {
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

    public String getNoteAvatarImage() {
        return noteAvatarImage;
    }

    public void setNoteAvatarImage(String noteAvatarImage) {
        this.noteAvatarImage = noteAvatarImage;
    }

    public CmsCategoryDto getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(CmsCategoryDto primaryCategory) {
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

    public Double getRoyalties() {
        return royalties;
    }

    public void setRoyalties(Double royalties) {
        this.royalties = royalties;
    }

    public CmsLocationFocalPointsDto getLocationFocalPoints() {
        return locationFocalPoints;
    }

    public void setLocationFocalPoints(CmsLocationFocalPointsDto locationFocalPoints) {
        this.locationFocalPoints = locationFocalPoints;
    }

    public CmsArticleGroupDto getArticleGroup() {
        return articleGroup;
    }

    public void setArticleGroup(CmsArticleGroupDto articleGroup) {
        this.articleGroup = articleGroup;
    }

    public UserDto getSendToUser() {
        return sendToUser;
    }

    public void setSendToUser(UserDto sendToUser) {
        this.sendToUser = sendToUser;
    }

    public Set<UserArticleDto> getUserArticles() {
        return userArticles;
    }

    public void setUserArticles(Set<UserArticleDto> userArticles) {
        this.userArticles = userArticles;
    }

    public String getTypeCustomerLocation() {
        return typeCustomerLocation;
    }

    public void setTypeCustomerLocation(String typeCustomerLocation) {
        this.typeCustomerLocation = typeCustomerLocation;
    }

    public WebsiteDto getWebsite() {
        return website;
    }

    public void setWebsite(WebsiteDto website) {
        this.website = website;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPrimaryCategoryCode() {
        return primaryCategoryCode;
    }

    public void setPrimaryCategoryCode(String primaryCategoryCode) {
        this.primaryCategoryCode = primaryCategoryCode;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getPositionIndex() {
        return positionIndex;
    }

    public void setPositionIndex(Integer positionIndex) {
        this.positionIndex = positionIndex;
    }

}
