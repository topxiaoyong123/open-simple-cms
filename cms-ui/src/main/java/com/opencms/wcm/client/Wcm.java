package com.opencms.wcm.client;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.mvc.*;
import com.opencms.wcm.client.mvc.category.CategoryController;
import com.opencms.wcm.client.mvc.site.SiteController;
import com.opencms.wcm.client.mvc.content.ContentController;

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

        final WcmServiceAsync wService = WcmServiceAsync.Util.getInstance();
        Registry.register(WcmService.SERVICE, wService);

        final Dispatcher dispatcher = Dispatcher.get();
        dispatcher.addController(new AppController());
        dispatcher.addController(new NavigationController());
        dispatcher.addController(new BodyController());
        dispatcher.addController(new OtherController());
        
        dispatcher.addController(new SiteController());
        dispatcher.addController(new CategoryController());
        dispatcher.addController(new ContentController());

        dispatcher.addController(new LoginController());
        wService.setLocale(getLocale(), new AsyncCallback(){
            public void onFailure(Throwable throwable) {
                wService.checkLogin(new AsyncCallback(){
                    public void onFailure(Throwable throwable) {
                        dispatcher.dispatch(AppEvents.LOGIN);
                    }
                    public void onSuccess(Object o) {
                        dispatcher.dispatch(AppEvents.LOGIN_OK);
                    }
                });
            }
            public void onSuccess(Object o) {
                wService.checkLogin(new AsyncCallback(){
                    public void onFailure(Throwable throwable) {
                        dispatcher.dispatch(AppEvents.LOGIN);
                    }
                    public void onSuccess(Object o) {
                        dispatcher.dispatch(AppEvents.LOGIN_OK);
                    }
                });
            }
        });

    }

    public native String getLocale()/*-{
        var locale = $wnd.getparastr("locale");
        return locale;
    }-*/;
}
