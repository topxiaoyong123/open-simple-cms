package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.WcmServiceAsync;
import com.opencms.wcm.client.model.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-5
 * Time: 9:10:28
 * To change this template use File | Settings | File Templates.
 */
public class LoginController extends Controller {

    private LoginView view;

    final WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);

    public LoginController() {
        this.registerEventTypes(AppEvents.LOGIN);
        this.registerEventTypes(AppEvents.LOGIN_OK);
        this.registerEventTypes(AppEvents.LOGIN_FAIL);
    }

    public void handleEvent(AppEvent appEvent) {
        User u = appEvent.getData();
        if (u != null) {
            service.login(u, new AsyncCallback() {
                public void onFailure(Throwable throwable) {
                    MessageBox.alert("提示", throwable.getMessage(), new Listener() {
                        public void handleEvent(BaseEvent baseEvent) {
                            AppEvent e = new AppEvent(AppEvents.LOGIN_FAIL);
                            forwardToView(view, e);
                        }
                    });
                }

                public void onSuccess(Object o) {
                    AppEvent e = new AppEvent(AppEvents.LOGIN_OK);
                    forwardToView(view, e);
                }
            });
        } else {
            forwardToView(view, appEvent);
        }
    }

    @Override
    protected void initialize() {
        view = new LoginView(this);
    }
}
