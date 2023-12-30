import { ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Breadcrumb } from 'src/app/_common/component/breadcrumbs/breadcrumbs.component';
import { CmsCategory } from 'src/app/_models/CmsCategory.model';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { CategoryService } from './category.service';
import { CreateOrEditComponent } from './create-or-edit/create-or-edit.component';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogService } from 'src/app/_common/component/dialog/confirm-dialog/confirm-dialog.service';
import { ColumnsTable } from 'src/app/_common/component/table/table.component';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'category',
  templateUrl: './category.component.html',
})
export class CategoryComponent implements OnInit {
  id: string = null;
  breadcrumbs?: Breadcrumb[] = [];
  searchForm: FormGroup = null;
  searchObject: SearchObject = new SearchObject();
  pageCategory: PagePaging<CmsCategory> = new PagePaging();

  categoryParent: CmsCategory = null;

  columns: ColumnsTable<CmsCategory>[] = [
    { prop: "title", name: "Tiêu đề" },
    { prop: "code", name: "Mã" },
    { prop: "code", nameItemHTML: "button", name: "Thao tác" },
  ];

  constructor(
    private service: CategoryService,
    private toast: ToastrService,
    private dialog: MatDialog,
    private confirmDialog: ConfirmDialogService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private loading: NgxSpinnerService,
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.id = id;
      let breadcrumbs = [{ text: "Danh mục", link: "/admin/category" }]

      if (this.id) {
        this.service.getCategory(this.id).subscribe({
          next: res => {
            this.categoryParent = res;
            const listParent = this.getParentCategory(res).reverse();
            breadcrumbs.push(...listParent)
          }
        })
      }

      this.searchForm = this.fb.group({ text: null });
      this.breadcrumbs = breadcrumbs
      this.submitSearch();
    });
  }

  getParentCategory(category: CmsCategory, listResult = []) {
    if (category) {
      listResult.push({ text: category.title, link: '/admin/category/' + category.id });
      this.getParentCategory(category.parent, listResult)
    }

    return listResult;
  }

  async pagingCategory() {
    this.loading.show();

    try {
      const res = await this.service.pagingCategory({ ...this.searchObject, parentId: this.id }).toPromise();
      this.pageCategory = res;
    } catch (e) {
      console.error(e)
    } finally {
      this.loading.hide()
    }
  }

  submitSearch() {
    this.searchObject.checkSearchObject(this.searchForm.getRawValue());
    this.pagingCategory();
  }

  setPage(pageInfo) {
    if (pageInfo.offset >= 0) {
      this.searchObject.checkSearchObject({ pageIndex: pageInfo.offset + 1 });
      this.pagingCategory();
    }
  }

  async openFormCreateOrEditCategory(categoryId?: string) {
    let data = new CmsCategory({ parent: this.categoryParent });
    if (categoryId) {
      data = await this.service.getCategory(categoryId).toPromise();
    }
    this.dialog.open(CreateOrEditComponent, { width: "400px", disableClose: true, data }).afterClosed().subscribe(res => Boolean(res) && this.pagingCategory())
  }

  deleteCategory(id: string) {
    this.service.deleteCategory(id).subscribe({
      next: (res) => {
        if (res) {
          this.toast.success("Xoá bản ghi thành công", "Thông báo");
          this.pagingCategory();
        }
      },
      error: (err) => this.toast.warning("Xoá bản ghi thất bại", "Thông báo"),
    });
  }

  handleDeleteCategory(id: string) {
    this.confirmDialog.openConfirmDialog({
      title: "Bạn có chắc muốn xóa bản ghi này?",
      text: "Bạn sẽ không thể khôi phục bản ghi này!",
      onYesClick: () => this.deleteCategory(id),
    });
  }
}