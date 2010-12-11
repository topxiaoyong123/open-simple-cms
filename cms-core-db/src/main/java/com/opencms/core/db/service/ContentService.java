package com.opencms.core.db.service;

import com.opencms.core.db.bean.ContentBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 20:58:40
 * To change this template use File | Settings | File Templates.
 */
public interface ContentService {

    public boolean addContent(ContentBean content);

    public boolean updateContent(ContentBean content);

    public boolean addOrUpdateContent(ContentBean content);

    public ContentBean getContentById(String id);

    public ContentBean getContentById(String id, boolean loadContent);

    public List<ContentBean> getContentsByCategoryIdAndPage(String categoryId, int firstResult, int maxResults);

    public long getCountByCategoryId(String categoryId);

}
