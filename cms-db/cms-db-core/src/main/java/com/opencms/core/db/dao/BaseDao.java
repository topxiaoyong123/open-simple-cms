package com.opencms.core.db.dao;

import com.opencms.core.db.query.Finder;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

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

    public boolean deleteAll(List<T> list);

    public T get(Class c, Serializable id);

    public T get(Class c, String column, Serializable value);

    public List<T> getAll(Class c, String column, Serializable value);

    public List<T> getAll(Class c);

    public List<T> getByFinder(Finder finder);

    public Iterator<T> getIteratorByFinder(Finder finder);

    public long getCountByFinder(Finder finder);

}
