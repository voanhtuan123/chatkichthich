import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleComponent } from './article.component';
import { GridModule } from 'src/app/_common/component/grid/grid.module';
import { IconModule } from 'src/app/_common/Icon/icon.module';
import { TableModule } from 'src/app/_common/component/table/table.module';
import { ConfirmDialogModule } from 'src/app/_common/component/dialog/confirm-dialog/confirm-dialog.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DateInputModule } from 'src/app/_common/form-component/date-component/date-input.module';
import { MatDialogModule } from '@angular/material/dialog';
import { RouterModule } from '@angular/router';
import { SelectComponentModule } from 'src/app/_common/form-component/select-component/select-component.module';
import { InputComponentModule } from 'src/app/_common/form-component/input-component/input-component.module';
import { BreadcrumbsModule } from 'src/app/_common/component/breadcrumbs/breadcrumbs.module';
import { VexRoutes } from 'src/@vex/interfaces/vex-route.interface';
import { CreateOrEditComponent } from './create-or-edit/create-or-edit.component';
import { CKEditorModule } from 'ckeditor4-angular';

const routes: VexRoutes = [{ path: "", component: ArticleComponent }];

@NgModule({
  declarations: [ArticleComponent, CreateOrEditComponent],
  imports: [
    GridModule,
    CKEditorModule,
    BreadcrumbsModule,
    GridModule,
    IconModule,
    TableModule,
    CommonModule,
    ConfirmDialogModule,
    FormsModule,
    ReactiveFormsModule,
    InputComponentModule,
    SelectComponentModule,
    DateInputModule,
    MatDialogModule,
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})
export class ArticleModule { }
