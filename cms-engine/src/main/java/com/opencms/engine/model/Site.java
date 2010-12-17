package com.opencms.engine.model;

import com.opencms.core.db.bean.SiteBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:15
 * To change this template use File | Settings | File Templates.
 */
public class Site extends SiteBean implements Model {

    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
