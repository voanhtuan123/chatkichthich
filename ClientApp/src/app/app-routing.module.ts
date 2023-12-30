import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CustomLayoutComponent } from './custom-layout/custom-layout.component';
import { VexRoutes } from '../@vex/interfaces/vex-route.interface';
import { QuicklinkModule, QuicklinkStrategy } from 'ngx-quicklink';
import { AuthGuard } from './_guards/index';

const routes: VexRoutes = [
  {
    path: 'login',
    loadChildren: () => import('./pages/pages/auth/login/login.module').then(m => m.LoginModule),
  },
  {
    path: 'choose-view-account',
    canActivate: [AuthGuard],
    loadChildren: () => import('./pages/choose-view-account/choose-view-account.module').then(m => m.ChooseViewAccountModule),
  },
  {
    path: 'admin',
    component: CustomLayoutComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      {
        path: 'category',
        loadChildren: () => import('./pages/category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'article',
        loadChildren: () => import('./pages/article/article.module').then(m => m.ArticleModule),
      },
      {
        path: 'article-slider',
        loadChildren: () => import('./pages/article-slider/article-slider.module').then(m => m.ArticleSliderModule),
      },
      {
        path: 'article-home',
        loadChildren: () => import('./pages/article-home/article-home.module').then(m => m.ArticleHomeModule),
      },
      {
        path: 'page-menu',
        loadChildren: () => import('./pages/page-menu/page-menu.module').then(m => m.PageMenuModule),
      },
    ]
  },
  {
    path: '',
    loadChildren: () => import('./pages-user/page-user.module').then(m => m.PageUserModule)
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      preloadingStrategy: QuicklinkStrategy,
      scrollPositionRestoration: 'enabled',
      relativeLinkResolution: 'corrected',
      anchorScrolling: 'enabled'
    })
  ],
  exports: [RouterModule, QuicklinkModule]
})
export class AppRoutingModule {
}
