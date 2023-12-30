import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GridModule } from 'src/app/_common/component/grid/grid.module';
import { IconModule } from 'src/app/_common/Icon/icon.module';
import { TableModule } from 'src/app/_common/component/table/table.module';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { InputComponentModule } from 'src/app/_common/form-component/input-component/input-component.module';
import { BreadcrumbsModule } from 'src/app/_common/component/breadcrumbs/breadcrumbs.module';
import { VexRoutes } from 'src/@vex/interfaces/vex-route.interface';
import { ArticleSliderComponent } from './article-slider.component';

const routes: VexRoutes = [{ path: "", component: ArticleSliderComponent }];

@NgModule({
  declarations: [ArticleSliderComponent],
  imports: [
    GridModule,
    BreadcrumbsModule,
    GridModule,
    IconModule,
    TableModule,
    CommonModule,
    ReactiveFormsModule,
    InputComponentModule,
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule]
})
export class ArticleSliderModule { }
