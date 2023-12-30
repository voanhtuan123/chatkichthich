import { Component } from '@angular/core';

@Component({
    selector: 'icon-delete',
    styleUrls: ['./icon.component.scss'],
    template: `
<svg width="17" height="19" viewBox="0 0 17 19" fill="none">
  <path d="M1 4.25H15.5M5 4.25V3.25C5 2.14543 5.89543 1.25 7 1.25H9.5C10.6046 1.25 11.5 2.14543 11.5 3.25V4.25M10.5 8.25V13.75M6 8.25V13.75M2 4.25H14.5L13.8549 15.8609C13.7961 16.9208 12.9195 17.75 11.858 17.75H4.64197C3.5805 17.75 2.70393 16.9208 2.64505 15.8609L2 4.25Z" stroke="#FF0303" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
</svg>
`
})
export class IconDeleteComponent {
    constructor() { }
}
