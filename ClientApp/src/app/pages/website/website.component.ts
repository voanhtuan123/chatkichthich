import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Breadcrumb } from 'src/app/_common/component/breadcrumbs/breadcrumbs.component';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { WebsiteService } from './website.service';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { ConfirmDialogService } from 'src/app/_common/component/dialog/confirm-dialog/confirm-dialog.service';
import { ColumnsTable } from 'src/app/_common/component/table/table.component';
import { ActivatedRoute, Router } from '@angular/router';
import { Website } from 'src/app/_models/Website.model';
import { CreateOrEditComponent } from './create-or-edit/create-or-edit.component';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'website',
  templateUrl: './website.component.html',
})
export class WebsiteComponent implements OnInit {
  id: string = null;
  breadcrumbs?: Breadcrumb[] = [{ text: "Website", link: null }];
  columns?: ColumnsTable<Website>[] = [];
  searchForm: FormGroup = null;
  searchObject: SearchObject = new SearchObject();
  pageWebsite: PagePaging<Website> = new PagePaging();

  websiteParent: Website = null;

  constructor(
    private service: WebsiteService,
    private toast: ToastrService,
    private dialog: MatDialog,
    private confirmDialog: ConfirmDialogService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loading : NgxSpinnerService
    ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params["id"];

    if (this.id) {
      this.service.getWebsite(this.id).subscribe({
        next: res => this.websiteParent = res
      })
    }

    this.searchForm = this.fb.group({ text: null });
    this.columns = [
      { prop: "name", name: "Tên" },
      { prop: "code", name: "Mã" },
      { prop: "domain", name: "Tên miền" },
      { prop: "action", nameItemHTML: "button", name: "Thao tác" },
    ]
    this.pagingWebsite();
  }

  pagingWebsite() {
    this.loading.show();
    this.service.pagingWebsite({ ...this.searchObject, parentId: this.id }).subscribe({
      next: res => this.pageWebsite = res,
      complete: () => this.loading.hide()
    })
  }

  submitSearch() {
    this.searchObject.checkSearchObject(this.searchForm.getRawValue());
    this.pagingWebsite();
  }

  setPage(pageInfo) {
    if (pageInfo.offset >= 0) {
      this.searchObject.checkSearchObject({ pageIndex: pageInfo.offset + 1 });
      this.pagingWebsite();
    }
  }

  async openFormCreateOrEditWebsite(pageMenuId?: string) {
    let data = new Website({ parent: this.websiteParent });
    if (pageMenuId) {
      data = await this.service.getWebsite(pageMenuId).toPromise();
    }
    this.dialog.open(CreateOrEditComponent, { width: "500px", disableClose: true, data }).afterClosed().subscribe(res => Boolean(res) && this.pagingWebsite())
  }

  deleteWebsite(id: string) {
    this.service.deleteWebsite(id).subscribe({
      next: (res) => {
        if (res) {
          this.toast.success("Xoá bản ghi thành công", "Thông báo");
          this.pagingWebsite();
        }
      },
      error: (err) => this.toast.warning("Xoá bản ghi thất bại", "Thông báo"),
    });
  }

  handleDeleteWebsite(id: string) {
    this.confirmDialog.openConfirmDialog({
      title: "Bạn có chắc muốn xóa bản ghi này?",
      text: "Bạn sẽ không thể khôi phục bản ghi này!",
      onYesClick: () => this.deleteWebsite(id),
    });
  }

  toRouter(parentId: string) {
    if (parentId) {
      this.router.navigateByUrl("/admin/website", { skipLocationChange: true }).then(() => this.router.navigate(["/admin/website" + (parentId ? ("/" + parentId) : "")]));
    } else {
      this.router.navigate(["/admin/website"]);
    }
  }
}
