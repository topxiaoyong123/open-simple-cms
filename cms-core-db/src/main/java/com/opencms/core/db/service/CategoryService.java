package com.opencms.core.db.service;

import com.opencms.core.db.bean.CategoryBean;

import java.util.List;

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

    /**
     * 根据站点ID取以及栏目
     * @param siteId
     * @return
     */
    public List<CategoryBean> getCategorysBySiteId(String siteId, boolean visible);

    public List<CategoryBean> getCategorysByParentId(String parentId, boolean visible);

    public List<CategoryBean> getMenuBySiteId(String siteId);

    public List<CategoryBean> getMenuByParentId(String parentId);
}
