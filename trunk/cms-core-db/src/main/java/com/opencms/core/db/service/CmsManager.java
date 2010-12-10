package com.opencms.core.db.service;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-5
 * Time: 8:25:06
 * To change this template use File | Settings | File Templates.
 */
public interface CmsManager {

    public UserService getUserService();

    public ContentService getContentService();

    public SiteService getSiteService();

    public CategoryService getCategoryService();
    
}
