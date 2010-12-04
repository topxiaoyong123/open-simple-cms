package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.opencms.wcm.client.WcmServiceAsync;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.model.WcmApp;
import com.opencms.wcm.client.model.Entry;
import com.google.gwt.user.client.ui.Frame;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 20:48:18
 * To change this template use File | Settings | File Templates.
 */
public class OtherView extends View {

    final WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);// 定义所要引用的异步服务

    public OtherView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent appEvent) {
        final Entry entry = (Entry) appEvent.getData();
        Map<Long, WcmApp> wcmApps = AppState.wcmApps;
        WcmApp app = wcmApps.get(Long.valueOf(entry.getId()));
        if (app != null) {      
            Frame frame = new Frame(app.getUrl());
            ContentPanel framePanel = new ContentPanel();
            framePanel.setHeaderVisible(false);
            framePanel.setLayout(new FitLayout());
            framePanel.add(frame);
            framePanel.setBodyBorder(false);
            Dispatcher.forwardEvent(
                    entry.getEventType(),
                    new Entry(
                            String.valueOf(app.getId()),
                            app.getName(),
                            AppState.OWNER_OTHER_MANAGER_CALLBACK,
                            framePanel,
                            true,
                            true,
                            entry.getEventType(),
                            true));
        }
    }
}
