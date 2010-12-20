package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.bean.field.ContentField;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.*;
import com.opencms.util.CmsType;
import com.opencms.util.CmsUtils;
import com.opencms.util.ContextThreadLocal;
import com.opencms.util.common.page.PageBean;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
@Component("appMapper")
public class AppModelMapper implements ModelMapper {

    private static final Logger logger = LoggerFactory
			.getLogger(AppModelMapper.class);

    @Resource(name = "cmsUtils")
    private CmsUtils cmsUtils;

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    @Transactional(readOnly = true)
    public Object map(EngineInfo info) {
        if(CmsType.SITE == info.getType()){
            return map(cmsManager.getSiteService().getSiteById(info.getId()));
        } else if(CmsType.CATEGORY == info.getType()){
            return map(cmsManager.getCategoryService().getCategoryById(info.getId()), info.getPage(), info.getPageSize());
        } else if(CmsType.CONTENT == info.getType()){
            return map(cmsManager.getContentService().getContentById(info.getId()));
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Site map(SiteBean siteBean) {
        Site site = (Site)cmsUtils.getBeanMapperHelper().simpleMap(siteBean, Site.class);
        site.setUrl(getSiteURL(siteBean));
        return site;
    }

    @Transactional(readOnly = true)
    public Category map(CategoryBean categoryBean) {
        Category category = (Category)cmsUtils.getBeanMapperHelper().simpleMap(categoryBean, Category.class);
        category.setUrl(getCategoryURL(categoryBean));
        return category;
    }

    @Transactional(readOnly = true)
    public Category map(CategoryBean categoryBean, int page, int pagesize){
        Category category = map(categoryBean);
        category.setPage(new PageBean(pagesize, page, (int)cmsManager.getContentService().getCountByCategoryId(categoryBean.getId(), ContentField._STATE_PUBLISHED)));
        return category;
    }

    @Transactional(readOnly = true)
    public Content map(ContentBean contentBean) {
        Content content = (Content)cmsUtils.getBeanMapperHelper().simpleMap(contentBean, Content.class);
        content.setUrl(getContentURL(contentBean));
        return content;
    }

    public List<Content> mapContents(List<ContentBean> contentBeans) {
        List<Content> list = new ArrayList<Content>();
        for(ContentBean contentBean : contentBeans){
            Content content = simpleMap(contentBean);
            content.setUrl(getContentURL(contentBean));
            list.add(content);
        }
        return list;
    }

    private Content simpleMap(ContentBean contentBean){
        Content content = new Content();
        content.setId(contentBean.getId());
        content.setTitle(contentBean.getTitle());
        content.setKeywords(contentBean.getKeywords());
        content.setUrl(contentBean.getUrl());
        content.setDescription(contentBean.getDescription());
        content.setNo(contentBean.getNo());
        content.setState(contentBean.getState());
        content.setTop(contentBean.getTop());
        content.setType(contentBean.getType());
        content.setSource(contentBean.getSource());
        content.setTemplate(contentBean.getTemplate());
        content.setAuthor(contentBean.getAuthor());
        content.setCreationDate(contentBean.getCreationDate());
        content.setModificationDate(contentBean.getModificationDate());
        return content;
    }

    public String getSiteURL(SiteBean siteBean) {
        if(siteBean.getUrl() != null && !"".equals(siteBean.getUrl())){
            return siteBean.getUrl();
        }
        return ContextThreadLocal.getRequest().getContextPath() + "/site/" + siteBean.getName() + "/index.html";
    }

    public String getCategoryURL(CategoryBean categoryBean) {
        if(categoryBean.getUrl() != null && !"".equals(categoryBean.getUrl())){
            return categoryBean.getUrl();
        }
        return ContextThreadLocal.getRequest().getContextPath() + "/category" + "/1/" + PageBean.DEFAULT_SIZE + "/" + categoryBean.getId() + ".html";
    }

    public String getCategoryURL(CategoryBean categoryBean, int page, int pageSize) {
        return ContextThreadLocal.getRequest().getContextPath() + "/category" + "/" + page + "/" + pageSize + "/" + categoryBean.getId() + ".html";
    }

    public String getContentURL(ContentBean contentBean) {
        if(contentBean.getUrl() != null && !"".equals(contentBean.getUrl())){
            return contentBean.getUrl();
        }
        return ContextThreadLocal.getRequest().getContextPath() + "/content/" + contentBean.getId() + ".html";
    }

}
