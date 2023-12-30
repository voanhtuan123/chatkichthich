import { Injectable } from "@angular/core";
import { MatDialog } from "@angular/material/dialog";
import { ConfirmDialogComponent } from "./confirm-dialog.component";

interface DataConfirmDialogComponent {
  title: string;
  text: string;
  confirmButtonText?: string;
  cancelButtonText?: string;
  onYesClick: () => void;
}

@Injectable({
  providedIn: "root",
})
export class ConfirmDialogService {
  constructor(private dialog: MatDialog) {}

  openConfirmDialog(data: DataConfirmDialogComponent) {
    this.dialog.open(ConfirmDialogComponent, {
      disableClose: false,
      width: "400px",
      data,
    });
  }
}