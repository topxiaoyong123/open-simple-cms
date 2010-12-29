package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.opencms.wcm.client.AppEvents;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 11:10:53
 * To change this template use File | Settings | File Templates.
 */
public class AppController extends Controller {

    private AppView view;

    public AppController() {
        this.registerEventTypes(AppEvents.INIT_UI);
    }

    @Override
    protected void initialize() {
        view = new AppView(this);
    }

    @Override
    public void handleEvent(AppEvent appEvent) {
        this.forwardToView(view, appEvent);
    }
}
