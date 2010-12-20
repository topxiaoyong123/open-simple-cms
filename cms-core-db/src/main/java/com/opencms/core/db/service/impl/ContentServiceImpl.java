package com.opencms.core.db.service.impl;

import com.opencms.core.db.query.Finder;
import com.opencms.core.db.query.Page;
import com.opencms.core.db.service.ContentService;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.dao.ContentDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 20:58:51
 * To change this template use File | Settings | File Templates.
 */
@Service("contentService")
@Transactional(rollbackFor = Exception.class)
public class ContentServiceImpl implements ContentService {

    @Resource(name = "contentDao")
    private ContentDao contentDao;

    public boolean addContent(ContentBean content) {
        content.setCreationDate(new Date());
        return contentDao.persist(content);
    }

    public boolean updateContent(ContentBean content) {
        content.setModificationDate(new Date());
        return contentDao.merge(content);
    }

    public boolean addOrUpdateContent(ContentBean content) {
        if(content.getId() == null){
            return addContent(content);
        } else{
            return updateContent(content);            
        }
    }

    @Transactional(readOnly = true)
    public ContentBean getContentById(String id) {
        return contentDao.get(ContentBean.class, id);
    }

    @Transactional(readOnly = true)
    public ContentBean getContentById(String id, boolean loadContent) {
        ContentBean content = getContentById(id);
        if(loadContent){
            content.getContent();
        }
        return content;
    }

    @Transactional(readOnly = true)
    public List<ContentBean> getContentsByCategoryIdAndPage(String categoryId, int firstResult, int maxResults) {
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id"});
        finder.setValues(new Serializable[]{categoryId});
        finder.setPage(new Page(firstResult, maxResults));
        finder.setOrders(new String[]{"top", "no",  "creationDate"});
        return contentDao.getByFinder(finder);
    }

    @Transactional(readOnly = true)
    public List<ContentBean> getContentsByCategoryIdAndPage(String categoryId, String state, int firstResult, int maxResults) {
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id", "state"});
        finder.setValues(new Serializable[]{categoryId, state});
        finder.setPage(new Page(firstResult, maxResults));
        finder.setOrders(new String[]{"top", "no", "creationDate"});
        return contentDao.getByFinder(finder);
    }

    @Transactional(readOnly = true)
    public long getCountByCategoryId(String categoryId){
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id"});
        finder.setValues(new Serializable[]{categoryId});
        return contentDao.getCountByFinder(finder);
    }

    @Transactional(readOnly = true)
    public long getCountByCategoryId(String categoryId, String state){
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id", "state"});
        finder.setValues(new Serializable[]{categoryId, state});
        return contentDao.getCountByFinder(finder);
    }
}
