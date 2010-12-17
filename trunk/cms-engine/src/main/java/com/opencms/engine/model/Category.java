package com.opencms.engine.model;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.util.common.page.PageBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:15
 * To change this template use File | Settings | File Templates.
 */
public class Category extends CategoryBean implements Model {

    private PageBean page;

    private Menu menu;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
