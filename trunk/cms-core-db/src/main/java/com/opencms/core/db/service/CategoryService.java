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

    public CategoryBean getCategoryById(Long id);
    
    public CategoryBean getCategoryByNames(String[] names);

    /**
     * 根据站点ID取以及栏目
     * @param siteId
     * @return
     */
    public List<CategoryBean> getCategorysBySiteId(Long siteId, boolean visible);

    public List<CategoryBean> getCategorysByParentId(Long parentId, boolean visible);

    public List<CategoryBean> getMenuBySiteId(Long siteId);

    public List<CategoryBean> getMenuByParentId(Long parentId);
}
