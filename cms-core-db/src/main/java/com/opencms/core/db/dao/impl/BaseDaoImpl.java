package com.opencms.core.db.dao.impl;

import com.opencms.core.db.dao.BaseDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 21:23:13
 * To change this template use File | Settings | File Templates.
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

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

    public T get(Class c, Serializable id) {
        return (T)this.getHibernateTemplate().get(c, id);
    }

    public T get(Class c, String column, Serializable value) {
        List<T> list = this.getHibernateTemplate().find("from " + c.getSimpleName() + " o where o." + column + " = ?", new Object[]{value});
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public List<T> getAll(Class c){
        return this.getHibernateTemplate().find("from " + c.getSimpleName());
    }

}
