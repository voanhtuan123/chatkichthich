import { Directive, TemplateRef, Input, OnInit } from '@angular/core';

@Directive({
  selector: 'ng-template[item-html-table]'
})
export class ItemHTMLTableDirective {

  @Input("item-html-table") name = '';
  templateRef: TemplateRef<any> = this.template;

  constructor(private template: TemplateRef<any>) {
  }
}
