package com.globits.cms.service.impl;

import com.globits.core.domain.Person;
import com.globits.core.repository.PersonRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.cms.Const;
import com.globits.cms.domain.*;
import com.globits.cms.dto.*;
import com.globits.cms.repository.*;
import com.globits.cms.service.CmsArticleService;
import com.globits.cms.service.CmsCategoryService;
import com.globits.cms.util.Utils;
import com.globits.cms.util.VNCharacterUtils;
import com.globits.security.domain.User;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.*;

@Service
public class CmsArticleServiceImpl extends GenericServiceImpl<CmsArticle, UUID> implements CmsArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CmsArticleServiceImpl.class);
    @Autowired
    private CmsArticleRepsitory cmsArticleRepsitory;

    @Autowired
    private CmsArticleTypeRepository cmsArticleTypeRepository;

    @Autowired
    private CmsCategoryService cmsCategoryService;

    @Autowired
    private CmsCategoryArticleRepository cmsCategoryArticleRepository;

    @Autowired
    private CmsCategoryRepository cmsCategoryRepository;

    @Resource
    private PersonRepository personRepository;

    @Autowired
    private CmsLocationFocalPointsRepository cmsLocationFocalPointsRepository;

    @Autowired
    private CmsArticleGroupRepository articleGroupRepository;

    @Autowired
    private CmsMapObjectRepository cmsMapObjectRepository;

    @Autowired
    private WebsiteRepository websiteRepository;

    @Override
    public CmsArticleDto getArticle(UUID id) {
        if (id != null) {
            CmsArticle article = cmsArticleRepsitory.findById(id).orElse(null);
            if (article != null) {
                return new CmsArticleDto(article);
            }
        }
        return null;
    }

    @Override
    public CmsArticleDto saveArticle(CmsArticleDto dto) {
        if (dto == null) {
            return null;
        }
        CmsArticle article = null;
        boolean isNew = false;
        if (dto.getId() != null) {
            article = cmsArticleRepsitory.findById(dto.getId()).orElse(null);
        }
        if (article == null) {
            article = new CmsArticle();
            isNew = true;
        }
        CmsArticleType articleType = null;
        if (dto.getArticleType() != null && dto.getArticleType().getId() != null) {
            articleType = cmsArticleTypeRepository.findById(dto.getArticleType().getId()).orElse(null);
        }
        CmsCategory primaryCategory = null;
        if (dto.getPrimaryCategory() != null && dto.getPrimaryCategory().getId() != null) {
            primaryCategory = cmsCategoryRepository.findById(dto.getPrimaryCategory().getId()).orElse(null);
        }
        Person blogger = null;
        if (dto.getBlogger() != null && dto.getBlogger().getId() != null) {
            blogger = personRepository.findById(dto.getBlogger().getId()).orElse(null);
        }
        CmsLocationFocalPoints locationFocalPoints = null;
        if (dto.getLocationFocalPoints() != null && dto.getLocationFocalPoints().getId() != null) {
            locationFocalPoints = cmsLocationFocalPointsRepository.findById(dto.getLocationFocalPoints().getId())
                    .orElse(null);
        }
        CmsArticleGroup articleGroup = null;
        if (dto.getArticleGroup() != null && dto.getArticleGroup().getId() != null) {
            articleGroup = articleGroupRepository.findById(dto.getArticleGroup().getId()).orElse(null);
        }
        Website website = null;
        if (dto.getWebsite() != null && dto.getWebsite().getId() != null) {
            website = websiteRepository.findById(dto.getWebsite().getId()).orElse(null);
        }
        article.setContent(dto.getContent());
        article.setTitle(dto.getTitle());
        article.setSummary(dto.getSummary());
        article.setTitleImageUrl(dto.getTitleImageUrl());
        article.setPublishDate(dto.getPublishDate());
        article.setView(dto.getView());
        article.setArticleType(articleType);
        article.setSubtitle(dto.getSubtitle());
        if (dto.getSlug() != null) {
            CmsMapObject mapObjectBySlug = cmsMapObjectRepository.getCmsMapByKey(dto.getSlug().toLowerCase());
            if (isNew && mapObjectBySlug != null) {
                String slugDuplicate = VNCharacterUtils
                        .replaceAndRemoveSpace(mapObjectBySlug.getSlug() + " " + System.currentTimeMillis());
                article.setSlug(slugDuplicate);
            } else {
                article.setSlug(dto.getSlug().toLowerCase());
            }
        }
        article.setLanguage(dto.getLanguage());
        article.setPrimaryCategory(primaryCategory);
        article.setNoteAvatarImage(dto.getNoteAvatarImage());
        article.setBlogger(blogger);
        article.setRealAuthor(dto.getRealAuthor());
        article.setPlugInTheFocus(dto.getPlugInTheFocus());
        article.setPlugInTheItem(dto.getPlugInTheItem());
        article.setLocationFocalPoints(locationFocalPoints);
        article.setNotShowHome(dto.getNotShowHome());
        article.setNotShowCategory(dto.getNotShowCategory());
        article.setNotShowAds(dto.getNotShowAds());
        article.setNote(dto.getNote());
        article.setTags(dto.getTags());
        article.setSource(dto.getSource());
        article.setTypeCustomer(dto.getTypeCustomer());
        article.setTypeCustomerUrl(dto.getTypeCustomerUrl());
        article.setTypeCustomerFromDate(dto.getTypeCustomerFromDate());
        article.setTypeCustomerEndDate(dto.getTypeCustomerEndDateEvent());
        article.setTypeCustomerLocation(dto.getTypeCustomerLocation());
        article.setArticleGroup(articleGroup);
        article.setRoyalties(dto.getRoyalties());
        article.setStatus(dto.getStatus());
        article.setIsActive(dto.getIsActive());
        article.setWebsite(website);
        article.setPositionIndex(dto.getPositionIndex());
        if (dto.getCategories() != null && dto.getCategories().size() > 0) {
            Set<CmsCategoryArticle> categoryArticles = new HashSet<>();
            for (CmsCategoryArticleDto cmsCategoryArticleDto : dto.getCategories()) {
                CmsCategoryArticle categoryArticle = null;
                if (cmsCategoryArticleDto.getId() != null) {
                    categoryArticle = cmsCategoryArticleRepository.findById(cmsCategoryArticleDto.getId()).orElse(null);
                }
                if (categoryArticle == null) {
                    categoryArticle = new CmsCategoryArticle();
                }
                categoryArticle.setArticle(article);
                if (cmsCategoryArticleDto.getCategoryId() != null) {
                    CmsCategory category = cmsCategoryRepository.findById(cmsCategoryArticleDto.getCategoryId())
                            .orElse(null);
                    if (category != null) {
                        categoryArticle.setCategory(category);
                        categoryArticles.add(categoryArticle);
                    }
                }
            }
            if (article.getCategories() != null && article.getCategories().size() > 0) {
                article.getCategories().clear();
                article.getCategories().addAll(categoryArticles);
            } else {
                article.getCategories().addAll(categoryArticles);
            }
        }
        if (article.getTitle() != null) {
            CmsMapObject mapObject = article.getMapObject() != null ? article.getMapObject() : new CmsMapObject();
            String slug = VNCharacterUtils.replaceAndRemoveSpace(article.getTitle().toLowerCase());
            mapObject.setObjectType(Const.SlugTypeEnum.ARTICLE.getKey());
            CmsMapObject cmsMapObjectBySlug = cmsMapObjectRepository.getCmsMapByKey(dto.getSlug().toLowerCase());
            if (isNew && cmsMapObjectBySlug != null && cmsMapObjectBySlug.getSlug() != null) {
                String slugDuplicate = VNCharacterUtils
                        .replaceAndRemoveSpace(cmsMapObjectBySlug.getSlug() + " " + System.currentTimeMillis());
                mapObject.setSlug(slugDuplicate);
            } else {
                mapObject.setSlug(slug);
            }
            mapObject.setArticle(article);
            article.setMapObject(mapObject);
        }
        article = cmsArticleRepsitory.save(article);
        return new CmsArticleDto(article);
    }

    @Override
    public Boolean deleteArticle(UUID id) {
        if (id != null) {
            CmsArticle article = cmsArticleRepsitory.findById(id).orElse(null);
            if (article != null) {
                try {
                    if (article.getCategories() != null && article.getCategories().size() > 0) {
                        for (CmsCategoryArticle categoryArticle : article.getCategories()) {
                            cmsCategoryArticleRepository.deleteById(categoryArticle.getId());
                        }
                    }
                    cmsMapObjectRepository.deleteById(article.getMapObject().getId());
                    cmsArticleRepsitory.deleteById(id);
                    return true;
                } catch (Exception e) {
                    LOGGER.info("Error deleting article");
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public Page<CmsArticleDto> paging(CmsSearchDto dto) {
        if (dto == null) {
            return null;
        }
        int pageIndex = dto.getPageIndex() != null ? dto.getPageIndex() : 0;
        pageIndex = pageIndex > 0 ? pageIndex - 1 : 0;
        int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10;
        String hql = "select new com.globits.cms.dto.CmsArticleDto(e) from CmsArticle e ";
        String hqlCount = "select count(e) from CmsArticle e ";
        String whereClause = " where (1=1) ";
        String orderBy = " order by e.modifyDate ";
        if (dto.getCategoryId() != null) {
            whereClause += " and (e.primaryCategory.id = :categoryId or " +
                    " e.id in (select csa.article.id from CmsCategoryArticle csa where csa.category.id =: categoryId))";
        }
        if (dto.getFromDate() != null) {
            whereClause += "and (e.publishDate >= :fromDate) ";
        }
        if (dto.getToDate() != null) {
            whereClause += "and (e.publishDate <= :toDate) ";
        }
        if (dto.getStatus() != null) {
            whereClause += " and (e.status = :status) ";
        }
        if (dto.getWebsiteId() != null) {
            whereClause += " and (e.website.id = :websiteId) ";
        }
        if (dto.getTextSearch() != null) {
            whereClause += " and (e.title like :textSearch or e.slug like :textSearch) ";
        }
        if (dto.getIsActive() != null) {
            whereClause += " and (e.isActive = :isActive) ";
        }
        if (dto.getShowOnSlider() != null) {
            whereClause += " and (e.plugInTheItem = :showOnSlider) ";
        }
        if (dto.getShowOnHomePage() != null) {
            whereClause += " and (e.plugInTheFocus = :showOnHomePage) ";
        }
        hql += whereClause + orderBy;
        hqlCount += whereClause;
        Query query = manager.createQuery(hql, CmsArticleDto.class);
        Query queryCount = manager.createQuery(hqlCount);
        if (dto.getCategoryId() != null) {
            query.setParameter("categoryId", dto.getCategoryId());
            queryCount.setParameter("categoryId", dto.getCategoryId());
        }
        if (dto.getFromDate() != null) {
            query.setParameter("fromDate", dto.getFromDate());
            queryCount.setParameter("fromDate", dto.getFromDate());
        }
        if (dto.getToDate() != null) {
            query.setParameter("toDate", dto.getToDate());
            queryCount.setParameter("toDate", dto.getToDate());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
            queryCount.setParameter("toDate", dto.getStatus());
        }
        if (dto.getWebsiteId() != null) {
            query.setParameter("websiteId", dto.getWebsiteId());
            queryCount.setParameter("websiteId", dto.getWebsiteId());
        }
        if (dto.getTextSearch() != null) {
            query.setParameter("textSearch", '%' + dto.getTextSearch() + '%');
            queryCount.setParameter("textSearch", '%' + dto.getTextSearch() + '%');
        }
        if (dto.getIsActive() != null) {
            query.setParameter("isActive", dto.getIsActive());
            queryCount.setParameter("isActive", dto.getIsActive());
        }
        if (dto.getShowOnSlider() != null) {
            query.setParameter("showOnSlider", dto.getShowOnSlider());
            queryCount.setParameter("showOnSlider", dto.getShowOnSlider());
        }
        if (dto.getShowOnHomePage() != null) {
            query.setParameter("showOnHomePage", dto.getShowOnHomePage());
            queryCount.setParameter("showOnHomePage", dto.getShowOnHomePage());
        }
        List<CmsArticleDto> result = query.getResultList();
        long count = (long) queryCount.getSingleResult();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public Page<CmsArticleDto> getPageCmsArticleDto(int pageIndex, int pageSize) {
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return cmsArticleRepsitory.getPageCmsArticleDto(pageable);
    }

    @Override
    public CmsArticleDto saveCmsArticle(CmsArticleDto dto, UUID id) {
        if (dto != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User modifiedUser = null;
            LocalDateTime currentDate = LocalDateTime.now();
            String currentUserName = "Unknown User";
            if (authentication != null) {
                modifiedUser = (User) authentication.getPrincipal();
                currentUserName = modifiedUser.getUsername();
            }

            CmsArticle cmsArticle = null;
            if (id != null)
                cmsArticle = cmsArticleRepsitory.getOne(id);
            else if (dto.getId() != null)
                cmsArticle = cmsArticleRepsitory.getOne(dto.getId());

            if (cmsArticle == null)
                cmsArticle = new CmsArticle();

            cmsArticle.setStatus(dto.getStatus());
            cmsArticle.setModifyDate(currentDate);
            cmsArticle.setPublishDate(dto.getPublishDate());
            cmsArticle.setContent(dto.getContent());
            cmsArticle.setSummary(dto.getSummary());
            cmsArticle.setTitle(dto.getTitle());
            cmsArticle.setTitleImageUrl(dto.getTitleImageUrl());
            cmsArticle.setSubtitle(dto.getSubtitle());
            cmsArticle.setSlug(dto.getSlug());
            cmsArticle.setNotShowAds(dto.getNotShowAds());
            cmsArticle.setNotShowCategory(dto.getNotShowCategory());
            cmsArticle.setNotShowHome(dto.getNotShowHome());
            cmsArticle.setNote(dto.getNote());
            cmsArticle.setNoteAvatarImage(dto.getNoteAvatarImage());
            cmsArticle.setRealAuthor(dto.getRealAuthor());
            cmsArticle.setPlugInTheFocus(dto.getPlugInTheFocus());
            cmsArticle.setPlugInTheItem(dto.getPlugInTheItem());
            cmsArticle.setTags(dto.getTags());
            cmsArticle.setSource(dto.getSource());
            cmsArticle.setRoyalties(dto.getRoyalties());
            cmsArticle.setLanguage(dto.getLanguage());
            cmsArticle.setIsActive(dto.getIsActive());
            cmsArticle.setTypeCustomer(dto.getTypeCustomer());
            cmsArticle.setTypeCustomerUrl(dto.getTypeCustomerUrl());
            cmsArticle.setTypeCustomerFromDate(dto.getTypeCustomerFromDate());
            cmsArticle.setTypeCustomerEndDate(dto.getTypeCustomerEndDateEvent());
            cmsArticle.setTypeCustomerLocation(dto.getTypeCustomerLocation());
            cmsArticle.setView(dto.getView());
            cmsArticle.setPositionIndex(dto.getPositionIndex());

            Person blogger = null;
            CmsCategory primaryCategory = null;
            CmsArticleType articleType = null;
            CmsLocationFocalPoints locationFocalPoints = null;
            CmsArticleGroup articleGroup = null;
            Website website = null;

            if (dto.getWebsite() != null)
                website = websiteRepository.getOne(dto.getWebsite().getId());

            if (dto.getBlogger() != null && dto.getBlogger().getId() != null)
                blogger = personRepository.getOne(dto.getBlogger().getId());
            if (dto.getPrimaryCategory() != null && dto.getPrimaryCategory().getId() != null)
                primaryCategory = cmsCategoryRepository.getOne(dto.getPrimaryCategory().getId());
            if (dto.getPrimaryCategoryCode() != null && dto.getPrimaryCategoryCode().length() > 0) {
                List<CmsCategory> listFind = cmsCategoryRepository.findSlug(dto.getPrimaryCategoryCode());
                if (listFind != null && listFind.size() > 0)
                    primaryCategory = listFind.get(0);
            }
            if (primaryCategory == null || primaryCategory.getId() == null)
                return null;

            if (dto.getArticleType() != null && dto.getArticleType().getId() != null)
                articleType = cmsArticleTypeRepository.getOne(dto.getArticleType().getId());

            if (dto.getLocationFocalPoints() != null && dto.getLocationFocalPoints().getId() != null)
                locationFocalPoints = cmsLocationFocalPointsRepository.getOne(dto.getLocationFocalPoints().getId());

            if (dto.getArticleGroup() != null && dto.getArticleGroup().getId() != null)
                articleGroup = articleGroupRepository.getOne(dto.getArticleGroup().getId());
            cmsArticle.setWebsite(website);
            cmsArticle.setBlogger(blogger);
            cmsArticle.setPrimaryCategory(primaryCategory);
            cmsArticle.setArticleType(articleType);
            cmsArticle.setLocationFocalPoints(locationFocalPoints);
            cmsArticle.setArticleGroup(articleGroup);

            if (dto.getCategories() != null) {
                Set<CmsCategoryArticle> categoryArticles = new HashSet<>();
                for (CmsCategoryArticleDto cmsCategoryArticleDto : dto.getCategories())
                    if (cmsCategoryArticleDto != null) {
                        CmsCategoryArticle categoryArticle = null;
                        if (cmsCategoryArticleDto.getCategory() != null
                                && cmsCategoryArticleDto.getCategory().getId() != null) {
                            UUID articleId = cmsCategoryArticleDto.getArticle() != null
                                    && cmsCategoryArticleDto.getArticle().getId() != null
                                            ? cmsCategoryArticleDto.getArticle().getId()
                                            : null;
                            categoryArticle = cmsCategoryArticleRepository.findByCategoryIdAndArticleId(
                                    cmsCategoryArticleDto.getCategory().getId(), articleId);
                        }
                        if (categoryArticle == null) {
                            categoryArticle = new CmsCategoryArticle();
                            categoryArticle.setCreateDate(currentDate);
                            categoryArticle.setCreatedBy(currentUserName);
                        }
                        categoryArticle.setArticle(cmsArticle);
                        CmsCategory category = null;
                        category = cmsCategoryService.findById(cmsCategoryArticleDto.getCategory().getId());
                        if (category != null) {
                            categoryArticle.setCategory(category);
                            categoryArticles.add(categoryArticle);
                        }
                    }
                if (cmsArticle.getCategories() == null)
                    cmsArticle.setCategories(new HashSet<CmsCategoryArticle>());
                cmsArticle.getCategories().clear();
                cmsArticle.getCategories().addAll(categoryArticles);
            }
            if (cmsArticle.getTitle() != null) {
                CmsMapObject mapObject = cmsArticle.getMapObject();
                if (cmsArticle.getMapObject() == null)
                    mapObject = new CmsMapObject();
                String slug = VNCharacterUtils
                        .replaceAndRemoveSpace(cmsArticle.getTitle().replace("/", "").replace(":", ""));
                Long countSlug = 0L;
                Long countSlugArticle = cmsArticleRepsitory.countBySlug(slug, id);
                Long countSlugMapObject = cmsMapObjectRepository.countBySlug(slug, mapObject.getId());
                if (countSlugArticle > countSlugMapObject)
                    countSlug = countSlugArticle;
                else
                    countSlug = countSlugMapObject;
                while (countSlug > 0) {
                    slug = slug + "(" + countSlug + ")";
                    countSlugArticle = cmsArticleRepsitory.countBySlug(slug, id);
                    countSlugMapObject = cmsMapObjectRepository.countBySlug(slug, mapObject.getId());
                    if (countSlugArticle > countSlugMapObject)
                        countSlug = countSlugArticle;
                    else
                        countSlug = countSlugMapObject;
                }
                cmsArticle.setSlug(slug);
                mapObject.setSlug(slug);
                mapObject.setArticle(cmsArticle);
                mapObject.setObjectType(Const.ObjectMapType.ARTICLE.getValue());
                cmsArticle.setMapObject(mapObject);
            }
            try {
                cmsArticle = cmsArticleRepsitory.save(cmsArticle);

                // if (dto.getPlugInTheFocus()) {
                // primaryCategory.setHeaderArticle(cmsArticle);
                // cmsCategoryRepository.save(primaryCategory);
                // }

                return new CmsArticleDto(cmsArticle);
            } catch (Exception e) {
                LOGGER.error("Failure Save cmsArticle!", e);
            }
        }
        return null;
    }

    @Override
    public List<CmsArticleDto> saveImportCmsArticleDto(List<CmsArticleDto> list) {
        List<CmsArticleDto> ret = new ArrayList<>();
        if (list != null && list.size() > 0)
            for (CmsArticleDto cmsArticleDto : list)
                if (cmsArticleDto != null)
                    try {
                        cmsArticleDto = saveCmsArticle(cmsArticleDto, null);
                        if (cmsArticleDto != null)
                            ret.add(cmsArticleDto);
                        // System.out.println("OK:"+cmsArticleDto.getSlug());
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: handle exception
                        System.out.println("ERR:" + cmsArticleDto.getTitle());
                    }
        return ret;
    }

    @Override
    public Boolean deleteCmsArticleById(UUID id) {
        if (id != null) {
            CmsArticle entity = cmsArticleRepsitory.getOne(id);
            if (entity != null) {
                List<UUID> list = cmsCategoryArticleRepository.findByCategoryIdAndArticle(entity.getId());
                try {
                    for (UUID cmsCategoryArticleId : list)
                        cmsCategoryArticleRepository.deleteById(cmsCategoryArticleId);
                    cmsMapObjectRepository.deleteById(entity.getMapObject().getId());
                    cmsArticleRepsitory.deleteById(id);
                    return true;
                } catch (Exception e) {
                    LOGGER.error("Delete article have id=" + id + " is failured", e);
                    return false;
                }
            }
        }
        return null;
    }

    @Override
    public Page<CmsArticleDto> searchArticleServiceBySearchDto(SearchDto search) {
        int pageIndex = search.getPageIndex();
        int pageSize = search.getPageSize();
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String sql = "select distinct new com.globits.cms.dto.CmsArticleDto(c.article) from CmsCategoryArticle c where 1=1 ";
        String sqlCount = "select distinct count(*) from CmsCategoryArticle c where 1=1 ";
        String whereHasCategoryId = " and c.category is not null and c.category.id = :categoryId";
        String whereHasTitle = " and c.article.title like :title";
        String whereWebsite = "and c.website.id =: websiteId";
        boolean isCategoryId = search.getCategoryId() != null ? true : false;
        boolean isTitle = !Utils.isBlank(search.getTitle());

        if (isCategoryId) {
            sql += whereHasCategoryId;
            sqlCount += whereHasCategoryId;
        }

        if (isTitle) {
            sql += whereHasTitle;
            sqlCount += whereHasTitle;
        }
        if (search.getWebsiteId() != null) {
            sql += whereWebsite;
            sqlCount += whereWebsite;
        }
        sql += " order by c.article.id desc";

        Query query = manager.createQuery(sql, CmsArticleDto.class);
        Query queryCount = manager.createQuery(sqlCount);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageIndex * pageSize);

        if (search.getWebsiteId() != null) {
            query.setParameter("websiteId", search.getWebsiteId());
            queryCount.setParameter("websiteId", search.getWebsiteId());
        }

        if (isCategoryId) {
            query.setParameter("categoryId", search.getCategoryId());
            queryCount.setParameter("categoryId", search.getCategoryId());
        }

        if (isTitle) {
            query.setParameter("title", "%" + search.getTitle() + "%");
            queryCount.setParameter("title", "%" + search.getTitle() + "%");
        }

        List<CmsArticleDto> content = query.getResultList();
        Object object = queryCount.getSingleResult();
        Long total = 0L;
        if (object != null)
            total = (Long) object;
        // if (content.size() > 0) {
        // Collections.sort(content, new Comparator<CmsArticleDto>() {
        // @Override
        // public int compare(final CmsArticleDto object1, final CmsArticleDto object2)
        // {
        // return object2.getPublishDate().compareTo(object1.getPublishDate());
        // }
        // });
        // }
        Page<CmsArticleDto> page = new PageImpl<>(content, pageable, total);
        return page;
    }

    @Override
    public void seeNews(UUID id) {
        if (id != null)
            try {
                CmsArticle cmsArticle = cmsArticleRepsitory.getOne(id);
                if (cmsArticle != null) {
                    if (cmsArticle.getView() == null || cmsArticle.getView() <= 0)
                        cmsArticle.setView(0);
                    cmsArticle.setView(cmsArticle.getView() + 1);
                    cmsArticleRepsitory.save(cmsArticle);
                }
            } catch (Exception e) {
                LOGGER.error("Error up view in news=" + id + " is failured", e);
            }
    }

    @Override
    public Page<CmsArticleDto> getPageCmsArticleOrderByViewDESC(int pageIndex, int pageSize) {
        String sqlCount = "select count(news) from CmsArticle as news where (1=1)";
        String sql = "select new com.globits.cms.dto.CmsArticleDto(news, false) from CmsArticle as news where (1=1) ORDER BY news.view DESC ";

        Query q = manager.createQuery(sql, CmsArticleDto.class);
        Query qCount = manager.createQuery(sqlCount);

        int startPosition = (pageIndex - 1) * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<CmsArticleDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<CmsArticleDto> result = new PageImpl<>(entities, pageable, count);

        return result;
    }

    @Override
    public Page<CmsArticleDto> searchArticleServiceBySearchDtoByPublishAPI(SearchDto search) {
        int pageIndex = search.getPageIndex();
        int pageSize = search.getPageSize();
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        // String sql = "select distinct new
        // com.globits.cms.dto.CmsArticleDto(c.article) from CmsCategoryArticle c where
        // 1=1 ";
        // String sqlCount = "select distinct count(*) from CmsCategoryArticle c where
        // 1=1 ";
        // String whereHasCategoryId = " and c.category is not null and c.category.id =
        // :categoryId";
        // String whereHasTitle = " and c.article.title like :title";

        String sql = "select distinct new com.globits.cms.dto.CmsArticleDto(a) from CmsCategoryArticle c right join c.article a where 1=1 and a.status!= 1";
        String sqlCount = "select distinct count(*) from CmsCategoryArticle c right join c.article a where 1=1 ";
        String whereHasCategoryId = "and (( c.category is not null and c.category.id = :categoryId) or (a.primaryCategory.id=:categoryId))";
        String whereHasListCategoryId = "and (( c.category is not null and c.category.id in  (:listCategoryId)) or (a.primaryCategory.id in  (:listCategoryId) ))";
        String whereHasTitle = " and (a.title like :title)";
        String whereHasIsActive = " and (a.isActive = :isActive)";

        boolean isCategoryId = search.getCategoryId() != null ? true : false;
        boolean isTitle = !Utils.isBlank(search.getTitle());
        List<UUID> listCategoryId = new ArrayList<>();
        if (isCategoryId) {
            sql += whereHasCategoryId;
            sqlCount += whereHasCategoryId;
            // }
        }

        if (isTitle) {
            sql += whereHasTitle;
            sqlCount += whereHasTitle;
        }
        if (search.getIsActive() != null) {
            sql += whereHasIsActive;
            sqlCount += whereHasIsActive;
        }
        sql += " order by a.publishDate desc";

        Query query = manager.createQuery(sql, CmsArticleDto.class);
        Query queryCount = manager.createQuery(sqlCount);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageIndex * pageSize);

        if (isCategoryId) {
            query.setParameter("categoryId", search.getCategoryId());
            queryCount.setParameter("categoryId", search.getCategoryId());
            // query.setParameter("listCategoryId", listCategoryId);
            // queryCount.setParameter("listCategoryId", listCategoryId);
            // } else {
            // if (search.getCategoryId() != null) {
            // query.setParameter("categoryId", search.getCategoryId());
            // queryCount.setParameter("categoryId", search.getCategoryId());
            // }
        }

        if (isTitle) {
            query.setParameter("title", "%" + search.getTitle() + "%");
            queryCount.setParameter("title", "%" + search.getTitle() + "%");
        }

        if (search.getIsActive() != null) {
            query.setParameter("isActive", search.getIsActive());
            queryCount.setParameter("isActive", search.getIsActive());
        }
        List<CmsArticleDto> content = query.getResultList();
        Object object = queryCount.getSingleResult();
        Long total = 0L;
        if (object != null)
            total = (Long) object;
        Page<CmsArticleDto> page = new PageImpl<>(content, pageable, total);
        return page;
    }

    @Override
    public Page<CmsArticleDto> searchByDto(SearchDto search) {
        int pageIndex = search.getPageIndex();
        int pageSize = search.getPageSize();
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;
        String whereClause = "";
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String sql = "select distinct new com.globits.cms.dto.CmsArticleDto(c) from CmsArticle c where (1=1) ";
        String sqlCount = "select distinct count(*) from CmsArticle c where (1=1) ";
        String whereHasCategoryId = " and ( c.primaryCategory.id =:categoryId or c.id in (select csa.article.id from CmsCategoryArticle csa where csa.category.id =:categoryId) )";
        String whereDate = " and (c.publishDate between :since and :toDate) ";
        String whereStatus = " and (c.status like :status) ";
        String whereWebsite = " and c.website.id =: websiteId ";

        if (search.getKeyword() != null && !search.getKeyword().isEmpty() && StringUtils.hasText(search.getKeyword())) {
            whereClause += " AND (c.title LIKE :keyword OR c.slug LIKE :keyword)";
            sql += whereClause;
            sqlCount += whereClause;
        }

        boolean isCategoryId = search.getCategoryId() != null ? true : false;

        if (isCategoryId) {
            sql += whereHasCategoryId;
            sqlCount += whereHasCategoryId;
        }
        if (search.getStatus() != null) {
            sql += whereStatus;
            sqlCount += whereStatus;
        }
        if (search.getSince() != null && search.getToDate() != null) {
            sql += whereDate;
            sqlCount += whereDate;
        }
        if (search.getWebsiteId() != null) {
            sql += whereWebsite;
            sqlCount += whereWebsite;
        }
        sql += " order by c.modifyDate desc";

        Query query = manager.createQuery(sql, CmsArticleDto.class);
        Query queryCount = manager.createQuery(sqlCount);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageIndex * pageSize);

        if (search.getKeyword() != null && !search.getKeyword().isEmpty() && StringUtils.hasText(search.getKeyword())) {
            query.setParameter("keyword", "%" + search.getKeyword() + "%");
            queryCount.setParameter("keyword", "%" + search.getKeyword() + "%");
        }

        if (search.getWebsiteId() != null) {
            query.setParameter("websiteId", search.getWebsiteId());
            queryCount.setParameter("websiteId", search.getWebsiteId());
        }

        if (isCategoryId) {
            query.setParameter("categoryId", search.getCategoryId());
            queryCount.setParameter("categoryId", search.getCategoryId());
        }

        if (search.getSince() != null && search.getToDate() != null) {

            Date a = search.getSince();
            Date b = search.getToDate();
            query.setParameter("since", a);
            query.setParameter("toDate", b);

            queryCount.setParameter("since", a);
            queryCount.setParameter("toDate", b);
        }

        if (search.getStatus() != null) {
            query.setParameter("status", search.getStatus());
            queryCount.setParameter("status", search.getStatus());
        }

        @SuppressWarnings("unchecked")
        List<CmsArticleDto> content = query.getResultList();
        Object object = queryCount.getSingleResult();
        Long total = 0L;
        if (object != null)
            total = (Long) object;
        Page<CmsArticleDto> page = new PageImpl<>(content, pageable, total);
        return page;
    }

    @Override
    public Page<CmsArticleDto> searchByName(SearchDto search) {
        int pageIndex = search.getPageIndex();
        int pageSize = search.getPageSize();
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;
        String whereClause = "";
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        String sql = "select distinct new com.globits.cms.dto.CmsArticleDto(c) from CmsArticle c where 1 = 1 ";
        String sqlCount = "select distinct count(*) from CmsArticle c where 1=1 ";
        if (search.getWebsiteId() != null) {
            whereClause += " and (c.website.id =: websiteId ) ";
        }

        if (search.getKeyword() != null && !search.getKeyword().isEmpty() && StringUtils.hasText(search.getKeyword())) {
            whereClause += " AND c.title LIKE :keyword OR c.slug LIKE :keyword";
        }

        sql += whereClause;
        sqlCount += whereClause;
        // boolean isTitle = !Utils.isBlank(search.getTitle());

        // sql += whereWebsite;
        // sqlCount += whereWebsite;
        sql += " order by c.modifyDate desc";

        Query query = manager.createQuery(sql, CmsArticleDto.class);
        Query queryCount = manager.createQuery(sqlCount);
        query.setMaxResults(pageSize);
        query.setFirstResult(pageIndex * pageSize);
        // System.out.println(sql);
        if (search.getKeyword() != null && !search.getKeyword().isEmpty() && StringUtils.hasText(search.getKeyword())) {
            query.setParameter("keyword", "%" + search.getKeyword() + "%");
            queryCount.setParameter("keyword", "%" + search.getKeyword() + "%");
        }

        if (search.getWebsiteId() != null) {
            query.setParameter("websiteId", search.getWebsiteId());
            queryCount.setParameter("websiteId", search.getWebsiteId());
        }

        @SuppressWarnings("unchecked")
        List<CmsArticleDto> content = query.getResultList();
        Object object = queryCount.getSingleResult();
        Long total = 0L;
        if (object != null)
            total = (Long) object;
        Page<CmsArticleDto> page = new PageImpl<>(content, pageable, total);

        return page;
    }

    @Override
    public Boolean checkSlug(UUID id, String title) {
        if (title != null && title.length() > 0) {
            String slugCheck = VNCharacterUtils
                    .replaceAndRemoveSpace(title.replace("/", "").replace(":", ""));
            CmsMapObject entity = cmsMapObjectRepository.getCmsMapByKey(slugCheck);
            if (entity != null) {
                if (id != null)
                    return false;
                return true;
            }
            return false;
        }
        return null;
    }

    @Override
    public Page<CmsArticleDto> getPageCmsArticleByCategory(UUID categoryId, int pageIndex, int pageSize) {
        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return cmsArticleRepsitory.getPageCmsArticleByCategory(categoryId, pageable);
    }

    public Page<CmsArticleDto> getPageCmsArticleByCategoryAndSubCategory(UUID categoryId, int pageIndex, int pageSize) {
        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return cmsArticleRepsitory.getPageCmsArticleByCategoryAndSubCategory(categoryId, pageable);
    }

    public Page<CmsArticleDto> searchByPageArticleByPrimaryCategory(UUID categoryId, int pageIndex, int pageSize) {

        if (pageIndex > 0)
            pageIndex--;
        else
            pageIndex = 0;

        String whereClause = "";

        // String orderBy = " ORDER BY entity.name DESC";
        // if(dto.getOrderBy() != null &&
        // StringUtils.hasLength(dto.getOrderBy().toString()))
        // orderBy = " ORDER BY entity." + dto.getOrderBy() + " ASC";

        String sqlCount = "SELECT COUNT(entity.id) FROM CmsArticle AS entity WHERE (1=1) ";
        String sql = "SELECT  new com.globits.cms.dto.CmsArticleDto(entity) FROM CmsArticle AS entity WHERE (1=1) ";

        if (categoryId != null)
            whereClause += " AND entity.primaryCategory.id= :categoryIdParent order by entity.publishDate desc";

        sql += whereClause;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, CmsArticleDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (categoryId != null) {
            q.setParameter("categoryIdParent", categoryId);
            qCount.setParameter("categoryIdParent", categoryId);
        }
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<CmsArticleDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<CmsArticleDto> result = new PageImpl<>(entities, pageable, count);
        return result;
    }

    @Override
    public List<CmsCategoryDto> getListCategoryAndArticleOfCategory(UUID categoryId, int pageIndex, int pageSize) {
        List<UUID> list = cmsCategoryRepository.findListIDCategoryByParentId(categoryId);
        List<CmsCategoryDto> result = new ArrayList<>();

        for (UUID uuid : list) {
            List<CmsArticleDto> dto = new ArrayList<>();
            CmsCategoryDto entity = new CmsCategoryDto(
                    cmsCategoryRepository.findById(uuid).orElseGet(CmsCategory::new));
            List<CmsArticleDto> listCA = searchByPageArticleByPrimaryCategory(uuid, pageIndex, pageSize).getContent();
            for (CmsArticleDto cmsArticleDto : listCA) {
                dto.add(cmsArticleDto);
            }
            Page<CmsArticleDto> catArticles = this.getPageCmsArticleByCategoryAndSubCategory(uuid, 1,
                    10);// Tạm thời lấy về 10
            if (catArticles.getSize() > 0) {
                for (CmsArticleDto cmsCategoryDto : catArticles.getContent()) {
                    dto.add(cmsCategoryDto);
                }
            }

            entity.setListArticles(dto);
            result.add(entity);
        }

        return result;
    }

}
