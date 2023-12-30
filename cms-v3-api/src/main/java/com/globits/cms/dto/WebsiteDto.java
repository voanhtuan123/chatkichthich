package com.globits.cms.dto;

import java.util.UUID;

import com.globits.cms.domain.Website;
import com.globits.core.dto.OrganizationDto;

public class WebsiteDto extends BaseObjectDto {
    private String name;
    private String code;
    private String domain;
    private String template;
    private OrganizationDto org;
    private WebsiteDto parent;
    private String logoPath;
    private UUID parentId;

    public WebsiteDto() {

    }

    public WebsiteDto(Website entity) {
        if (entity != null) {
            this.name = entity.getName();
            this.code = entity.getCode();
            this.domain = entity.getDomain();
            this.template = entity.getTemplate();
            this.logoPath = entity.getLogoPath();
            this.setId(entity.getId());
            if (entity.getParent() != null) {
                this.parent = new WebsiteDto(entity.getParent());
                this.parentId = entity.getParent().getId();
            }
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public OrganizationDto getOrg() {
        return org;
    }

    public void setOrg(OrganizationDto org) {
        this.org = org;
    }

    public WebsiteDto getParent() {
        return parent;
    }

    public void setParent(WebsiteDto parent) {
        this.parent = parent;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }
}
