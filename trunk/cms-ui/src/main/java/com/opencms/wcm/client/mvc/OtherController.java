package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.opencms.wcm.client.AppEvents;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 20:48:09
 * To change this template use File | Settings | File Templates.
 */
public class OtherController extends Controller {

    private OtherView view;

    public OtherController() {
        this.registerEventTypes(AppEvents.OTHER_APP);
    }

    @Override
    protected void initialize() {
        view = new OtherView(this);
    }

    @Override
    public void handleEvent(AppEvent appEvent) {
        this.forwardToView(view, appEvent);
    }
}
