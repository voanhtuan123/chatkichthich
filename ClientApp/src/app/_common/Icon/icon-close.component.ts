import { Component ,Input} from '@angular/core';

@Component({
    selector: 'icon-close',
    styleUrls: ['./icon.component.scss'],
    template: `
    <svg width="15" height="15" viewBox="0 0 18 18" fill="none">
      <path [attr.fill]="color" d="M10.5851 8.99904L17.6675 1.92716C17.8792 1.71545 17.9981 1.42831 17.9981 1.12891C17.9981 0.829501 17.8792 0.54236 17.6675 0.330649C17.4558 0.118938 17.1687 0 16.8693 0C16.5699 0 16.2828 0.118938 16.0711 0.330649L9 7.41377L1.92888 0.330649C1.71719 0.118938 1.43008 2.65824e-07 1.1307 2.68055e-07C0.831331 2.70286e-07 0.54422 0.118938 0.332532 0.330649C0.120844 0.54236 0.0019188 0.829501 0.0019188 1.12891C0.0019188 1.42831 0.120844 1.71545 0.332532 1.92716L7.4149 8.99904L0.332532 16.0709C0.227164 16.1754 0.143531 16.2998 0.0864579 16.4368C0.0293845 16.5738 0 16.7208 0 16.8692C0 17.0176 0.0293845 17.1645 0.0864579 17.3016C0.143531 17.4386 0.227164 17.5629 0.332532 17.6674C0.43704 17.7728 0.561376 17.8565 0.698368 17.9135C0.83536 17.9706 0.982298 18 1.1307 18C1.27911 18 1.42605 17.9706 1.56304 17.9135C1.70003 17.8565 1.82437 17.7728 1.92888 17.6674L9 10.5843L16.0711 17.6674C16.1756 17.7728 16.3 17.8565 16.437 17.9135C16.574 17.9706 16.7209 18 16.8693 18C17.0177 18 17.1646 17.9706 17.3016 17.9135C17.4386 17.8565 17.563 17.7728 17.6675 17.6674C17.7728 17.5629 17.8565 17.4386 17.9135 17.3016C17.9706 17.1645 18 17.0176 18 16.8692C18 16.7208 17.9706 16.5738 17.9135 16.4368C17.8565 16.2998 17.7728 16.1754 17.6675 16.0709L10.5851 8.99904Z" />
    </svg>
`
})
export class IconCloseComponent {
  @Input() color = "white"
    constructor() { }
}
