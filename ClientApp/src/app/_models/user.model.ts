import { CmsArticle } from "./CmsArticle.model";
import { Person } from "./person.model";

export class User {
    id?: string;
    displayName?: string;
    username?: string;
    password?: string;
    oldPassword?: string;
    confirmPassword?: string;
    isSetPassword?: boolean;
    changePass?: boolean;
    active?: boolean;
    lastName?: string;
    firstName?: string;
    dob?: Date;
    birthPlace?: string;
    email?: string;
    hasPhoto?: boolean;
    roles?: any[];
    person?: Person;
    groups?: any[];
    constructor() {

    }
}

export class UserArticle {
    article?: CmsArticle = null;
    user?: User = null;
    type?: number = null; //loại ( cấu hình trong Constant )
}