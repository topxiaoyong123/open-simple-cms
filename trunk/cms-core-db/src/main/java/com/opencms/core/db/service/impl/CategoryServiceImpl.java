package com.opencms.core.db.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.opencms.core.db.service.CategoryService;
import com.opencms.core.db.dao.CategoryDao;
import com.opencms.core.db.bean.CategoryBean;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-10
 * Time: 10:09:04
 * To change this template use File | Settings | File Templates.
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
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

    public CategoryBean getCategoryById(String id) {
        return categoryDao.get(CategoryBean.class, id);
    }
}
