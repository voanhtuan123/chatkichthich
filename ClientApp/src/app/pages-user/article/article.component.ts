import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleView, CmsArticle } from 'src/app/_models/CmsArticle.model';
import { PageUserService } from '../pages-user.service';
import { CategoryView } from 'src/app/_models/CmsCategory.model';
import { PagePaging } from 'src/app/_models/PagePaging';
import { CmsSearch } from 'src/app/_models/SearchObject.model';
import { HomePage } from 'src/app/_models/HomePage.model';
import { TypeMenu } from 'src/app/_common/constant/local-constant';

@Component({
    selector: 'user-article',
    templateUrl: './article.component.html',
    host: {
        class: "ngx-datatable material overflow-visible",
        style: "box-shadow: none"
    }
})
export class ArticleComponent implements OnInit {
    homePage?: HomePage = new HomePage();
    categoryView?: CategoryView = null;

    articleView?: CmsArticle = null;
    pageArticle: PagePaging<ArticleView> = new PagePaging();
    searchArticle: CmsSearch = new CmsSearch();

    typeMenu = TypeMenu;

    constructor(private service: PageUserService, private route: ActivatedRoute) { }

    ngOnInit(): void {
        this.route.params.subscribe(this.getDataView);
    }

    get link() {
        return "/" + this.categoryView.slug + '/';
    }

    getDataView = async ({ category, article }) => {
        this.homePage = new HomePage();
        this.categoryView = null;

        this.articleView = null;
        this.pageArticle = new PagePaging();
        this.searchArticle = new CmsSearch();

        try {
            if (category) {
                const res = await this.service.getCategoryBySlug(category).toPromise();
                if (res && res.length > 0) {
                    this.categoryView = res[0];
                } else {
                    article = category;
                }
            }

            if (article) {
                const res = await this.service.getArticleBySlug(article).toPromise();
                this.articleView = new CmsArticle(res);

                if (!this.categoryView && this.articleView?.primaryCategory?.slug) {
                    const res = await this.service.getCategoryBySlug(this.articleView.primaryCategory.slug).toPromise();
                    this.categoryView = res?.[0] || null;
                    category = this.articleView.primaryCategory.slug;
                }
            }

            if (this.categoryView) {
                this.searchArticle.slug = category;
                this.getPageArticle();
                this.service.getHomePage().forEach(res => this.homePage = res);
            }
        } catch (error) {
            console.error(error);
        }
    }

    getPageArticle() {
        this.service.getPageArticleBySlug(this.searchArticle).subscribe({ next: res => this.pageArticle = res });
    }

    onChangePageIndex(event: { page: number }) {
        this.searchArticle.pageIndex = event.page;
        this.getPageArticle();
    }
}