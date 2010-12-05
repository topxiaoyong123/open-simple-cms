package com.opencms.wcm.client.widget;

import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.KeyNav;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.model.User;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-5
 * Time: 8:57:43
 * To change this template use File | Settings | File Templates.
 */
public class LoginDialog extends Dialog {

    protected TextField<String> userName;
    protected TextField<String> password;
    protected TextField<String> checkcode;
    protected Button reset;
    protected Button login;
    private String url = GWT.getModuleBaseURL();

    public LoginDialog() {

        WcmMessages msgs = GWT.create(WcmMessages.class);

        String username_label = msgs.username();
        String password_label = msgs.password();

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(90);
        layout.setDefaultWidth(155);

        KeyNav<ComponentEvent> keyNav = new KeyNav<ComponentEvent>(this) {
            @Override
            public void handleEvent(ComponentEvent ce) {
                if (ce.getKeyCode() == 13) {
                    onSubmit();
                }
            }
        };

        setLayout(layout);
        setButtons("");
        setIconStyle("user");
        setHeading("WCM Login");
        setModal(true);
        setBodyBorder(true);
        setBodyStyle("padding: 8px;background: none");
        setWidth(300);
        setResizable(false);
        KeyListener keyListener = new KeyListener() {
            public void componentKeyUp(ComponentEvent event) {
                validate();
            }

        };
        userName = new TextField<String>();
        userName.setMinLength(4);
        userName.setFieldLabel(username_label);
        userName.addKeyListener(keyListener);
        add(userName);
        password = new TextField<String>();
        password.setMinLength(4);
        password.setPassword(true);
        password.setFieldLabel(password_label);
        password.addKeyListener(keyListener);
        add(password);
        checkcode = new TextField<String>();
        checkcode.setAllowBlank(false);
        checkcode.setFieldLabel(getDetailTemplate());
        add(checkcode);
        setFocusWidget(userName);
        addButtons();
    }

    protected void addButtons() {
        WcmMessages msgs = GWT.create(WcmMessages.class);

        reset = new Button(msgs.reset());
        reset.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                userName.reset();
                password.reset();
                validate();
                userName.focus();
            }
        });
        login = new Button(msgs.login());
        login.disable();
        login.addSelectionListener(new SelectionListener<ButtonEvent>() {
            public void componentSelected(ButtonEvent ce) {
                onSubmit();
            }
        });
        this.addButton(login);
        this.addButton(reset);
    }

    protected void onSubmit() {
        if (userName.isValid() == false) {
            MessageBox.alert("错误", "用户名不能为空且要大于4个字符", null);
            userName.setValue(null);
            setFocusWidget(userName);
        } else if (password.isValid() == false) {
            MessageBox.alert("错误", "密码不能为空", null);
            password.setValue(null);
            setFocusWidget(password);
        } else if (checkcode.isValid() == false) {
            MessageBox.alert("错误", "验证码不能为空", null);
            checkcode.setValue(null);
            setFocusWidget(checkcode);
        } else {
            WcmMessages msgs = GWT.create(WcmMessages.class);
            login.disable();
            Timer t = new Timer() {
                @Override
                public void run() {
                    LoginDialog.this.hide();
                }
            };
            t.schedule(2000);
            User u = new User();
            u.setUsername(userName.getRawValue());
            u.setPassword(password.getRawValue());
            u.setCheckcode(checkcode.getRawValue());
            AppEvent evt = new AppEvent(AppEvents.LOGIN, u);
            Dispatcher.forwardEvent(evt);
        }
    }

    protected boolean hasValue(TextField<String> field) {
        return field.getValue() != null && field.getValue().length() > 0;
    }

    protected void validate() {
        login.setEnabled(hasValue(userName) && hasValue(password) && password.getValue().length() > 3 && userName.getValue().length() > 3);
    }

    public native String getDetailTemplate() /*-{
    var url=this.@com.opencms.wcm.client.widget.LoginDialog::url;
    return [
    '<img src="'+url+'image">',
    ].join("")
    }-*/;

}
