package com.opencms.core.db.dao.impl;

import com.opencms.core.db.dao.BaseDao;
import com.opencms.core.db.query.Finder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 21:23:13
 * To change this template use File | Settings | File Templates.
 */
@Transactional(readOnly = true)
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

    private static Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    @Transactional(rollbackFor = Exception.class)
    public boolean persist(T obj){
        this.getHibernateTemplate().persist(obj);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean merge(T obj){
        this.getHibernateTemplate().merge(obj);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delete(T obj){
        this.getHibernateTemplate().delete(obj);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAll(List<T> list){
        this.getHibernateTemplate().deleteAll(list);
        return true;
    }

    public T get(Class c, Serializable id) {
        return (T)this.getHibernateTemplate().get(c, id);
    }

    public T get(Class c, String column, Serializable value) {
        List<T> list = getAll(c, column, value);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public List<T> getAll(Class c, String column, Serializable value) {
        Finder finder = new Finder(c);
        finder.setColumns(new String[]{column});
        finder.setValues(new Serializable[]{value});
        return getByFinder(finder);
    }

    public List<T> getAll(Class c){
        Finder finder = new Finder(c);
        return getByFinder(finder);
    }

    public List<T> getByFinder(Finder finder) {
        return finder.find(this.getSession());
    }

    public Iterator<T> getIteratorByFinder(Finder finder) {
        return finder.findIterator(this.getSession());
    }

    public long getCountByFinder(Finder finder) {
        return finder.findNumber(this.getSession());
    }

}
