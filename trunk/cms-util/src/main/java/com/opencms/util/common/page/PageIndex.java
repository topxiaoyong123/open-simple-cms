package com.opencms.util.common.page;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-16
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
public class PageIndex {

    private long startindex;

    private long endindex;

    public PageIndex(long startindex, long endindex) {
        this.startindex = startindex;
        this.endindex = endindex;
    }

    public long getStartindex() {
        return startindex;
    }

    public void setStartindex(long startindex) {
        this.startindex = startindex;
    }

    public long getEndindex() {
        return endindex;
    }

    public void setEndindex(long endindex) {
        this.endindex = endindex;
    }

    public static PageIndex getPageIndex(long viewpagecount, int currenPage, long totalpage) {
        long startpage = currenPage - (viewpagecount % 2 == 0 ? viewpagecount / 2 - 1 : viewpagecount / 2);
        long endpage = currenPage + viewpagecount / 2;
        if (startpage < 1) {
            startpage = 1;
            if (totalpage >= viewpagecount) endpage = viewpagecount;
            else endpage = totalpage;
        }
        if (endpage > totalpage) {
            endpage = totalpage;
            if ((endpage - viewpagecount) > 0) startpage = endpage - viewpagecount + 1;
            else startpage = 1;
        }
        return new PageIndex(startpage, endpage);
    }

}

