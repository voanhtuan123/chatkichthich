package com.globits.cms.dto;

import com.globits.cms.dto.SearchDto;

public class CmsLocationFocalPointsSearchDto extends SearchDto {

	private String name;// Tên
	

	private String code;// Mã 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
