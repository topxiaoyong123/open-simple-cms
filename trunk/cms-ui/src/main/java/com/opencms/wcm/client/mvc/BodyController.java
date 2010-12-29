package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.opencms.wcm.client.AppEvents;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 15:19:23
 * To change this template use File | Settings | File Templates.
 */
public class BodyController extends Controller {

    private BodyView view ;

    public BodyController() {
        this.registerEventTypes(AppEvents.INIT_UI);
        this.registerEventTypes(AppEvents.WELCOME);
        this.registerEventTypes(AppEvents.OTHER_MANAGER_CHANGE_EVENT);
        this.registerEventTypes(AppEvents.ARTICLE_MANAGER_CHANGE_EVENT);
        this.registerEventTypes(AppEvents.ARTICLE_MANAGER_CHANGE_CATEGORY);
    }

    @Override
    protected void initialize() {
        view = new BodyView(this);
    }

    @Override
    public void handleEvent(AppEvent appEvent) {
        forwardToView(view,appEvent);
    }
}
