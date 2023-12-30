import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CmsArticle, FileDescription } from 'src/app/_models/CmsArticle.model';
import { CmsCategory } from 'src/app/_models/CmsCategory.model';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { ConfigInitService } from 'src/app/init/config-init.service';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private requestHeaders = new HttpHeaders();

  private readonly END_POINT = this.configInitService.apiBaseUrl + "/api/cms/article";

  constructor(private http: HttpClient, private configInitService: ConfigInitService) { }

  pagingArticle(search: SearchObject) {
    const url = this.END_POINT + "/paging";
    return this.http.post<PagePaging<CmsArticle>>(url, search, { responseType: "json" });
  }

  saveArticle(article: CmsArticle) {
    const url = this.END_POINT + "/save";
    return this.http.post<CmsArticle>(url, article, { headers: this.requestHeaders, responseType: "json" });
  }

  getArticle(articleId: string) {
    const url = this.END_POINT + "/" + articleId;
    return this.http.get<CmsArticle>(url, { responseType: "json" });
  }

  deleteArticle(articleId: string) {
    let url = this.END_POINT + "/" + articleId;
    return this.http.delete<boolean>(url, { responseType: "json" });
  }

  uploadAttachment(value: File) {
    const formData = new FormData();
    formData.append("uploadfile", value);
    return this.http.post<FileDescription>(this.END_POINT + "/" + "uploadattachment", formData, { responseType: "json" });
  }
}
