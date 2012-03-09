package com.opencms.engine.publish.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.bean.field.ContentField;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.EngineUtil;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.ContentModel;
import com.opencms.engine.model.Item;
import com.opencms.engine.model.Menu;
import com.opencms.engine.util.PathUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-20
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
@Component
@Transactional(readOnly = true)
public class PublishEngineUtil implements EngineUtil {

    private static final Logger logger = LoggerFactory.getLogger(PublishEngineUtil.class);

    @Resource
    private CmsManager cmsManager;

    @Resource
	private ModelMapper mapper;
    
    @Resource
    private PathUtils pathUtils;

    public CmsManager getCmsManager() {
        return cmsManager;
    }

    public ModelMapper getMapper() {
        return mapper;
    }
    
    public PathUtils getPathUtils() {
    	return pathUtils;
    }

    public Menu getSiteMenu(Long siteId){
        SiteBean siteBean = cmsManager.getSiteService().getSiteById(siteId);
        logger.debug("设置站点菜单...");
        Menu menu = new Menu();
		Item item = new Item();
		item.setName(siteBean.getName());
		item.setUrl(pathUtils.getSiteURL(siteBean));
		item.setTitle(siteBean.getTitle());
        item.setId(siteBean.getId());
		menu.setItem(item);
        List<CategoryBean> categoryBeans = cmsManager.getCategoryService().getMenuBySiteId(siteBean.getId());
        for(CategoryBean categoryBean : categoryBeans){
            menu.addMenu(getSiteMenu(categoryBean));
        }
        return menu;
    }

    public Menu getSiteMenu(CategoryBean categoryBean){
        Menu menu = new Menu();
		Item item = new Item();
		item.setName(categoryBean.getName());
		item.setUrl(pathUtils.getCategoryURL(categoryBean));
		item.setTitle(categoryBean.getTitle());
        item.setId(categoryBean.getId());
		menu.setItem(item);
        List<CategoryBean> categoryBeans = cmsManager.getCategoryService().getMenuByParentId(categoryBean.getId());
        for(CategoryBean category : categoryBeans){
             menu.addMenu(getSiteMenu(category));
        }
        return menu;
    }

    public Menu getCategoryMenu(Long categoryId){
        CategoryBean categoryBean = cmsManager.getCategoryService().getCategoryById(categoryId);
        logger.debug("设置栏目菜单...");
        List<Long> currentIds = new ArrayList<Long>();
        currentIds.add(categoryBean.getId());
        CategoryBean parent = categoryBean.getParent();
        while(parent != null){
            currentIds.add(parent.getId());
            parent = parent.getParent();
        }
        SiteBean siteBean = categoryBean.getSite();
        Menu menu = new Menu();
		Item item = new Item();
		item.setName(siteBean.getName());
		item.setUrl(pathUtils.getSiteURL(siteBean));
		item.setTitle(siteBean.getTitle());
        item.setId(siteBean.getId());
		menu.setItem(item);
        menu.setCurrent(true);
        List<CategoryBean> categoryBeans = cmsManager.getCategoryService().getMenuBySiteId(siteBean.getId());
        for(CategoryBean category : categoryBeans){
             menu.addMenu(getCategoryMenu(category, currentIds));
        }
        return menu;
    }

    public Menu getCategoryMenu(CategoryBean categoryBean, List<Long> currentIds){
        Menu menu = new Menu();
		Item item = new Item();
		item.setName(categoryBean.getName());
		item.setUrl(pathUtils.getCategoryURL(categoryBean));
		item.setTitle(categoryBean.getTitle());
        item.setId(categoryBean.getId());
        if(currentIds.contains(categoryBean.getId())){
            menu.setCurrent(true);
        }
		menu.setItem(item);
        List<CategoryBean> categoryBeans = cmsManager.getCategoryService().getMenuByParentId(categoryBean.getId());
        for(CategoryBean category : categoryBeans){
            menu.addMenu(getCategoryMenu(category, currentIds));
        }
        return menu;
    }

    public List<ContentModel> getContents(Long categoryId, int firstResult, int maxResults) {
        List<ContentBean> contentBeans = cmsManager.getContentService().getContentsByCategoryIdAndPage(categoryId, ContentField._STATE_PUBLISHED, firstResult, maxResults);
        return mapper.mapContents(contentBeans);
    }

    public List<ContentModel> getContents(Long categoryId, int firstResult, int maxResults, boolean loadContent) {
        List<ContentBean> contentBeans = cmsManager.getContentService().getContentsByCategoryIdAndPage(categoryId, ContentField._STATE_PUBLISHED, firstResult, maxResults);
        return mapper.mapContents(contentBeans, loadContent);
    }

}
