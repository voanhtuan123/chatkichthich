import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Breadcrumb } from 'src/app/_common/component/breadcrumbs/breadcrumbs.component';
import { ConfirmDialogService } from 'src/app/_common/component/dialog/confirm-dialog/confirm-dialog.service';
import { ColumnsTable } from 'src/app/_common/component/table/table.component';
import { CmsArticle } from 'src/app/_models/CmsArticle.model';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { ArticleService } from './article.service';
import { CreateOrEditComponent } from './create-or-edit/create-or-edit.component';
import { formatDate } from 'src/app/_common/constant/local-function';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'article',
  templateUrl: './article.component.html',
})
export class ArticleComponent implements OnInit {
  breadcrumbs?: Breadcrumb[] = [{ text: "Bài viết", link: null }];
  searchForm: FormGroup = null;
  searchObject: SearchObject = new SearchObject();
  pageArticle: PagePaging<CmsArticle> = new PagePaging();

  columns?: ColumnsTable<CmsArticle>[] =  [
    { prop: "title", name: "Tiêu đề" },
    { prop: "summary", name: "Tóm tắt" },
    { prop: "publishDate", name: "Ngày đăng", render: (value) => formatDate(value.publishDate) },
    { prop: "action", nameItemHTML: "button", name: "Thao tác" },
  ];

  constructor(
    private service: ArticleService,
    private toast: ToastrService,
    private dialog: MatDialog,
    private confirmDialog: ConfirmDialogService,
    private fb: FormBuilder,
    private loading: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.searchForm = this.fb.group({ text: null });
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

  async openFormCreateOrEditArticle(categoryId?: string) {
    let data = new CmsArticle();
    if (categoryId) {
      data = await this.service.getArticle(categoryId).toPromise();
    }
    this.dialog.open(CreateOrEditComponent, { width: "auto", disableClose: true, data, minWidth: "55svw" }).afterClosed().subscribe(res => Boolean(res) && this.pagingArticle())
  }

  deleteArticle(id: string) {
    this.service.deleteArticle(id).subscribe({
      next: (res) => {
        if (res) {
          this.toast.success("Xoá bản ghi thành công", "Thông báo");
          this.pagingArticle();
        }
      },
      error: (err) => this.toast.warning("Xoá bản ghi thất bại", "Thông báo"),
    });
  }

  handleDeleteArticle(id: string) {
    this.confirmDialog.openConfirmDialog({
      title: "Bạn có chắc muốn xóa bản ghi này?",
      text: "Bạn sẽ không thể khôi phục bản ghi này!",
      onYesClick: () => this.deleteArticle(id),
    });
  }
}
