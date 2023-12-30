export class MenuHeader {
    name?: string = null;
    slug?: string = null;
    type?: string = null; // 1: url, 2: category, 3: article
    children?: Array<MenuHeader> = new Array();
    linkUrl?: string = null;
}