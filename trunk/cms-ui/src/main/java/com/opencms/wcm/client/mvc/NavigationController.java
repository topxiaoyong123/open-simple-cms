package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.opencms.wcm.client.AppEvents;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 14:20:18
 * To change this template use File | Settings | File Templates.
 */
public class NavigationController extends Controller {

    private NavigationView view;

    public NavigationController() {
        this.registerEventTypes(AppEvents.INIT_UI);
        this.registerEventTypes(AppEvents.OTHER_MANAGER_ITEM_SELECTION);
        this.registerEventTypes(AppEvents.OTHER_MANAGER_ITEM_SELECTION_NONE);
        this.registerEventTypes(AppEvents.ARTICLE_MANAGER_ITEM_SELECTION_NONE);
    }

    public void handleEvent(AppEvent appEvent) {
        this.forwardToView(view, appEvent);
    }

    @Override
    protected void initialize() {
        view = new NavigationView(this);
    }
}
