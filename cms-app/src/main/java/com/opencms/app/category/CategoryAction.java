package com.opencms.app.category;

import com.opencms.util.common.page.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 13:28:18
 * To change this template use File | Settings | File Templates.
 */
public class CategoryAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(CategoryAction.class);

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int page = 1;

    private int pagesize = PageBean.DEFAULT_SIZE;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public String view(){
        logger.debug("category-id:[{}] page:[{}] pagesize:[{}]", new Object[]{id, page, pagesize});
        return SUCCESS;
    }

}
