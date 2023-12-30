package com.globits.cms.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.globits.cms.dto.*;
import com.globits.cms.dto.home.ArticleViewDto;
import com.globits.cms.dto.home.CategoryViewDto;
import com.globits.cms.dto.home.HomePageDto;
import com.globits.cms.dto.home.MenuHeaderDto;
import com.globits.cms.service.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.globits.cms.domain.CmsPage;
import com.globits.cms.repository.CmsPageRepository;
import com.globits.core.service.FileDescriptionService;

@RestController
@RequestMapping("/public")
public class RestPublicController {
	@Autowired
	private Environment env;
	@Autowired
	FileDescriptionService fileDescriptionService;

	@Autowired
	CmsCategoryService cmsCategoryService;
	@Autowired
	PageMenuCmsService pageMenuCmsService;

	@Autowired
	CmsPageRepository cmsPageRepository;

	@Autowired
	CmsPageService cmsPageService;

	@Autowired
	WebsiteService websiteService;

	@Autowired
	private CmsPublishService cmsPublishService;

	@RequestMapping(path = "/getImage/{filename}/{type}", method = RequestMethod.GET)
	public void getImage(HttpServletResponse response, @PathVariable String filename, @PathVariable String type)
			throws IOException {
		String path = "";
		if (env.getProperty("cms.file.folder") != null) {
			path = env.getProperty("cms.file.folder");
		}
		File file = new File(path + filename + "." + type);
		if (file.exists()) {
			String contentType = "application/octet-stream";
			response.setContentType(contentType);
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(file);
			// copy from in to out
			IOUtils.copy(in, out);
			out.close();
			in.close();
		} else {
			throw new FileNotFoundException();
		}
	}

	@RequestMapping(path = "/image/{filename:.+}", method = RequestMethod.GET)
	public void getImageByName(HttpServletResponse response, @PathVariable(value = "filename") String filename)
			throws IOException {
		String path = "";
		if (env.getProperty("cms.file.folder") != null) {
			path = env.getProperty("cms.file.folder");
		}
		File file = new File(path + filename);
		if (file.exists()) {
			String contentType = "application/octet-stream";
			response.setContentType(contentType);
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(file);
			// copy from in to out
			IOUtils.copy(in, out);
			out.close();
			in.close();
		} else {
			throw new FileNotFoundException();
		}
	}

	@Autowired
	private CmsMapObjectService cmsMapObjectService;

	@RequestMapping(value = "/{key}", method = RequestMethod.GET)
	public CmsMapObjectDto getCmsMapByKey(@PathVariable String key) {
		return cmsMapObjectService.getCmsMapByKey(key);
	}

	@RequestMapping(value = "/{key}/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public CmsMapObjectDto getPageCmsMapByKey(@PathVariable String key, @PathVariable int pageIndex,
											  @PathVariable int pageSize) {
		return cmsMapObjectService.getPageCmsMapByKey(key, pageIndex, pageSize);
	}

	@RequestMapping(value = "/category/get-all-parent", method = RequestMethod.GET)
	public List<CmsCategoryDto> getAllParent() {
		CmsSearchDto dto = new CmsSearchDto();
		dto.setPageIndex(1);
		dto.setPageSize(Integer.MAX_VALUE);
		return cmsCategoryService.paging(dto).getContent();
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	public List<PageMenuCmsDto> getAllMenuPageCms() {
		CmsSearchDto dto = new CmsSearchDto();
		dto.setPageIndex(1);
		dto.setPageSize(Integer.MAX_VALUE);
		return pageMenuCmsService.paging(dto).getContent();
	}

	// @RequestMapping(value="/page", method = RequestMethod.GET)
	// public CmsPageDto getPanel() {
	// SearchDto dto = new SearchDto();
	// dto.setPageIndex(1);
	// dto.setPageSize(99999);
	// dto.setGetOnlyParent(true);
	// CmsPageDto list =
	// cmsPageRepository.findOneById(UUID.fromString("55bc6a4b-9228-42a6-b19f-461c8aedfdb3"));
	// return list;
	// }

	@RequestMapping(value = "/category/parentisnull/{getIsHoverArticle}", method = RequestMethod.GET)
	public List<CmsCategoryDto> getAllByParentNull(@PathVariable Boolean getIsHoverArticle) {
		return cmsCategoryService.getAllByParentNull(getIsHoverArticle);
	}

	@RequestMapping(value = "/website/getbydomain/{domainName:.+}", method = RequestMethod.GET)
	public WebsiteDto getWebsite(@PathVariable(value = "domainName") String domainName) {
		return websiteService.getWebsite(domainName);
	}

	@RequestMapping(value = "/page/{param}", method = RequestMethod.GET)
	public CmsPageDto getWebsitePage(@PathVariable(value = "param") String param) {
		return cmsPageService.getPageByTemplatePath(param);
	}

	// v3

	// lay ra menu
	@RequestMapping(value = "/get-menu-header", method = RequestMethod.GET)
	public List<MenuHeaderDto> getListMenu() {
		return cmsPublishService.getMenuHeader();
	}
	// danh muc

	@RequestMapping(value = "/get-category-by-slug", method = RequestMethod.GET)
	public List<CategoryViewDto> getCategoryBySlug(@RequestParam("slug") String slug) {
		return cmsPublishService.getCategoryBySlug(slug);
	}

	// bai viet
	@RequestMapping(value = "/get-page-article-by-slug", method = RequestMethod.POST)
	public Page<ArticleViewDto> getPageArticleBySlug(@RequestBody CmsSearchDto dto) {
		return cmsPublishService.getPageArticleBySlug(dto);
	}

	@RequestMapping(value = "/get-article-by-slug/{slug}", method = RequestMethod.GET)
	public CmsArticleDto getArticleBySlug(@PathVariable("slug") String slug) {
		return cmsPublishService.getArticleBySlug(slug);
	}

	// trang chu
	@RequestMapping(value = "/get-home-page", method = RequestMethod.GET)
	public HomePageDto getHomePage() {
		return cmsPublishService.getHomePage();
	}
}
