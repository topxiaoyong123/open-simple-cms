package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.model.Content;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.widget.content.ContentListPanel;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-8
 * Time: 8:55:38
 * To change this template use File | Settings | File Templates.
 */
public class ContentListView extends View {

    private ContentListPanel artcilelisttpanel;

    public ContentListView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent appEvent) {
        if(appEvent.getType() == AppEvents.CONTENT_VIEWARTICLELIST){
            Content content = (Content) appEvent.getData();
            artcilelisttpanel = new ContentListPanel(content, true);
            Dispatcher.forwardEvent(
                    AppEvents.ARTICLE_MANAGER_CHANGE_EVENT,
                    new Entry(
                            AppEvents.CONTENT_VIEWARTICLELIST.getId(),
                            "文章采编",
                            AppState.OWNER_ARTICLE_MANAGER_CALLBACK,
                            artcilelisttpanel,
                            true,
                            false,
                            AppEvents.CONTENT_VIEWARTICLELIST,
                            false));
        }
    }
}
