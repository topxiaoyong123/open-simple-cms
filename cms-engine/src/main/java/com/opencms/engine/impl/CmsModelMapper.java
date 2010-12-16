package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.EngineInfo;
import com.opencms.engine.model.Site;
import com.opencms.util.CmsType;
import com.opencms.util.mapper.BeanMapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
@Component("cmsMapper")
public class CmsModelMapper implements ModelMapper {

    @Resource(name = "beanMapperHelper")
    private BeanMapperHelper beanMapperHelper;

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    @Override
    public Site map(SiteBean siteBean) {
        Site site = (Site)beanMapperHelper.map(siteBean, Site.class);
        return site;
    }

    @Override
    public Category map(CategoryBean categoryBean) {
        Category category = (Category)beanMapperHelper.map(categoryBean, Category.class);
        return category;
    }

    @Override
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

    public Category map(CategoryBean categoryBean, int page, int pagesize){
        Category category = (Category)beanMapperHelper.map(categoryBean, Category.class);
        return category;
    }

    @Override
    public Content map(ContentBean contentBean) {
        Content content = (Content)beanMapperHelper.map(contentBean, Content.class);
        return content;
    }

    @Override
    public String getSiteURL(SiteBean siteBean) {
        if(siteBean.getUrl() != null && !"".equals(siteBean.getUrl())){
            return siteBean.getUrl();
        }
        return "{contextPath}/site/" + siteBean.getName() + "/index.html";
    }

    @Override
    public String getCategoryURL(CategoryBean categoryBean) {
        if(categoryBean.getUrl() != null && !"".equals(categoryBean.getUrl())){
            return categoryBean.getUrl();
        }
        return "{contextPath}/category/" + categoryBean.getId() + ".html";
    }

    @Override
    public String getContentURL(ContentBean contentBean) {
        if(contentBean.getUrl() != null && !"".equals(contentBean.getUrl())){
            return contentBean.getUrl();
        }
        return "{contextPath}/content/" + contentBean.getId() + ".html";
    }
}
