import { FormControl, FormGroup, Validators } from "@angular/forms";
import { Website } from "./Website.model";

export class PageMenuCms {
    serialVersionUID?: number = null;
    code?: string = null;
    name?: string = null;
    linkUrl?: string = null;
    parent?: PageMenuCms = null;
    subPageMenu?: PageMenuCms[] = [];
    website?: Website = null;
    type?: string = null;
    id?: string = null;
    positionIndex?: number= null;
    
    constructor(value?: PageMenuCms) {
        Object.assign(this, value);
    }

    static getDataForm = (value: PageMenuCms) => {
        const form = new FormGroup({
            id: new FormControl(value.id),
            name: new FormControl(value.name, [Validators.required]),
            code: new FormControl(value.code, [Validators.required]),
            type: new FormControl(value.type, [Validators.required]),
            linkUrl: new FormControl(value.linkUrl, [Validators.required]),
            parent: new FormControl(value.parent),
            positionIndex: new FormControl(value.positionIndex)
        })

        form.get("type").valueChanges.subscribe(() => form.get("linkUrl").setValue(null));
        return form;
    }
}