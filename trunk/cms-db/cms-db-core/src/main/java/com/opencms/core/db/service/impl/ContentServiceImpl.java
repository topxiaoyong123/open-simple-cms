package com.opencms.core.db.service.impl;

import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.field.ContentField;
import com.opencms.core.db.dao.ContentDao;
import com.opencms.core.db.query.Finder;
import com.opencms.core.db.query.Page;
import com.opencms.core.db.service.ContentService;
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
@Service
@Transactional(readOnly = true)
public class ContentServiceImpl implements ContentService {

    @Resource
    private ContentDao contentDao;

    @Transactional(rollbackFor = Exception.class)
    public boolean addContent(ContentBean content) {
        content.setCreationDate(new Date());
        return contentDao.persist(content);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateContent(ContentBean content) {
        content.setModificationDate(new Date());
        return contentDao.merge(content);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addOrUpdateContent(ContentBean content) {
        if(content.getId() == null){
            return addContent(content);
        } else{
            return updateContent(content);            
        }
    }

    public ContentBean getContentById(Long id) {
        return contentDao.get(ContentBean.class, id);
    }
    
    public ContentBean getPublishedContentById(Long id) {
    	ContentBean c = contentDao.get(ContentBean.class, id);
    	if(c != null && ContentField._STATE_PUBLISHED.equals(c.getState())) {
    		return c;
    	}
        return null;
    }

    public List<ContentBean> getContentsByCategoryIdAndPage(Long categoryId, int firstResult, int maxResults) {
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id"});
        finder.setValues(new Serializable[]{categoryId});
        finder.setPage(new Page(firstResult, maxResults));
        finder.setOrders(new String[]{"top", "no",  "creationDate"});
        return contentDao.getByFinder(finder);
    }

    public List<ContentBean> getContentsByCategoryIdAndPage(Long categoryId, String state, int firstResult, int maxResults) {
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id", "state"});
        finder.setValues(new Serializable[]{categoryId, state});
        finder.setPage(new Page(firstResult, maxResults));
        finder.setOrders(new String[]{"top", "no", "creationDate"});
        return contentDao.getByFinder(finder);
    }

    public long getCountByCategoryId(Long categoryId){
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id"});
        finder.setValues(new Serializable[]{categoryId});
        return contentDao.getCountByFinder(finder);
    }

    public long getCountByCategoryId(Long categoryId, String state){
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id", "state"});
        finder.setValues(new Serializable[]{categoryId, state});
        return contentDao.getCountByFinder(finder);
    }

    public List<ContentBean> getContentsByCategoryIdAndPage(Long categoryId, String[] states, int firstResult, int maxResults) {
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id"});
        finder.setValues(new Serializable[]{categoryId});
        if(states != null && states.length > 0){
            StringBuffer s = new StringBuffer(" (1=2 ");
            for(int i = 0; i < states.length; i ++){
                s.append(" or o.state = ? ");
            }
            s.append(") ");
            finder.setFilter(s.toString());
            finder.setFilterValues(states);
        }
        finder.setPage(new Page(firstResult, maxResults));
        finder.setOrders(new String[]{"top", "no", "creationDate"});
        return contentDao.getByFinder(finder);
    }

    public long getCountByCategoryId(Long categoryId, String[] states) {
        Finder finder = new Finder(ContentBean.class);
        finder.setColumns(new String[]{"category.id"});
        finder.setValues(new Serializable[]{categoryId});
        if(states != null && states.length > 0){
            StringBuffer s = new StringBuffer(" (1=2 ");
            for(int i = 0; i < states.length; i ++){
                s.append(" or o.state = ? ");
            }
            s.append(") ");
            finder.setFilter(s.toString());
            finder.setFilterValues(states);
        }
        return contentDao.getCountByFinder(finder);
    }
}
