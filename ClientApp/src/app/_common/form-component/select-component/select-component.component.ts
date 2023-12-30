import { ChangeDetectorRef, Component, ContentChild, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormGroup, FormGroupDirective, FormControl, ControlContainer } from '@angular/forms';
import { MatFormFieldControl, MatFormField } from '@angular/material/form-field';

@Component({
  selector: 'select-component',
  templateUrl: './select-component.component.html',
  viewProviders: [
    { provide: ControlContainer, useExisting: FormGroupDirective }
  ],
  host: {
    "class": "d-block"
  }
})
export class SelectComponent implements OnInit {

  @Output() public onChange: EventEmitter<any> = new EventEmitter();
  @Input() public options: any[] = [];
  @Input() public objectValue: boolean = false;
  @Input() public placeholder: string = '';
  @Input() public multiple: boolean = false;
  @Input() public label: string = '';
  @Input() public hint: string = '';
  @Input() public control: any;
  @Input() public controlName: string = '';
  @Input() public appearance: 'legacy' | 'fill' | 'standard' | 'outline' = 'outline';
  @Input() public requiredText: string = this.controlName + ' không được để trống';
  @Input() public notFoundText: string = 'Không tìm thấy dữ liệu';
  @Input() public required: boolean = null;
  @Input() bindLabel: 'name' | 'shortName' | 'value' | 'title' = 'name';
  @Input() bindValue: 'id' | 'code' | 'value' | "slug" = 'id';
  @Input() floatLabel: 'auto' | 'always';

  formGroup!: FormGroup;

  constructor(private ctrlContainer: FormGroupDirective, private cdr: ChangeDetectorRef,) { }

  @ContentChild(MatFormFieldControl, { static: true }) public formFieldControl!: MatFormFieldControl<any>;

  public matFormField!: MatFormField;

  ngOnInit(): void {
    if (this.matFormField) this.matFormField._control = this.formFieldControl;
    this.formGroup = this.ctrlContainer.form;
    const control = new FormControl('');
    this.formGroup.addControl(this.controlName, control);
  }

  ngAfterContentChecked() {
    this.cdr.detectChanges();
  }

  handleChange($event) {
    this.onChange.emit($event); // this will pass the $event object to the parent component.
    if ($event == null && this.options.find(e => e.id == null)) {
      this.floatLabel = 'always'
    } else {
      this.floatLabel = 'auto'
    }
  }

  compareFn(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.id == c2.id : c1 == c2;
  }

  getErrorMessage(): string {
    let { errors } = this.control;

    if (typeof errors?.[Object.keys(errors)[0]] === 'boolean') {
      errors = null
    }
    return errors?.[Object.keys(errors)[0]]
  }

}
