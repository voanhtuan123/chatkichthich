package com.globits.cms.dto.home;

import java.util.ArrayList;
import java.util.List;

public class HomePageDto {
    private List<ArticleViewDto> articleSlider = new ArrayList<>();// slider
    private List<ArticleViewDto> listArticle = new ArrayList<>();// danh sach bai viet don le
    private List<CategoryViewDto> listCategory = new ArrayList<>();// danh sach danh muc + bài viet

    public HomePageDto() {

    }

    public List<ArticleViewDto> getArticleSlider() {
        return articleSlider;
    }

    public void setArticleSlider(List<ArticleViewDto> articleSlider) {
        this.articleSlider = articleSlider;
    }

    public List<ArticleViewDto> getListArticle() {
        return listArticle;
    }

    public void setListArticle(List<ArticleViewDto> listArticle) {
        this.listArticle = listArticle;
    }

    public List<CategoryViewDto> getListCategory() {
        return listCategory;
    }

    public void setListCategory(List<CategoryViewDto> listCategory) {
        this.listCategory = listCategory;
    }
}
