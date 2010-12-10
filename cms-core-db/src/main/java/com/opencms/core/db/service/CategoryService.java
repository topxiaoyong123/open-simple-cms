package com.opencms.core.db.service;

import com.opencms.core.db.bean.CategoryBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-10
 * Time: 10:08:49
 * To change this template use File | Settings | File Templates.
 */
public interface CategoryService {

    public boolean addCategory(CategoryBean category);

    public boolean updateCategory(CategoryBean category);

    public boolean addOrUpdateCategory(CategoryBean category);

    public CategoryBean getCategoryById(String id);
}
