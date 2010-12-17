package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.*;
import com.opencms.util.CmsType;
import com.opencms.util.ContextThreadLocal;
import com.opencms.util.mapper.BeanMapperHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource(name = "beanMapperHelper")
    private BeanMapperHelper beanMapperHelper;

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    @Override
    public Site map(SiteBean siteBean) {
        Site site = (Site)beanMapperHelper.map(siteBean, Site.class);
        site.setMenu(getMenu(siteBean));
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

    private Menu getMenu(SiteBean siteBean){
        logger.debug("设置菜单...");
        Menu menu = new Menu();
		Item item = new Item();
		item.setName(siteBean.getName());
		item.setUrl(getSiteURL(siteBean));
		item.setTitle(siteBean.getTitle());
        item.setId(siteBean.getId());
		menu.setItem(item);
        List<CategoryBean> categoryBeans = cmsManager.getCategoryService().getMenuBySiteId(siteBean.getId());
        for(CategoryBean categoryBean : categoryBeans){
            menu.addMenu(getMenu(categoryBean));
        }
        return menu;
    }

    private Menu getMenu(CategoryBean categoryBean){
        Menu menu = new Menu();
		Item item = new Item();
		item.setName(categoryBean.getName());
		item.setUrl(getCategoryURL(categoryBean));
		item.setTitle(categoryBean.getTitle());
        item.setId(categoryBean.getId());
		menu.setItem(item);
        List<CategoryBean> categoryBeans = cmsManager.getCategoryService().getMenuByParentId(categoryBean.getId());
        for(CategoryBean category : categoryBeans){
             menu.addMenu(getMenu(category));
        }
        return menu;
    }

    @Override
    public String getSiteURL(SiteBean siteBean) {
        if(siteBean.getUrl() != null && !"".equals(siteBean.getUrl())){
            return siteBean.getUrl();
        }
        return ContextThreadLocal.getRequest().getContextPath() + "/site/" + siteBean.getName() + "/index.html";
    }

    @Override
    public String getCategoryURL(CategoryBean categoryBean) {
        if(categoryBean.getUrl() != null && !"".equals(categoryBean.getUrl())){
            return categoryBean.getUrl();
        }
        return ContextThreadLocal.getRequest().getContextPath() + "/category/" + categoryBean.getId() + ".html";
    }

    @Override
    public String getContentURL(ContentBean contentBean) {
        if(contentBean.getUrl() != null && !"".equals(contentBean.getUrl())){
            return contentBean.getUrl();
        }
        return ContextThreadLocal.getRequest().getContextPath() + "/content/" + contentBean.getId() + ".html";
    }
}
