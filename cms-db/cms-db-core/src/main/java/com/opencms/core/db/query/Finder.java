package com.opencms.core.db.query;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 23:11:44
 * To change this template use File | Settings | File Templates.
 */
public class Finder<T> {

    private static Logger logger = LoggerFactory.getLogger(Finder.class);

    private boolean cacheable = false;

    private Class targetEntity;

    private String[] columns;

    private Serializable[] values;

    private String[] notColumns;

    private Serializable[] notValues;

    private String filter;

    private Serializable[] filterValues;

    private String[] nullColumns;

    private String[] orders;

    private Page page;

    public Finder(Class targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Finder(Class targetEntity, String[] columns, Serializable[] values, String[] nullColumns, String[] orders, Page page) {
        this.targetEntity = targetEntity;
        this.columns = columns;
        this.values = values;
        this.nullColumns = nullColumns;
        this.orders = orders;
        this.page = page;
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public void setCacheable(boolean cacheable) {
        this.cacheable = cacheable;
    }

    public Class getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(Class targetEntity) {
        this.targetEntity = targetEntity;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public Serializable[] getValues() {
        return values;
    }

    public void setValues(Serializable[] values) {
        this.values = values;
    }

    public String[] getNotColumns() {
        return notColumns;
    }

    public void setNotColumns(String[] notColumns) {
        this.notColumns = notColumns;
    }

    public Serializable[] getNotValues() {
        return notValues;
    }

    public void setNotValues(Serializable[] notValues) {
        this.notValues = notValues;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Serializable[] getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(Serializable[] filterValues) {
        this.filterValues = filterValues;
    }

    public String[] getNullColumns() {
        return nullColumns;
    }

    public void setNullColumns(String[] nullColumns) {
        this.nullColumns = nullColumns;
    }

    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getQueryHql(){
        StringBuffer s = new StringBuffer("from ").append(this.getTargetEntity().getSimpleName()).append(" o where 1 = 1");
        if(columns != null && values != null){
            if(columns.length == values.length){
                for(String column : columns){
                    s.append(" and o.").append(column).append(" = ? ");
                }
            } else{
                logger.warn("属性与值无法一一对应");
                columns = null;
                values = null;
            }
        }
        if(notColumns != null && notValues != null){
            if(notColumns.length == notValues.length){
                for(String column : notColumns){
                    s.append(" and o.").append(column).append(" <> ? ");
                }
            } else{
                logger.warn("属性与值无法一一对应");
                notColumns = null;
                notValues = null;
            }
        }
        if(nullColumns != null){
            for(String nullColumn : nullColumns){
                s.append(" and o.").append(nullColumn).append(" is null ");    
            }
        }
        if(filter != null && !"".equals(filter)){
            s.append(" and ").append(filter);
        }
        if(orders != null && orders.length > 0){
            s.append(" order by ");
            int index = 0;
            for(String order : orders){
                if(index == 0){
                    s.append(" o.").append(order);
                } else{
                    s.append(" ,o.").append(order);
                }
                index ++;
            }
        }
        logger.debug("query hql : [{}]", s);
        return s.toString();
    }

    public String getCountHql(){
        String s = "select count(o.id) " + getQueryHql();
        logger.debug("count hql : [{}]", s);
        return s;
    }

    public List<T> find(Session session){
        Query query = session.createQuery(getQueryHql());
        query = setParameters(query).setCacheable(true);
        if(this.getPage() != null){
            logger.debug("根据分页查询[{}]", this.getPage());
            return query.setFirstResult(page.getFirstResult()).setMaxResults(page.getMaxResults()).list();
        } else{
            logger.debug("查询所有，不分页");
            return query.list();
        }
    }

    /**
     * 返回query.iterator()，支持缓存读取;
     * @param session
     * @return
     */
    public Iterator<T> findIterator(Session session){
        Query query = session.createQuery(getQueryHql());
        query = setParameters(query).setCacheable(true);
        if(this.getPage() != null){
            logger.debug("根据分页查询[{}]", this.getPage());
            return query.setFirstResult(page.getFirstResult()).setMaxResults(page.getMaxResults()).iterate();
        } else{
            logger.debug("查询所有，不分页");
            return query.iterate();
        }
    }

    public long findNumber(Session session){
        Query query = session.createQuery(getCountHql());
        query = setParameters(query).setCacheable(true);
        return ((Long)query.uniqueResult()).longValue();
    }

    private Query setParameters(Query query){
        int index = 0;
        if(values != null){
            for(int i = 0; i < values.length; i ++){
                query = query.setParameter(index, values[i]);
                index ++;
            }
        }
        if(notValues != null){
            for(int i = 0; i < notValues.length; i ++){
                query = query.setParameter(index, notValues[i]);
                index ++;
            }
        }
        if(filterValues != null){
            for(int i = 0; i < filterValues.length; i ++){
                query = query.setParameter(index, filterValues[i]);
                index ++;
            }
        }
        return query;
    }

}
