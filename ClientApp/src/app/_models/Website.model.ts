import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Organization } from "./Organization.model";

export class Website {
  id?: string = null;
  name?: string = null;
  code?: string = null;
  domain?: string = null;
  template?: string = null;
  org?: Organization = null;
  parent?: Website = null;
  logoPath?: string = null;
  parentId?: string = null;

  constructor(value?: Website) {
    Object.assign(this, value);
  }

  static getDataForm = (value: Website) => (
    new FormGroup({
      id: new FormControl(value.id),
      name: new FormControl(value.name, [Validators.required]),
      code: new FormControl(value.code, [Validators.required]),
      domain: new FormControl(value.domain, [Validators.required]),
      parent: new FormControl(value.parent)
    })
  )
}
