import { Component, Input } from '@angular/core';

@Component({
    selector: 'button-component',
    template: `
    <button class="btn-component {{color}}" [type]="type">
        <ng-content></ng-content>
    </button>
    `,
})
export class ButtonComponent {

    @Input() color: string = 'primary';
    @Input() content: string = ''
    @Input() type: string = 'button'

    constructor() {

    }

}
