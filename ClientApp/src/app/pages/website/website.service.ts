import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PagePaging } from 'src/app/_models/PagePaging';
import { SearchObject } from 'src/app/_models/SearchObject.model';
import { ConfigInitService } from 'src/app/init/config-init.service';
import { Website } from 'src/app/_models/Website.model';

@Injectable({
  providedIn: 'root'
})
export class WebsiteService {
  private requestHeaders = new HttpHeaders();

  private readonly END_POINT = this.configInitService.apiBaseUrl + "/api/cms/website";

  constructor(private http: HttpClient, private configInitService: ConfigInitService) { }

  pagingWebsite(search: SearchObject) {
    const url = this.END_POINT + "/paging";
    return this.http.post<PagePaging<Website>>(url, search, { responseType: "json" });
  }

  saveWebsite(website: Website) {
    const url = this.END_POINT + "/save";
    return this.http.post<Website>(url, website, { headers: this.requestHeaders, responseType: "json" });
  }

  getWebsite(websiteId: string) {
    const url = this.END_POINT + "/" + websiteId;
    return this.http.get<Website>(url, { responseType: "json" });
  }

  deleteWebsite(websiteId: string) {
    let url = this.END_POINT + "/" + websiteId;
    return this.http.delete<boolean>(url, { responseType: "json" });
  }

  checkDomain(websiteDomain: string){
    let url = this.END_POINT + "/check-domain" ;
    let params = new HttpParams();
    params = params.append('domainName', websiteDomain);
    return this.http.get<boolean>(url, {params: params});
  }
}
