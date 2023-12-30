package com.globits.cms.rest;

import java.util.UUID;

import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.globits.cms.dto.PageMenuCmsDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.PageMenuCmsService;

@RestController
@RequestMapping("/api/cms/page-menu")
public class RestPageMenuCmsController {
    @Autowired
    private PageMenuCmsService pageMenuCmsService;

    // NDuc start
    @RequestMapping(value = "/{menuId}", method = RequestMethod.GET)
    public PageMenuCmsDto getPageMenu(@PathVariable("menuId") UUID menuId) {
        return pageMenuCmsService.getPageMenu(menuId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public PageMenuCmsDto savePageMenu(@RequestBody PageMenuCmsDto dto) {
        return pageMenuCmsService.savePageMenu(dto);
    }

    @RequestMapping(value = "/{menuId}", method = RequestMethod.DELETE)
    public Boolean deletePageMenu(@PathVariable("menuId") UUID menuId) {
        return pageMenuCmsService.deletePageMenu(menuId);
    }

    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Page<PageMenuCmsDto> paging(@RequestBody CmsSearchDto dto) {
        return pageMenuCmsService.paging(dto);
    }

    @RequestMapping(value = "/check-code", method = RequestMethod.GET)
    public Boolean checkDuplicate(@RequestParam("code") String code) {
        return pageMenuCmsService.checkDuplicateCode(code);
    }
    // NDuc end
}
