package com.opencms.wcm.client.mvc.content;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.WcmServiceAsync;
import com.opencms.wcm.client.model.content.Content;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-27
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
public class ContentAuditingController extends Controller {

    private ContentAuditingView view;

    final WcmMessages msgs = GWT.create(WcmMessages.class);

    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);

    public ContentAuditingController() {
        this.registerEventTypes(AppEvents.CONTENT_AUDITING_MANAGER);
        this.registerEventTypes(AppEvents.CONTENT_AUDITING_MANAGER_PASS);
        this.registerEventTypes(AppEvents.CONTENT_AUDITING_MANAGER_REJECT);
        this.registerEventTypes(AppEvents.CONTENT_AUDITING_MANAGER_SUCCESS);
    }

    public void handleEvent(AppEvent appEvent) {
        if (AppEvents.CONTENT_AUDITING_MANAGER == appEvent.getType()){
            forwardToView(view, appEvent);
        } else if(AppEvents.CONTENT_AUDITING_MANAGER_PASS == appEvent.getType()){
            List<Content> contents = (List<Content>)appEvent.getData();
            service.auditingContents(contents, true, new AsyncCallback() {
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Boolean re = (Boolean)o;
                    if(re){
                        MessageBox.alert(msgs.tips(), msgs.operation_success(), null);
                        AppEvent event = new AppEvent(AppEvents.CONTENT_AUDITING_MANAGER_SUCCESS);
                        forwardToView(view, event);
                    } else{
                        MessageBox.alert(msgs.warn(), msgs.operation_fail(), null);
                    }
                }
            });
        } else if(AppEvents.CONTENT_AUDITING_MANAGER_REJECT == appEvent.getType()){
            List<Content> contents = (List<Content>)appEvent.getData();
            service.auditingContents(contents, false, new AsyncCallback() {
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Boolean re = (Boolean)o;
                    if(re){
                        MessageBox.alert(msgs.tips(), msgs.operation_success(), null);
                        AppEvent event = new AppEvent(AppEvents.CONTENT_AUDITING_MANAGER_SUCCESS);
                        forwardToView(view, event);
                    } else{
                        MessageBox.alert(msgs.warn(), msgs.operation_fail(), null);
                    }
                }
            });
        }
    }

    protected void initialize() {
        view = new ContentAuditingView(this);
    }
}
