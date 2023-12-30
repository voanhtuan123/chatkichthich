import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Breadcrumb } from 'src/app/_common/component/breadcrumbs/breadcrumbs.component';
import { ColumnsTable } from 'src/app/_common/component/table/table.component';
import { CmsArticle } from 'src/app/_models/CmsArticle.model';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { formatDate } from 'src/app/_common/constant/local-function';
import { NgxSpinnerService } from 'ngx-spinner';
import { ArticleService } from '../article/article.service';

@Component({
    selector: 'article-home',
    templateUrl: './article-home.component.html',
})
export class ArticleHomeComponent implements OnInit {
    breadcrumbs?: Breadcrumb[] = [{ text: "Bài viết trang chủ", link: null }];
    searchForm: FormGroup = null;
    searchObject: SearchObject = new SearchObject({ showOnHomePage: true });
    pageArticle: PagePaging<CmsArticle> = new PagePaging();
    columns?: ColumnsTable<CmsArticle>[] = [];

    constructor(
        private service: ArticleService,
        private fb: FormBuilder,
        private loading: NgxSpinnerService
    ) { }

    ngOnInit(): void {
        this.searchForm = this.fb.group({ text: null });
        this.columns = [
            { prop: "title", name: "Tiêu đề" },
            { prop: "summary", name: "Tóm tắt" },
            { prop: "publishDate", name: "Ngày đăng", render: (value) => formatDate(value.publishDate) },
        ]
        this.pagingArticle();
    }

    async pagingArticle() {
        try {
            this.loading.show();
            const res = await this.service.pagingArticle(this.searchObject).toPromise();
            this.pageArticle = res
        } catch (e) {
            console.log(e);
        } finally {
            this.loading.hide()
        }
    }

    submitSearch() {
        this.searchObject.checkSearchObject(this.searchForm.getRawValue());
        this.pagingArticle();
    }

    setPage(pageInfo) {
        if (pageInfo.offset >= 0) {
            this.searchObject.checkSearchObject({ pageIndex: pageInfo.offset + 1 });
            this.pagingArticle();
        }
    }
}
