export interface TypeShard {
    [field: string]: any;
}

export enum TypeMenu {
    URL = "1",
    ARTICLE = "2",
    CATEGORY = "3",
}

export const LIST_TYPE_PAGE_MENU = [
    { id: TypeMenu.URL, name: "Đường link" },
    { id: TypeMenu.ARTICLE, name: "Bài viết" },
    { id: TypeMenu.CATEGORY, name: "Danh mục" },
]