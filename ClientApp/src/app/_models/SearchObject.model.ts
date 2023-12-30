export class SearchObject {
  pageIndex?: number = 1;
  pageSize?: number = 10;
  textSearch?: string = null;
  parentId?: string = null;
  categoryId?: string = null;
  fromDate?: Date = null;
  toDate?: Date = null;
  status?: number = null;
  websiteId?: number = null;
  showOnSlider?: boolean = null;
  showOnHomePage?: boolean = null;

  constructor(value?: SearchObject) {
    Object.assign(this, value);
  }

  checkSearchObject?(newValue?: SearchObject) {
    if (!("pageIndex" in newValue) && !("pageSize" in newValue)) {
      Object.assign(this, newValue, { pageIndex: 1, pageSize: this.pageSize });
    } else if ("pageIndex" in newValue) {
      this.pageIndex = newValue.pageIndex;
    } else if ("pageSize" in newValue) {
      this.pageSize = newValue.pageSize;
      this.pageIndex = 1;
    }
  }
}

export class CmsSearch {
  pageIndex?: number = 1;
  pageSize?: number = 10;
  textSearch?: string = null;
  parentId?: string = null;
  categoryId?: string = null;
  fromDate?: Date = null;
  toDate?: Date = null;
  status?: number = null;
  websiteId?: number = null;
  slug?: string = null
  listSlug?: string[] = [];
}