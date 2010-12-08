package com.opencms.wcm.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.mvc.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 11:14:10
 * To change this template use File | Settings | File Templates.
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
        
        dispatcher.addController(new SiteController());
        dispatcher.addController(new ContentListController());

        dispatcher.addController(new LoginController());

        wService.checkLogin(new AsyncCallback(){
            public void onFailure(Throwable throwable) {
                dispatcher.dispatch(AppEvents.LOGIN);    
            }
            public void onSuccess(Object o) {
                dispatcher.dispatch(AppEvents.LOGIN_OK);
            }
        });
    }
}
