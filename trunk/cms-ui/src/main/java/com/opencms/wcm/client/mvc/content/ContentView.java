package com.opencms.wcm.client.mvc.content;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.widget.content.ContentListPanel;
import com.opencms.wcm.client.widget.content.ContentPanel;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-8
 * Time: 8:55:38
 * To change this template use File | Settings | File Templates.
 */
public class ContentView extends View {

    private Window window = new Window();
    
    private ContentListPanel contentListPanel;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public ContentView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent appEvent) {
        if(appEvent.getType() == AppEvents.CONTENT_MANAGER){
            Content content = (Content) appEvent.getData();
            contentListPanel = new ContentListPanel(content);
            Dispatcher.forwardEvent(
                    AppEvents.ARTICLE_MANAGER_CHANGE_EVENT,
                    new Entry(
                            AppEvents.CONTENT_MANAGER.getId(),
                            msgs.content_editing_header(),
                            AppState.OWNER_ARTICLE_MANAGER_CALLBACK,
                            contentListPanel,
                            true,
                            false,
                            AppEvents.CONTENT_MANAGER,
                            false));
        } else if(AppEvents.CONTENT_MANAGER_CANCEL == appEvent.getType() || AppEvents.CONTENT_MANAGER_SUCCESS == appEvent.getType()){
            window.hide();
            contentListPanel.refresh();
        } else if(AppEvents.CONTENT_MANAGER_ADD == appEvent.getType()){
            Content c = appEvent.getData();
            ContentPanel cp = new ContentPanel(c, AppState.westTreeItemObj);
            window.removeAll();
            window.add(cp);
            window.setHeading(msgs.content_add_header());
            window.setWidth(617);
            window.show();
        } else if(AppEvents.CONTENT_MANAGER_EDIT == appEvent.getType()){
            Content c = appEvent.getData();
            ContentPanel cp = new ContentPanel(c, AppState.westTreeItemObj);
            window.removeAll();
            window.add(cp);
            window.setHeading(msgs.content_add_header());
            window.setWidth(617);
            window.show();
        }
    }
}
