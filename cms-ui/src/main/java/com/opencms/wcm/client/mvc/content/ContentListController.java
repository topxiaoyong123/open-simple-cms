package com.opencms.wcm.client.mvc.content;

import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.opencms.wcm.client.AppEvents;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-8
 * Time: 8:53:27
 * To change this template use File | Settings | File Templates.
 */
public class ContentListController extends Controller {

    private ContentListView view;

    public ContentListController() {
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER);
    }

    @Override
    public void handleEvent(AppEvent appEvent) {
        if(appEvent.getType() == AppEvents.CATEGORY_MANAGER){
            this.forwardToView(view, appEvent);
        }
    }

    @Override
    protected void initialize() {
        view = new ContentListView(this);
    }
}
