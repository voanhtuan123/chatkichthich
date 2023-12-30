import { FormControl, FormGroup, Validators } from "@angular/forms";
import { CmsCategory, CmsCategoryArticle } from "./CmsCategory.model";
import { Person } from "./person.model";
import { User, UserArticle } from "./User.model";
import { Website } from "./Website.model";
import { convertTitleToSlug, formatDate } from "../_common/constant/local-function";

export class CmsArticle {
    id?: string = null
    status?: number = null;  //Trạng thái
    content?: string = null; // noi dung bai viet
    title?: string = null;// Tiêu đề bài báo
    summary?: string = null;// Tóm tắt nội dung
    titleImageUrl?: string = null;// Đường dẫn đến File ảnh tiêu đề bài báo (nếu có)
    createdBy?: string = null;
    categories?: CmsCategoryArticle[] = [];// Danh sách các chủ đề mà bài báo này thuộc về
    categoryArticle?: CmsCategory[] = [];// Danh sách các chủ đề mà bài báo này thuộc về
    categoryId?: string = null;
    articleType?: CmsArticleType = null;// Loại bài báo (tin nóng, tin thông thường, ...)
    publishDate?: Date | string = null; // Ngày đăng tin
    view?: number = null;; // lượt xem
    subtitle?: string = null;
    slug?: string = null; // dương dan
    notShowHome?: boolean = null;// Không hiển thị trang chủ
    notShowCategory?: boolean = null;// Không hiển thị trang chuyên mục
    notShowAds?: boolean = null;// Không hiển thị quảng cáo
    blogger?: Person = null;    //Blogger
    realAuthor?: string = null;    //Tác giả thực (Người viết thực - Chấm nhuận bút)
    note?: string = null;    //Ghi chú
    noteAvatarImage?: string = null;    //Ghi chú ảnh đại diện
    primaryCategory?: CmsCategory = null;    //Danh mục chính
    plugInTheFocus?: boolean = null;// Cắm tin tiêu điểm
    plugInTheItem?: boolean = null;// Cắm tiêu điểm mục
    tags?: string = null;    //tags
    source?: string = null;    //Nguồn
    royalties?: number = null;    //Nhuận bút
    locationFocalPoints?: CmsLocationFocalPoints = null;    //Vị trí cắm
    articleGroup?: CmsArticleGroup = null;    //Nhóm tin
    sendToUser?: User = null;    //Chuyển tin cho
    userArticles?: Set<UserArticle> = null;//Danh sách các user liên quan đến article theo loại ( người đăng - người tạo - người gửi - người nhận )
    website?: Website = null;
    language?: string = null;
    primaryCategoryCode?: string = null;
    isActive?: boolean = null;
    modifyDate?: string = null;
    typeCustomer?: string = null;    //1. bài viết thường 2. link: thêm 1 trường url (giống category) 3. Sự kiện: thêm ngày bắt đầu và ngày kết thúc
    typeCustomerUrl?: string = null; // url. TypeCustomer
    typeCustomerFromDate?: Date = null; // from date TypeCustomer
    typeCustomerEndDateEvent?: Date = null; // end TypeCustomer
    typeCustomerLocation?: string = null;
    positionIndex?: number = null;
    
    constructor(value?: CmsArticle) {
        Object.assign(this, value);
        if (this.publishDate) {
            this.publishDate = formatDate(this.publishDate, "dd/MM/yyyy HH:mm");
        }
    }

    static getDataForm = (value: CmsArticle) => {
        const article = new FormGroup({
            id: new FormControl(value.id),
            title: new FormControl(value.title, [Validators.required]),
            summary: new FormControl(value.summary),
            publishDate: new FormControl(value.publishDate),
            categoryArticle: new FormControl((value.categories || []).map(item => item.category)),
            content: new FormControl(value.content),
            titleImageUrl: new FormControl(value.titleImageUrl),
            primaryCategory: new FormControl(value.primaryCategory),
            realAuthor: new FormControl(value.realAuthor),
            slug: new FormControl(value.slug),
            isActive: new FormControl(value.isActive),
            plugInTheFocus: new FormControl(value.plugInTheFocus),
            plugInTheItem: new FormControl(value.plugInTheItem),
            notShowHome: new FormControl(value.notShowHome),
            positionIndex: new FormControl(value.positionIndex),
        })

        article.get("title").valueChanges.subscribe(val => {
            article.get("slug").setValue(convertTitleToSlug(val), { emitEvent: false })
        })

        return article;
    }
}

export class CmsArticleType {
    id?: string = null;
    name?: string = null;// Tên loại bài báo
    code?: string = null;// Mã loại bài báo
    description?: string = null;// Mô tả
    priority?: number = null;//
    isDuplicate?: boolean = null;
    dupName?: string = null;
    dupCode?: string = null;
    website?: Website = null;
}

export class CmsLocationFocalPoints {
    name?: string = null;// Tên
    code?: string = null;// Mã
}

export class CmsArticleGroup {
    name?: string = null;
    description?: string = null;
    slug?: string = null;
    positionIndex?: number = null;
    avatar?: FileDescription = null;
    website?: Website = null;
}

export class FileDescription {
    id?: string = null;
    serialVersionUID?: number = null;
    contentType?: string = null;
    contentSize?: number = null;
    name?: string = null;
    extension?: string = null;
    filePath?: string = null;
}

export class ArticleView {
    id?: string = null;
    title?: string = null;
    summary?: string = null;
    publishDate?: Date = null;
    slug?: string = null;
    titleImageUrl?: string = null;
}