import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableComponent } from './table.component';
import { GlobitsPaginatorModule } from '../paginator/paginator.module';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { ItemHTMLTableDirective } from './item-html-table.directive';

@NgModule({
    declarations: [TableComponent, ItemHTMLTableDirective],
    imports: [
        MatProgressBarModule,
        CommonModule,
        GlobitsPaginatorModule,
    ],
    exports: [TableComponent, ItemHTMLTableDirective]
})
export class TableModule { }
