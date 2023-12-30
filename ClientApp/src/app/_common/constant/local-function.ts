import { toPath } from "lodash";
import { formatDate as format } from "@angular/common";
import { TypeShard } from "./local-constant";

export function formatDate(val: Date | string, type: string = "dd/MM/yyyy") {
  return val ? format(new Date(val), type, "en-US") : "";
}

export function getInObject<T extends TypeShard = TypeShard>(objValue: T, fieldValue: string): any {
  toPath(fieldValue).forEach((item: string) => {
    objValue = objValue?.[item];
  });

  return objValue;
}

export const convertTitleToSlug = (name: string) => {
  if(!name) {
    return "";
  }
  name = name.toLowerCase();
  name = name.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
  name = name.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
  name = name.replace(/ì|í|ị|ỉ|ĩ/g, "i");
  name = name.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
  name = name.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
  name = name.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
  name = name.replace(/đ/g, "d");
  name = name.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
  name = name.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
  name = name.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
  name = name.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
  name = name.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
  name = name.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
  name = name.replace(/Đ/g, "D");
  name = name.replace("'", "");
  name = name.replace("?", "");
  name = name.replace(",", "");
  name = name.replace(".", "");
  name = name.replace(" ", "-")
  const value = name.split(" ");
  return value.join("-");
}