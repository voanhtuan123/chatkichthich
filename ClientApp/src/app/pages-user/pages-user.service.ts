import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ArticleView, CmsArticle } from 'src/app/_models/CmsArticle.model';
import { ConfigInitService } from 'src/app/init/config-init.service';
import { HomePage } from '../_models/HomePage.model';
import { MenuHeader } from '../_models/MenuHeader.model';
import { CategoryView } from '../_models/CmsCategory.model';
import { CmsSearch } from '../_models/SearchObject.model';
import { PagePaging } from '../_models/PagePaging';

@Injectable({
  providedIn: 'root'
})
export class PageUserService {
  private requestHeaders = new HttpHeaders();

  private readonly END_POINT = this.configInitService.apiBaseUrl + "/public";

  constructor(private http: HttpClient, private configInitService: ConfigInitService) { }

  getHomePage() {
    return this.http.get<HomePage>(this.END_POINT + "/get-home-page", { responseType: "json" });
  }

  getMenuHeader() {
    return this.http.get<Array<MenuHeader>>(this.END_POINT + "/get-menu-header", { responseType: "json" });
  }

  getArticleBySlug(slug: string) {
    return this.http.get<CmsArticle>(this.END_POINT + "/get-article-by-slug/" + slug, { responseType: "json" });
  }

  getCategoryBySlug(slug: string) {
    return this.http.get<CategoryView[]>(this.END_POINT + "/get-category-by-slug?slug=" + slug, { responseType: "json" });
  }

  getPageArticleBySlug(searchObj: CmsSearch) {
    return this.http.post<PagePaging<ArticleView>>(this.END_POINT + "/get-page-article-by-slug", searchObj, { responseType: "json" });
  }
}
