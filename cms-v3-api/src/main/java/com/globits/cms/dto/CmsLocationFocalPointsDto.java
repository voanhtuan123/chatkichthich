package com.globits.cms.dto;

import com.globits.cms.domain.CmsLocationFocalPoints;


public class CmsLocationFocalPointsDto extends BaseObjectDto {

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
	public CmsLocationFocalPointsDto() {
		super();
	}
	
	public CmsLocationFocalPointsDto(CmsLocationFocalPoints entity) {
		super();
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();

			

			

		}
	}
	
	public CmsLocationFocalPointsDto(CmsLocationFocalPoints entity, boolean simple) {
		super();
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.code = entity.getCode();

		}
	}
}
