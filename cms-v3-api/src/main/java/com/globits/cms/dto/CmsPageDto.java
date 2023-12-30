package com.globits.cms.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import com.globits.cms.domain.CmsPage;
import com.globits.cms.domain.CmsPane;

public class CmsPageDto extends BaseObjectDto {
	private String name;
	private String code;
	private String templatePath;
	private WebsiteDto website;
	private List<CmsPaneDto> panes;

	public CmsPageDto() {
		super();
	}

	public CmsPageDto(CmsPage entity) {
		super();
		if (entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.name = entity.getName();
			this.templatePath = entity.getTemplatePath();

			if (entity.getWebsite() != null) {
				this.website = new WebsiteDto(entity.getWebsite());
			}
			if (entity.getPanes() != null && entity.getPanes().size() > 0) {
				this.panes = new ArrayList<CmsPaneDto>();
				for (CmsPane c : entity.getPanes()) {
					// CmsPaneDto cpDto = new CmsPaneDto(c);
					// cpDto.setPage(null);
					// this.panes.add(cpDto);
					this.panes.add(new CmsPaneDto(c));
				}
			}

		}
	}

	public CmsPageDto(CmsPage entity, Boolean sample) {
		super();
		if (entity != null) {
			this.id = entity.getId();
			this.code = entity.getCode();
			this.name = entity.getName();
			this.templatePath = entity.getTemplatePath();
		}
	}

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

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public WebsiteDto getWebsite() {
		return website;
	}

	public void setWebsite(WebsiteDto website) {
		this.website = website;
	}

	public List<CmsPaneDto> getPanes() {
		return panes;
	}

	public void setPanes(List<CmsPaneDto> panes) {
		this.panes = panes;
	}

}
