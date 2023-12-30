package com.globits.cms.dto;

import java.util.ArrayList;
import java.util.List;

import com.globits.cms.domain.CmsPage;
import com.globits.cms.domain.CmsPane;
import com.globits.cms.domain.CmsPaneDataConfig;

public class CmsPaneDto extends BaseObjectDto{
	private String code;
	private String name;
	private CmsPageDto page;
	private List<CmsPaneDataConfigDto> listData;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public CmsPageDto getPage() {
		return page;
	}
	public void setPage(CmsPageDto page) {
		this.page = page;
	}
	public CmsPaneDto() {
		super();
	}
	public List<CmsPaneDataConfigDto> getListData() {
		return listData;
	}
	public void setListData(List<CmsPaneDataConfigDto> listData) {
		this.listData = listData;
	}
	public CmsPaneDto(CmsPane entity) {
		super();
		if(entity!=null) {
			this.id = entity.getId();
			this.code=entity.getCode();
			this.name=entity.getName();
			if(entity.getPage() != null) {
				this.page=new CmsPageDto(entity.getPage(), true);
			}
			if(entity.getListData() != null && entity.getListData().size() >0)
			{
				this.listData = new ArrayList<CmsPaneDataConfigDto>();
				for(CmsPaneDataConfig cpdc:entity.getListData()) {
					CmsPaneDataConfigDto dto = new CmsPaneDataConfigDto(cpdc,true);
					this.listData.add(dto);
				}
			}
//			this.listData=new ArrayList<CmsPaneDataConfigDto>();
		}
	}
	
}