import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { ArticleService } from '../article.service';
import { ToastrService } from 'ngx-toastr';
import { ColumnsTable } from 'src/app/_common/component/table/table.component';
import { CmsArticle } from 'src/app/_models/CmsArticle.model';
import { CategoryService } from '../../category/category.service';
import { PagePaging } from 'src/app/_models/PagePaging';
import { CmsCategory } from 'src/app/_models/CmsCategory.model';
import { NgxSpinnerService } from 'ngx-spinner';
import { ConfigInitService } from 'src/app/init/config-init.service';

@Component({
  selector: 'article-create-or-edit',
  templateUrl: './create-or-edit.component.html',
  host: {
    class: "popup-container"
  }
})
export class CreateOrEditComponent implements OnInit {
  form = CmsArticle.getDataForm(this.defaults);
  pageCategory: PagePaging<CmsCategory> = new PagePaging();
  columns?: ColumnsTable[] = [
    { prop: "title", name: "Tiêu đề" },
    { prop: "code", name: "Mã" },
  ];

  constructor(
    @Inject(MAT_DIALOG_DATA) public defaults: CmsArticle,
    private dialogRef: MatDialogRef<CreateOrEditComponent>,
    private service: ArticleService,
    private categoryService: CategoryService,
    private toast: ToastrService,
    private loading: NgxSpinnerService,
    private configInitService: ConfigInitService
  ) { }

  ngOnInit(): void {
    this.categoryService.pagingParentAndChild({ pageIndex: 1, pageSize: 100 }).subscribe({
      next: res => this.pageCategory = res,
    })
  }

  get f() {
    return this.form.controls;
  }

  onSelectCategory(value: CmsCategory[]) {
    this.f.categoryArticle.setValue(value)
  }

  async onSubmit() {
    if (this.form.invalid) {
      return;
    }

    try {
      this.loading.show();
      const article = this.form.value as CmsArticle;

      article.categories = article.categoryArticle.map(item => ({ categoryId: item.id }));

      const res = await this.service.saveArticle(article).toPromise();
      if (!res) {
        throw new Error();
      }

      this.dialogRef.close(res);
      this.toast.success(!this.defaults?.id ? "Thêm mới thành công!" : "Cập nhật thành công!");
    } catch (e) {
      this.toast.error(!this.defaults?.id ? "Thêm mới thất bại!" : "Cập nhật thất bại!")
    } finally {
      this.loading.hide()
    }
  }

  uploadImage(event) {
    const file = event.target.files[0];
    this.service.uploadAttachment(file).subscribe(res => {
      event.target.value = null;
      this.form.get("titleImageUrl").setValue(this.configInitService.apiBaseUrl + "/public/image/" + res.filePath)
    });
  }
}
