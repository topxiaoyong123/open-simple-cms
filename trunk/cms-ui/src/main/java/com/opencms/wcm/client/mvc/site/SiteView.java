package com.opencms.wcm.client.mvc.site;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Window;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.AppEventType;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.model.Site;
import com.opencms.wcm.client.widget.site.SiteListPanel;
import com.opencms.wcm.client.widget.site.SitePanel;
import com.google.gwt.core.client.GWT;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-8
 * Time: 13:18:48
 * To change this template use File | Settings | File Templates.
 */
public class SiteView extends View {

    private Window window = new Window();
    
    private SiteListPanel siteListPanel ;

    WcmMessages msgs = GWT.create(WcmMessages.class);
    
    public SiteView(Controller controller) {
        super(controller);
    }

    @Override
    protected void handleEvent(AppEvent appEvent) {
        AppEventType etype = (AppEventType)appEvent.getType();
        if(AppEvents.SITE_MANAGER == appEvent.getType()){
            siteListPanel = new SiteListPanel();
            Dispatcher.forwardEvent(
                    AppEvents.OTHER_MANAGER_CHANGE_EVENT,
                    new Entry(
                            etype.getId(),
                            msgs.site_manager_header(),
                            AppState.OWNER_OTHER_MANAGER_CALLBACK,
                            siteListPanel,
                            true,
                            true,
                            etype,
                            false));
        } else if(AppEvents.SITE_MANAGER_CANCEL == appEvent.getType() || AppEvents.SITE_MANAGER_SUCCESS == appEvent.getType()){
            window.hide();
            siteListPanel.refresh();
        } else if(AppEvents.SITE_MANAGER_ADD == appEvent.getType()){
            Site c = appEvent.getData();
            SitePanel editsite = new SitePanel(c);
            window.removeAll();
            window.add(editsite);
            window.setHeading(msgs.site_add_header());
            window.setWidth("500px");
            window.show();
        } else if(AppEvents.SITE_MANAGER_EDIT == appEvent.getType()){
            Site c = appEvent.getData();
            SitePanel editsite = new SitePanel(c);
            window.removeAll();
            window.add(editsite);
            window.setHeading(msgs.site_edit_header());
            window.setWidth("500px");
            window.show();
        } else if(AppEvents.SITE_MANAGER_DELETE == appEvent.getType()){

        }
    }
}
