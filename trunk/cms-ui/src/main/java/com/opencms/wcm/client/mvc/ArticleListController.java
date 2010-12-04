package com.opencms.wcm.client.mvc;

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
public class ArticleListController extends Controller {

    private ArticleListView view;
    public ArticleListController() {
        this.registerEventTypes(AppEvents.CONTENT_VIEWARTICLELIST);
    }

    public void handleEvent(AppEvent appEvent) {
        if(appEvent.getType() == AppEvents.CONTENT_VIEWARTICLELIST){
            this.forwardToView(view, appEvent);
        }
    }

    @Override
    protected void initialize() {
        view = new ArticleListView(this);
    }
}
