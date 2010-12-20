package com.opencms.core.db.service.impl;

import com.opencms.core.db.query.Finder;
import org.springframework.stereotype.Service;
import com.opencms.core.db.service.CategoryService;
import com.opencms.core.db.dao.CategoryDao;
import com.opencms.core.db.bean.CategoryBean;
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
@Service("categoryService")
@Transactional(rollbackFor = Exception.class)
public class CategoryServiceImpl implements CategoryService {

    @Resource(name = "categoryDao")
    private CategoryDao categoryDao;

    public boolean addCategory(CategoryBean category) {
        category.setCreationDate(new Date());
        categoryDao.persist(category);
        return true;
    }

    public boolean updateCategory(CategoryBean category) {
        categoryDao.merge(category);
        return true;
    }

    public boolean addOrUpdateCategory(CategoryBean category) {
        if(category.getId() == null){
            return addCategory(category);
        } else{
            return updateCategory(category);
        }
    }

    @Transactional(readOnly = true)
    public CategoryBean getCategoryById(String id) {
        return categoryDao.get(CategoryBean.class, id);
    }

    @Transactional(readOnly = true)
    public List<CategoryBean> getCategorysBySiteId(String siteId, boolean visible) {
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

    @Transactional(readOnly = true)
    public List<CategoryBean> getCategorysByParentId(String parentId, boolean visible){
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

    @Transactional(readOnly = true)
    public List<CategoryBean> getMenuBySiteId(String siteId) {
        List<CategoryBean> categoryBeans = getCategorysBySiteId(siteId, true);
        return categoryBeans;
    }

    @Transactional(readOnly = true)
    public List<CategoryBean> getMenuByParentId(String parentId){
        return getCategorysByParentId(parentId, true);
    }
}
