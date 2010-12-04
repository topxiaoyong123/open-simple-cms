package com.opencms.core.db.dao;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-4
 * Time: 21:22:58
 * To change this template use File | Settings | File Templates.
 */
public interface BaseDao<T> {

    public boolean persist(T obj);

    public boolean merge(T obj);

    public boolean delete(T obj);

    public T get(Class c, Serializable id);

    public T get(Class c, String column, Serializable value);

}
