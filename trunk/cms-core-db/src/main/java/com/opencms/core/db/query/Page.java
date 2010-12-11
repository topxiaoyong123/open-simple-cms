package com.opencms.core.db.query;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 23:33:38
 * To change this template use File | Settings | File Templates.
 */
public class Page {

    private int firstResult;

    private int maxResults;

    public Page(int firstResult, int maxResults) {
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }
}
