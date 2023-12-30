import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CmsCategory } from 'src/app/_models/CmsCategory.model';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { ConfigInitService } from 'src/app/init/config-init.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private requestHeaders = new HttpHeaders();

  private readonly END_POINT = this.configInitService.apiBaseUrl + "/api/cms/category";

  constructor(private http: HttpClient, private configInitService: ConfigInitService) { }

  pagingCategory(search: SearchObject) {
    const url = this.END_POINT + "/paging";
    return this.http.post<PagePaging<CmsCategory>>(url, search, { responseType: "json" });
  }

  pagingParentAndChild(search: SearchObject) {
    const url = this.END_POINT + "/paging-parent-and-child";
    return this.http.post<PagePaging<CmsCategory>>(url, search);
  }

  saveCategory(category: CmsCategory) {
    const url = this.END_POINT + "/save";
    return this.http.post<CmsCategory>(url, category, { headers: this.requestHeaders, responseType: "json" });
  }

  getCategory(categoryId: string) {
    const url = this.END_POINT + "/" + categoryId;
    return this.http.get<CmsCategory>(url, { responseType: "json" });
  }

  deleteCategory(categoryId: string) {
    let url = this.END_POINT + "/" + categoryId;
    return this.http.delete<boolean>(url, { responseType: "json" });
  }
}
