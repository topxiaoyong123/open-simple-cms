package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.widget.LoginDialog;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-5
 * Time: 9:10:35
 * To change this template use File | Settings | File Templates.
 */
public class LoginView extends View {

    public LoginView(Controller controller) {
        super(controller);
    }

    private LoginDialog dialog = new LoginDialog();

    protected void handleEvent(AppEvent appEvent) {

        if (appEvent.getType() == AppEvents.LOGIN) {
            showDialog(dialog);
        }
        if (appEvent.getType() == AppEvents.LOGIN_FAIL) {
            dialog.hide();
            dialog = new LoginDialog();
            showDialog(dialog);
        }
        if (appEvent.getType() == AppEvents.LOGIN_OK) {
            Dispatcher.forwardEvent(AppEvents.INIT_UI);
            hideDialog(dialog);
        }
    }

    private void showDialog(Dialog dialog){
        dialog.setClosable(false);
        dialog.show();        
    }

    private void hideDialog(Dialog dialog){
        dialog.hide();        
    }

    @Override
    protected void initialize() {
    }
}
