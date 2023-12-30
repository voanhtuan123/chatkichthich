import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {  MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { ConfirmDialogComponent } from './confirm-dialog.component';
import { IconModule } from '../../../Icon/icon.module';

@NgModule({
  declarations: [ConfirmDialogComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatDialogModule,
    IconModule,
  ],
  exports: [ConfirmDialogComponent]
})
export class ConfirmDialogModule { }
