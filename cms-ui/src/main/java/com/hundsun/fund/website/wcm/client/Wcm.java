package com.hundsun.fund.website.wcm.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.hundsun.fund.website.wcm.client.mvc.*;

/**
 * Created by IntelliJ IDEA. User: yingxiong Date: 2009-3-18 Time: 13:46:25
 */
public class Wcm implements EntryPoint {
    public void onModuleLoad() {
        GXT.hideLoadingPanel("loading");

        WcmServiceAsync wService = WcmServiceAsync.Util.getInstance();
        Registry.register(WcmService.SERVICE, wService);

        final Dispatcher dispatcher = Dispatcher.get();
        dispatcher.addController(new AppController());
        dispatcher.addController(new NavigationController());
        dispatcher.addController(new BodyController());
        dispatcher.addController(new OtherController());

        dispatcher.addController(new ArticleListController());

        dispatcher.dispatch(AppEvents.INIT_UI);
    }
}
