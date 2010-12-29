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
import com.opencms.wcm.client.widget.content.ContentPublishingListPanel;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-27
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
public class ContentPublishingView extends View {

    WcmMessages msgs = GWT.create(WcmMessages.class);

    private ContentPublishingListPanel contentPublishingListPanel;

    public ContentPublishingView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent appEvent) {
        if (AppEvents.CONTENT_PUBLISHING_MANAGER == appEvent.getType()){
            Content content = (Content) appEvent.getData();
            content.setStates(new String[]{"1", "2"});
            contentPublishingListPanel = new ContentPublishingListPanel(content);
            Dispatcher.forwardEvent(
                    AppEvents.ARTICLE_MANAGER_CHANGE_EVENT,
                    new Entry(
                            AppEvents.CONTENT_PUBLISHING_MANAGER.getId(),
                            msgs.content_publishing_header(),
                            AppState.OWNER_ARTICLE_MANAGER_CALLBACK,
                            contentPublishingListPanel,
                            true,
                            false,
                            AppEvents.CONTENT_PUBLISHING_MANAGER,
                            false));
        } else if(AppEvents.CONTENT_PUBLISHING_MANAGER_SUCCESS == appEvent.getType()){
            contentPublishingListPanel.refresh();
        }
    }
}
