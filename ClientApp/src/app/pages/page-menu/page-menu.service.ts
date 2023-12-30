import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PageMenuCms } from 'src/app/_models/PageMenuCms.model';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { ConfigInitService } from 'src/app/init/config-init.service';

@Injectable({
  providedIn: 'root'
})
export class PageMenuService {
  private requestHeaders = new HttpHeaders();

  private readonly END_POINT = this.configInitService.apiBaseUrl + "/api/cms/page-menu";

  constructor(private http: HttpClient, private configInitService: ConfigInitService) { }

  pagingPageMenu(search: SearchObject) {
    const url = this.END_POINT + "/paging";
    return this.http.post<PagePaging<PageMenuCms>>(url, search, { responseType: "json" });
  }

  savePageMenu(category: PageMenuCms) {
    const url = this.END_POINT + "/save";
    return this.http.post<PageMenuCms>(url, category, { headers: this.requestHeaders, responseType: "json" });
  }

  getPageMenu(categoryId: string) {
    const url = this.END_POINT + "/" + categoryId;
    return this.http.get<PageMenuCms>(url, { responseType: "json" });
  }

  deletePageMenu(categoryId: string) {
    let url = this.END_POINT + "/" + categoryId;
    return this.http.delete<boolean>(url, { responseType: "json" });
  }
}
