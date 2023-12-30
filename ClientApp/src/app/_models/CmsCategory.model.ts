import { ArticleView, CmsArticle } from "./CmsArticle.model";
import { Website } from "./Website.model";
import { PagePaging } from "./PagePaging";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import { convertTitleToSlug } from "../_common/constant/local-function";

export class CmsCategory {
    id?: string = null;
    title?: string = null;
    code?: string = null;
    description?: string = null;
    slug?: string = null;
    parent?: CmsCategory = null;
    isActive?: boolean = true;
    isShowMenu?: boolean = null;
    positionIndex?: number = null
    ischeck?: boolean = null;
    subCategories?: CmsCategory[] = [];
    articles?: PagePaging<CmsArticle> = null;//Các bài viết hiển thị trực tiếp
    listArticles?: CmsArticle = null;//Các bài viết hiển thị trực tiếp
    allArticles?: CmsArticle = null;//Các bài viết hiển thị trực tiếp
    website?: Website = null;
    categoryType?: number = null;// =1:Danh sách bài báo, =2:Link
    displayType?: number = 1;// =1: Hiển thị toán bộ bài viêt; =2: Hiển thị bài viết tiêu điểm
    linkUrl?: string = null;//đường dẫn link
    isShowLink?: boolean = null;
    showHomePage?: boolean = null;

    constructor(value?: CmsCategory) {
        Object.assign(this, value);
    }

    static getDataForm = (value: CmsCategory) => {
        const category = new FormGroup({
            id: new FormControl(value.id),
            title: new FormControl(value.title, [Validators.required]),
            code: new FormControl(value.code, [Validators.required]),
            showHomePage: new FormControl(value.showHomePage),
            isActive: new FormControl(value.isActive),
            parent: new FormControl(value.parent),
            positionIndex: new FormControl(value.positionIndex),
            slug: new FormControl(value.slug),
            description: new FormControl(value.description),
            displayType: new FormControl(value?.displayType === 2)
        });

        category.controls.title.valueChanges.subscribe(val => {
            category.controls.slug.setValue(convertTitleToSlug(val), { emitEvent: false })
        })

        return category
    }
}

export class CmsCategoryArticle {
    id?: string;
    category?: CmsCategory = null;
    article?: CmsArticle = null;
    ischeck?: boolean = false;
    categoryId?: string = null;
}


export class CategoryView {
    id?: string = null;
    name?: string = null;
    slug?: string = null;
    children?: Array<CategoryView> = new Array();
    articles?: Array<ArticleView> = new Array(); // bai viet theo category
    displayType?: number = 1;// =1: Hiển thị toán bộ bài viêt; =2: Hiển thị bài viết tiêu điểm
}