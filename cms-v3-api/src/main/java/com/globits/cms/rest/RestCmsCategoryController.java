package com.globits.cms.rest;

import com.globits.cms.dto.CmsCategoryDto;
import com.globits.cms.dto.CmsSearchDto;
import com.globits.cms.dto.SearchDto;
import com.globits.cms.service.CmsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cms/category")
public class RestCmsCategoryController {

    @Autowired
    private CmsCategoryService cmsCategoryService;

    // NDuc start
    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public CmsCategoryDto getCmsCategory(@PathVariable("categoryId") UUID categoryId) {
        return cmsCategoryService.getCmsCategory(categoryId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CmsCategoryDto saveCmsCategory(@RequestBody CmsCategoryDto dto) {
        return cmsCategoryService.saveCmsCategory(dto);
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.DELETE)
    public Boolean deleteCmsCategory(@PathVariable("categoryId") UUID categoryId) {
        return cmsCategoryService.deleteCmsCategory(categoryId);
    }

    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Page<CmsCategoryDto> paging(@RequestBody CmsSearchDto dto) {
        return cmsCategoryService.paging(dto);
    }

    @RequestMapping(value = "/paging-parent-and-child", method = RequestMethod.POST)
    public Page<CmsCategoryDto> pagingParentAndChild(@RequestBody CmsSearchDto dto) {
        return cmsCategoryService.pagingParentAndChild(dto);
    }
    // NDuc end
}
