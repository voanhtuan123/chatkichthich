import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PageMenuComponent } from './page-menu.component';
import { VexRoutes } from 'src/@vex/interfaces/vex-route.interface';
import { RouterModule } from '@angular/router';
import { BreadcrumbsModule } from 'src/app/_common/component/breadcrumbs/breadcrumbs.module';
import { GridModule } from 'src/app/_common/component/grid/grid.module';
import { IconModule } from 'src/app/_common/Icon/icon.module';
import { TableModule } from 'src/app/_common/component/table/table.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateOrEditComponent } from './create-or-edit/create-or-edit.component';
import { ConfirmDialogModule } from 'src/app/_common/component/dialog/confirm-dialog/confirm-dialog.module';
import { InputComponentModule } from 'src/app/_common/form-component/input-component/input-component.module';
import { SelectComponentModule } from 'src/app/_common/form-component/select-component/select-component.module';
import { DateInputModule } from 'src/app/_common/form-component/date-component/date-input.module';
import { MatDialogModule } from '@angular/material/dialog';

const routes: VexRoutes = [
  { path: "", component: PageMenuComponent },
  { path: ":id", component: PageMenuComponent }
];

@NgModule({
  declarations: [PageMenuComponent, CreateOrEditComponent],
  imports: [
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
export class PageMenuModule { }
