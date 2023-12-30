import { Component, Inject } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { Website } from 'src/app/_models/Website.model';
import { WebsiteService } from '../website.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'create-or-edit',
  templateUrl: './create-or-edit.component.html',
  host: {
    class: "popup-container"
  }
})
export class CreateOrEditComponent {
  form: FormGroup = Website.getDataForm(this.defaults);

  constructor(
    @Inject(MAT_DIALOG_DATA) public defaults: Website,
    private dialogRef: MatDialogRef<CreateOrEditComponent>,
    private service: WebsiteService,
    private toast: ToastrService,
    private loading: NgxSpinnerService
  ) { }

  get f() {
    return this.form.controls;
  }

  onSubmit() {
    if (this.form.invalid) {
      return;
    }
    this.loading.show();
    const website = this.form.value as Website;
    this.service.checkDomain(website.domain).subscribe({
      next: (response) => {
        if (response) {
          this.toast.error("Tên miền đã tồn tại!", "Thông báo");
        } else {
          this.service.saveWebsite(website).subscribe({
            next: (response) => {
              this.dialogRef.close(response);
              this.toast.success(!this.defaults?.id ? "Thêm mới thành công!" : "Cập nhật thành công!", "Thông báo");
            },
            error: () => this.toast.error(!this.defaults?.id ? "Thêm mới thất bại!" : "Cập nhật thất bại!", "Thông báo"),
            complete: () => this.loading.hide()
          });
        }
      }
    })
  }
}
