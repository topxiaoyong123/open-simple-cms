package com.opencms.util.common.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-16
 * Time: 下午1:21
 * To change this template use File | Settings | File Templates.
 */
public class PageBean<T> {

    public final static int DEFAULT_SIZE = 10;

    private int firstResult;

    private int lastResult;

    private List<T> records; /*记录集*/

    private PageIndex pageIndex; /*页面索引*/

    private int currentPage = 1;

    private int totalCount = 0;

    private int pageSize = PageBean.DEFAULT_SIZE;

    private int totalPage = 1;

    private String url;     /*页面url*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageBean(int pageSize, int currentPage, int totalCount) {
        this.setPageSize(pageSize);
        this.setCurrentPage(currentPage);
        this.setTotalCount(totalCount);
    }

    public PageBean(int pageSize, int currentPage, int totalCount, HttpServletRequest request) {
        this.setPageSize(pageSize);
        this.setCurrentPage(currentPage);
        this.setTotalCount(totalCount);
        this.rebuildUrl(request);
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * 防止当前页越界
     */
    public void resetCurpage() {
        if (this.currentPage < 1) {
            this.currentPage = 1;
        }
        if (this.currentPage > this.totalPage) {
            this.currentPage = this.totalPage <= 0 ? 1 : this.totalPage;
        }
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.setTotalPage(totalCount % this.getPageSize() == 0 ? totalCount / this.getPageSize() : totalCount / this.getPageSize() + 1);
        this.resetCurpage();
        this.setPageIndex(PageIndex.getPageIndex(pagecode, this.getCurrentPage(), this.getTotalPage()));
        this.setFirstResult((currentPage - 1) * pageSize);
        this.setLastResult(firstResult + pageSize - 1);
    }

    public PageIndex getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(PageIndex pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult > totalCount ? totalCount : firstResult;
    }

    public int getLastResult() {
        return lastResult;
    }

    public void setLastResult(int lastResult) {
        this.lastResult = lastResult > totalCount ? totalCount : lastResult;
    }

    public static int pagecode = 10; /*每页显示页码数量*/

    /**
     * 重构URL
     *
     * @param request
     */
    public void rebuildUrl(HttpServletRequest request) {
        StringBuffer url = new StringBuffer().append(request.getContextPath()).append(request.getServletPath());
        int urllength = url.length();
        Enumeration e = request.getParameterNames();
        String paraname;
        String value;
        while (urllength == url.length() && e.hasMoreElements()) {
            paraname = (String) e.nextElement();
            value = request.getParameter(paraname);
            if (value != null && !"".equals(value) && paraname != null && !"".equals(paraname) && !"currentpage".equals(paraname)) {
                url.append("?").append(paraname).append("=").append(value);
            }
        }
        if (urllength == url.length()) {
            url.append("?abcdefghijklmn=1");
        }
        while (e.hasMoreElements()) {
            paraname = (String) e.nextElement();
            value = request.getParameter(paraname);
            if (value != null && !"".equals(value) && paraname != null && !"".equals(paraname) && !"currentpage".equals(paraname)) {
                url.append("&").append(paraname).append("=").append(value);
            }
        }
        this.setUrl(url.toString());
    }
}
