import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VexRoutes } from 'src/@vex/interfaces/vex-route.interface';
import { RouterModule } from '@angular/router';
import { ArticleComponent } from './article/article.component';
import { HomeComponent } from './home/home.component';
import { IconModule } from '../_common/Icon/icon.module';
import { GlobitsPaginatorModule } from '../_common/component/paginator/paginator.module';
import { MenuComponent } from './common/menu.component';
import { SliderComponent } from './common/slider.component';
import { LayoutUserComponent } from './layout-user.component';

const routes: VexRoutes = [
    {
        path: "",
        component: LayoutUserComponent,
        children: [
            { path: "", component: HomeComponent },
            { path: ":category", component: ArticleComponent },
            { path: ":category/:article", component: ArticleComponent },
        ]
    }
];

@NgModule({
    declarations: [ArticleComponent, HomeComponent, MenuComponent, SliderComponent, LayoutUserComponent],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        GlobitsPaginatorModule,
        IconModule

    ],
    exports: [RouterModule]
})
export class PageUserModule { }
