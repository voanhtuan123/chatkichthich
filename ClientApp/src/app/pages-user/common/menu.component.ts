import { Component, OnInit } from "@angular/core";
import { PageUserService } from "../pages-user.service";
import { MenuHeader } from "src/app/_models/MenuHeader.model";
import { TypeMenu } from "src/app/_common/constant/local-constant";
import { Router } from "@angular/router";

@Component({
  selector: "menu",
  template: `
    <ng-container [ngTemplateOutlet]="Menu"[ngTemplateOutletContext]="{ row: listMenuHeader, level: 1 }">
    </ng-container>

    <ng-template #Menu let-level="level" let-row="row">
      <ul class="{{ level === 1 ? 'menu' : 'sub-menu' }}">
        <li *ngIf="level === 1">
          <a [routerLink]="['/']" href="/"><icon-home></icon-home></a>
        </li>

        <li *ngFor="let item of row">
          <ng-container [ngTemplateOutlet]="ItemMenu" [ngTemplateOutletContext]="{ item: item, level }">
          </ng-container>
        </li>
      </ul>
    </ng-template>

    <ng-template #ItemMenu let-item="item" let-level="level">
      <a href="{{ item.linkUrl }}" target="_blank" *ngIf="item.type === typeMenu.URL">{{item?.name}}</a>
      <a [routerLink]="['/' + item.linkUrl]" *ngIf="item.type === typeMenu.CATEGORY || item.type === typeMenu.ARTICLE" >{{item?.name}}</a>

      <ng-container *ngIf="level === 1 && item?.children?.length > 0" [ngTemplateOutlet]="Menu" [ngTemplateOutletContext]="{ row: item?.children, level: level + 1 }">
      </ng-container>
    </ng-template>
  `,
  host: {
    class: "container",
    id: "main-menu"
  }
})
export class MenuComponent implements OnInit {
  listMenuHeader?: Array<MenuHeader> = new Array();

  typeMenu = TypeMenu;

  constructor(private service: PageUserService, private router: Router) { }

  ngOnInit(): void {
    this.service
      .getMenuHeader()
      .subscribe((res) => (this.listMenuHeader = res));
  }
}
