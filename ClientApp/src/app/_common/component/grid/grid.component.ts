import { Component, Input, HostBinding } from '@angular/core';

@Component({
  selector: 'grid',
  template: '<ng-content></ng-content>',
})
export class GridComponent {
  @Input() xs: number = 12;
  @Input() sm: number = null;
  @Input() md: number = null;
  @Input() lg: number = null;
  @Input() xl: number = null;
  @Input() container: boolean = false;
  @Input() className: string = "";
  @Input() spacing: number = 0;
  @Input() justify: "end" | "start" | "center" | null = null;

  @HostBinding('class') get classNames() {
    let newClass: string[] = [];
    if (this.container) {
      newClass.push("grid-container");

      if (this.spacing) {
        newClass.push("grid-spacing-" + this.spacing);
      }

      if (this.justify) {
        newClass.push("grid-justify-" + this.justify);
      }

      this.xs = null;
    }

    let item = false;
    if (Boolean(this.xs)) {
      newClass.push(`grid-xs-${this.xs}`);
      item = true;
    }

    if (Boolean(this.sm)) {
      newClass.push(`grid-sm-${this.sm}`);
      item = true;
    }

    if (Boolean(this.md)) {
      newClass.push(`grid-md-${this.md}`);
      item = true;
    }

    if (Boolean(this.lg)) {
      newClass.push(`grid-lg-${this.lg}`);
      item = true;
    }

    if (Boolean(this.xl)) {
      newClass.push(`grid-xl-${this.xl}`);
      item = true;
    }

    if (item) {
      newClass.push(`grid-item`);
    }

    return newClass.join(" ");
  }
}
