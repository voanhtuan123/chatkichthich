import { Component } from '@angular/core';

@Component({
    selector: 'icon-home',
    styleUrls: ['./icon.component.scss'],
    template: `
    <svg width="30" height="40" viewBox="0 0 24 24" fill="none">
       <path fill-rule="evenodd" clip-rule="evenodd" d="M21.4498 10.275L11.9998 3.1875L2.5498 10.275L2.9998 11.625H3.7498V20.25H20.2498V11.625H20.9998L21.4498 10.275ZM5.2498 18.75V10.125L11.9998 5.0625L18.7498 10.125V18.75H14.9999V14.3333L14.2499 13.5833H9.74988L8.99988 14.3333V18.75H5.2498ZM10.4999 18.75H13.4999V15.0833H10.4999V18.75Z" fill="#ffffff"/>
    </svg>
`
})
export class IconHomeComponent {
    constructor() { }
}
