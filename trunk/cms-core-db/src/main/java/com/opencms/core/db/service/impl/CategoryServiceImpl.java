package com.opencms.core.db.service.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.dao.CategoryDao;
import com.opencms.core.db.query.Finder;
import com.opencms.core.db.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-10
 * Time: 10:09:04
 * To change this template use File | Settings | File Templates.
 */
@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Transactional(rollbackFor = Exception.class)
    public boolean addCategory(CategoryBean category) {
        category.setCreationDate(new Date());
        categoryDao.persist(category);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateCategory(CategoryBean category) {
        categoryDao.merge(category);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addOrUpdateCategory(CategoryBean category) {
        if(category.getId() == null){
            return addCategory(category);
        } else{
            return updateCategory(category);
        }
    }

    public CategoryBean getCategoryById(Long id) {
        return categoryDao.get(CategoryBean.class, id);
    }
    
    public CategoryBean getCategoryByNames(String[] names) {
    	if(names.length > 1) {
    		CategoryBean c = getCategoryBySiteAndName(names[0], names[1]);
    		for(int i=2; i<names.length; i++) {
    			if(c != null) {
    				c = getCategoryByParentAndName(c.getId(), names[i]);
    			} else{
    				return null;
    			}
    		}
    		return c;
    	}
        return null;
    }
    
    private CategoryBean getCategoryBySiteAndName(String siteName, String name) {
    	Finder finder = new Finder(CategoryBean.class);
    	finder.setColumns(new String[]{"name", "site.name"});
    	finder.setValues(new Serializable[]{name, siteName});
    	List<CategoryBean> list = categoryDao.getByFinder(finder);
    	if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
    
    private CategoryBean getCategoryByParentAndName(Long parent, String name) {
    	Finder finder = new Finder(CategoryBean.class);
    	finder.setColumns(new String[]{"name", "parent.id"});
    	finder.setValues(new Serializable[]{name, parent});
    	List<CategoryBean> list = categoryDao.getByFinder(finder);
    	if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public List<CategoryBean> getCategorysBySiteId(Long siteId, boolean visible) {
        Finder finder = new Finder(CategoryBean.class);
        if(visible){
            finder.setColumns(new String[]{"site.id", "visible"});
            finder.setValues(new Serializable[]{siteId, true});
        } else{
            finder.setColumns(new String[]{"site.id"});
            finder.setValues(new Serializable[]{siteId});
        }
        finder.setNullColumns(new String[]{"parent"});
        finder.setOrders(new String[]{"no"});
        return categoryDao.getByFinder(finder);
    }

    public List<CategoryBean> getCategorysByParentId(Long parentId, boolean visible){
        Finder finder = new Finder(CategoryBean.class);
        if(visible){
            finder.setColumns(new String[]{"parent.id", "visible"});
            finder.setValues(new Serializable[]{parentId, true});
        } else{
            finder.setColumns(new String[]{"parent.id"});
            finder.setValues(new Serializable[]{parentId});
        }
        finder.setOrders(new String[]{"no"});
        return categoryDao.getByFinder(finder);
    }

    public List<CategoryBean> getMenuBySiteId(Long siteId) {
        List<CategoryBean> categoryBeans = getCategorysBySiteId(siteId, true);
        return categoryBeans;
    }

    public List<CategoryBean> getMenuByParentId(Long parentId){
        return getCategorysByParentId(parentId, true);
    }
}
