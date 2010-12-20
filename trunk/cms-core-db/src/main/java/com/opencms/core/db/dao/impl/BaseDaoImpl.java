package com.opencms.core.db.dao.impl;

import com.opencms.core.db.dao.BaseDao;
import com.opencms.core.db.query.Finder;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 21:23:13
 * To change this template use File | Settings | File Templates.
 */
@Transactional(rollbackFor = Exception.class)
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    public boolean persist(T obj){
        this.getHibernateTemplate().persist(obj);
        return true;
    }

    public boolean merge(T obj){
        this.getHibernateTemplate().merge(obj);
        return true;
    }

    public boolean delete(T obj){
        this.getHibernateTemplate().delete(obj);
        return true;
    }

    public boolean deleteAll(List<T> list){
        this.getHibernateTemplate().deleteAll(list);
        return true;
    }

    @Transactional(readOnly = true)
    public T get(Class c, Serializable id) {
        return (T)this.getHibernateTemplate().get(c, id);
    }

    @Transactional(readOnly = true)
    public T get(Class c, String column, Serializable value) {
        List<T> list = getAll(c, column, value);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<T> getAll(Class c, String column, Serializable value) {
        Finder finder = new Finder(c);
        finder.setColumns(new String[]{column});
        finder.setValues(new Serializable[]{value});
        return getByFinder(finder);
    }

    @Transactional(readOnly = true)
    public List<T> getAll(Class c){
        Finder finder = new Finder(c);
        return getByFinder(finder);
    }

    @Transactional(readOnly = true)
    public List<T> getByFinder(Finder finder) {
        return finder.find(this.getSession());
    }

    @Transactional(readOnly = true)
    public long getCountByFinder(Finder finder) {
        return finder.findNumber(this.getSession());
    }

}
