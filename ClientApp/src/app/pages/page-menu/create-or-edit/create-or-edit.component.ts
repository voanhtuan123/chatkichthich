import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { PageMenuService } from '../page-menu.service';
import { PageMenuCms } from 'src/app/_models/PageMenuCms.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { LIST_TYPE_PAGE_MENU, TypeMenu } from 'src/app/_common/constant/local-constant';
import { CategoryService } from '../../category/category.service';
import { CmsCategory } from 'src/app/_models/CmsCategory.model';
import { CmsArticle } from 'src/app/_models/CmsArticle.model';
import { ArticleService } from '../../article/article.service';

@Component({
  selector: 'create-or-edit',
  templateUrl: './create-or-edit.component.html',
  host: {
    class: "popup-container"
  }
})
export class CreateOrEditComponent implements OnInit {
  form = PageMenuCms.getDataForm(this.defaults);

  listType = LIST_TYPE_PAGE_MENU;
  listCategory: CmsCategory[] = [];
  listArticle: CmsArticle[] = [];

  constructor(
    @Inject(MAT_DIALOG_DATA) public defaults: PageMenuCms,
    private dialogRef: MatDialogRef<CreateOrEditComponent>,
    private service: PageMenuService,
    private toast: ToastrService,
    private loading: NgxSpinnerService,
    private categoryService: CategoryService,
    private articleService: ArticleService
  ) { }

  ngOnInit(): void {
    this.categoryService.pagingParentAndChild({ pageIndex: 1, pageSize: 100 }).subscribe(res => this.listCategory = res.content)
    this.articleService.pagingArticle({ pageIndex: 1, pageSize: 100 }).subscribe(res => this.listArticle = res.content)
  }

  get f() {
    return this.form.controls;
  }

  get checkTypeMenuUrl() {
    return this.f.type.value === TypeMenu.URL;
  }

  get checkTypeMenuArticles() {
    return this.f.type.value === TypeMenu.ARTICLE;
  }

  get checkTypeMenuCategory() {
    return this.f.type.value === TypeMenu.CATEGORY;
  }

  async onSubmit() {
    if (this.form.invalid) {
      return;
    }

    try {
      this.loading.show();
      const pageMenu = this.form.getRawValue() as PageMenuCms;
      const res = await this.service.savePageMenu(pageMenu).toPromise();

      if (!res) {
        throw new Error();
      }

      this.dialogRef.close(res);
      this.toast.success(!this.defaults?.id ? "Thêm mới thành công!" : "Cập nhật thành công!");
    } catch (e) {
      this.toast.error(!this.defaults?.id ? "Thêm mới thất bại!" : "Cập nhật thất bại!");
    } finally {
      this.loading.hide();
    }
  }
}
