import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Breadcrumb } from 'src/app/_common/component/breadcrumbs/breadcrumbs.component';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { PageMenuService } from './page-menu.service';
import { CreateOrEditComponent } from './create-or-edit/create-or-edit.component';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogService } from 'src/app/_common/component/dialog/confirm-dialog/confirm-dialog.service';
import { ColumnsTable } from 'src/app/_common/component/table/table.component';
import { PageMenuCms } from 'src/app/_models/PageMenuCms.model';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'page-menu',
  templateUrl: './page-menu.component.html',
})
export class PageMenuComponent implements OnInit {
  id: string = null;
  breadcrumbs?: Breadcrumb[] = [];
  searchForm: FormGroup = null;
  searchObject: SearchObject = new SearchObject();
  pagePageMenu: PagePaging<PageMenuCms> = new PagePaging();

  pageMenuParent: PageMenuCms = null;

  columns?: ColumnsTable<PageMenuCms>[] = [
    { prop: "name", name: "Tên" },
    { prop: "code", name: "Mã" },
    { prop: "positionIndex", name: "Thứ tự hiển thị" },
    { prop: "action", nameItemHTML: "button", name: "Thao tác" },
  ];

  constructor(
    private service: PageMenuService,
    private toast: ToastrService,
    private dialog: MatDialog,
    private confirmDialog: ConfirmDialogService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private loading: NgxSpinnerService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe(({ id }) => {
      this.id = id;
      let breadcrumbs = [{ text: "Menu", link: "/admin/page-menu" }];

      if (this.id) {
        this.service.getPageMenu(this.id).subscribe({
          next: res => {
            this.pageMenuParent = res;
            const listParent = this.getParentPageMenu(res).reverse();
            breadcrumbs.push(...listParent);
          }
        })
      }

      this.breadcrumbs = breadcrumbs
      this.searchForm = this.fb.group({ text: null });
      this.submitSearch();
    });
  }

  getParentPageMenu(category: PageMenuCms, listResult = []) {
    if (category) {
      listResult.push({ text: category.name, link: '/admin/category/' + category.id });
      this.getParentPageMenu(category.parent, listResult)
    }

    return listResult.reverse();
  }

  async pagingPageMenu() {
    try {
      this.loading.show();
      const res = await this.service.pagingPageMenu({ ...this.searchObject, parentId: this.id }).toPromise();
      this.pagePageMenu = res;
    } catch (e) {
      console.error(e);
    } finally {
      this.loading.hide();
    }
  }

  submitSearch() {
    this.searchObject.checkSearchObject(this.searchForm.getRawValue());
    this.pagingPageMenu();
  }

  setPage(pageInfo) {
    if (pageInfo.offset >= 0) {
      this.searchObject.checkSearchObject({ pageIndex: pageInfo.offset + 1 });
      this.pagingPageMenu();
    }
  }

  async openFormCreateOrEditPageMenu(pageMenuId?: string) {
    let data = new PageMenuCms({ parent: this.pageMenuParent });
    if (pageMenuId) {
      data = await this.service.getPageMenu(pageMenuId).toPromise();
    }
    this.dialog.open(CreateOrEditComponent, { width: "400px", disableClose: true, data }).afterClosed().subscribe(res => Boolean(res) && this.pagingPageMenu())
  }

  deletePageMenu(id: string) {
    this.service.deletePageMenu(id).subscribe({
      next: (res) => {
        if (res) {
          this.toast.success("Xoá bản ghi thành công", "Thông báo");
          this.pagingPageMenu();
        }
      },
      error: (err) => this.toast.warning("Xoá bản ghi thất bại", "Thông báo"),
    });
  }

  handleDeletePageMenu(id: string) {
    this.confirmDialog.openConfirmDialog({
      title: "Bạn có chắc muốn xóa bản ghi này?",
      text: "Bạn sẽ không thể khôi phục bản ghi này!",
      onYesClick: () => this.deletePageMenu(id),
    });
  }
}