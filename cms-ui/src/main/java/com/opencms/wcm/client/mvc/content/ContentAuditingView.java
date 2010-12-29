package com.opencms.wcm.client.mvc.content;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.widget.content.ContentAuditingListPanel;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-27
 * Time: 上午10:09
 * To change this template use File | Settings | File Templates.
 */
public class ContentAuditingView extends View {

    private ContentAuditingListPanel contentAuditingListPanel;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public ContentAuditingView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent appEvent) {
        if (AppEvents.CONTENT_AUDITING_MANAGER == appEvent.getType()){
            Content content = (Content) appEvent.getData();
            content.setState("0");
            contentAuditingListPanel = new ContentAuditingListPanel(content);
            Dispatcher.forwardEvent(
                    AppEvents.ARTICLE_MANAGER_CHANGE_EVENT,
                    new Entry(
                            AppEvents.CONTENT_AUDITING_MANAGER.getId(),
                            msgs.content_auditing_header(),
                            AppState.OWNER_ARTICLE_MANAGER_CALLBACK,
                            contentAuditingListPanel,
                            true,
                            false,
                            AppEvents.CONTENT_AUDITING_MANAGER,
                            false));
        } else if(AppEvents.CONTENT_AUDITING_MANAGER_SUCCESS == appEvent.getType()){
            contentAuditingListPanel.refresh();
        }
    }
}
