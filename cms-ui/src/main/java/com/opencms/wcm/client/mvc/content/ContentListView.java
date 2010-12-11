package com.opencms.wcm.client.mvc.content;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.widget.content.ContentListPanel;
import com.google.gwt.core.client.GWT;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-8
 * Time: 8:55:38
 * To change this template use File | Settings | File Templates.
 */
public class ContentListView extends View {

    private ContentListPanel artcilelisttpanel;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public ContentListView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent appEvent) {
        if(appEvent.getType() == AppEvents.CATEGORY_MANAGER){
            Content content = (Content) appEvent.getData();
            artcilelisttpanel = new ContentListPanel(content);
            Dispatcher.forwardEvent(
                    AppEvents.ARTICLE_MANAGER_CHANGE_EVENT,
                    new Entry(
                            AppEvents.CATEGORY_MANAGER.getId(),
                            msgs.content_editing_header(),
                            AppState.OWNER_ARTICLE_MANAGER_CALLBACK,
                            artcilelisttpanel,
                            true,
                            false,
                            AppEvents.CATEGORY_MANAGER,
                            false));
        }
    }
}
