import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CmsCategory } from 'src/app/_models/CmsCategory.model';
import { CategoryService } from '../category.service';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'create-or-edit',
  templateUrl: './create-or-edit.component.html',
  host: {
    class: "popup-container"
  }
})
export class CreateOrEditComponent {
  form = CmsCategory.getDataForm(this.defaults);

  constructor(
    @Inject(MAT_DIALOG_DATA) public defaults: CmsCategory,
    private dialogRef: MatDialogRef<CreateOrEditComponent>,
    private service: CategoryService,
    private toast: ToastrService,
    private loading: NgxSpinnerService
  ) { }

  get f() {
    return this.form.controls;
  }

  onChangeDisplay(event) {
    this.form.get("displayType").setValue(event.target.checked)
  }

  async onSubmit() {
    if (this.form.invalid) {
      return;
    }

    try {
      this.loading.show();
      const value = this.form.getRawValue()
      const res = await this.service.saveCategory({...value,displayType: value.displayType ? 2 : 1}).toPromise();
      if (!res) {
        throw new Error()
      }

      this.dialogRef.close(res);
      this.toast.success(!this.defaults?.id ? "Thêm mới thành công!" : "Cập nhật thành công!");
    } catch (e) {
      this.toast.error(!this.defaults?.id ? "Thêm mới thất bại!" : "Cập nhật thất bại!")
    } finally {
      this.loading.hide()
    }
  }
}
