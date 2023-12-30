import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ArticleView } from 'src/app/_models/CmsArticle.model';

@Component({
    selector: 'slider',
    template: `
    <ul class="list" #ListSlider>
        <li class="item" *ngFor="let item of articleSlider">
            <img [src]="item?.titleImageUrl" [alt]="item.title"  [ngStyle]="{width: width}">
        </li>
    </ul>

    <div class="buttons">
        <button (click)="onClickPrev()"><</button>
        <button (click)="onClickNext()">></button>
    </div>
    `,
    host: {
        class: "slider container"
    }
})
export class SliderComponent implements OnInit {
    @Input() articleSlider: Array<ArticleView> = [];
    @ViewChild("ListSlider") slider = null;
    active = 1;
    width: number = null;

    constructor() {
    }

    ngOnInit(): void {
        window.onresize = () => this.reloadSlider();
    }

    refreshInterval = setInterval(() => this.onClickNext(), 3000);

    onClickNext() {
        this.active = this.active + 1 < this.articleSlider.length ? this.active + 1 : 1;
        this.reloadSlider();
    }

    onClickPrev = function () {
        this.active = this.active - 1 >= 1 ? this.active - 1 : this.articleSlider.length;
        this.reloadSlider();
    }

    reloadSlider() {
        const nativeElement = this.slider.nativeElement as HTMLElement;
        nativeElement.style.transform = `translate3d(-${nativeElement.clientWidth / this.articleSlider.length * (this.active - 1)}px, 0px, 0px)`;

        clearInterval(this.refreshInterval);
        this.refreshInterval = setInterval(() => this.onClickNext(), 3000);
    }
}