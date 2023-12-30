import { ArticleView } from "./CmsArticle.model";
import { CategoryView } from "./CmsCategory.model";

export class HomePage {
    articleSlider: Array<ArticleView> = new Array();
    article?: ArticleView = null;
    listArticle: Array<ArticleView> = new Array();
    listCategory?: Array<CategoryView> = new Array();
}