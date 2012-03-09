package com.opencms.wcm.client.mvc.content;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.*;
import com.opencms.wcm.client.model.content.Content;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-8
 * Time: 8:53:27
 * To change this template use File | Settings | File Templates.
 */
public class ContentController extends Controller {

    private ContentView view;

    final WcmMessages msgs = GWT.create(WcmMessages.class);

    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);

    public ContentController() {
        this.registerEventTypes(AppEvents.CONTENT_MANAGER);
        this.registerEventTypes(AppEvents.CONTENT_AUDITING_MANAGER);
        this.registerEventTypes(AppEvents.CONTENT_PUBLISHING_MANAGER);
        this.registerEventTypes(AppEvents.CONTENT_MANAGER_ALL);
        this.registerEventTypes(AppEvents.CONTENT_MANAGER_ADD);
        this.registerEventTypes(AppEvents.CONTENT_MANAGER_EDIT);
        this.registerEventTypes(AppEvents.CONTENT_MANAGER_DELETE);
        this.registerEventTypes(AppEvents.CONTENT_MANAGER_SAVE);
        this.registerEventTypes(AppEvents.CONTENT_MANAGER_CANCEL);
        this.registerEventTypes(AppEvents.CONTENT_MANAGER_SUCCESS);
    }

    @Override
    public void handleEvent(AppEvent appEvent) {
        if (AppEvents.CONTENT_MANAGER == appEvent.getType()
                || AppEvents.CONTENT_MANAGER_CANCEL == appEvent.getType()
                || AppEvents.CONTENT_MANAGER_SUCCESS == appEvent.getType()
                || AppEvents.CONTENT_AUDITING_MANAGER == appEvent.getType()
                || AppEvents.CONTENT_PUBLISHING_MANAGER == appEvent.getType()
                || AppEvents.CONTENT_MANAGER_ALL == appEvent.getType()) {
            this.forwardToView(view, appEvent);
        } else if(AppEvents.CONTENT_MANAGER_SAVE == appEvent.getType()){
            Content content = appEvent.getData();
            service.addOrUpdateContent(content, new AsyncCallback(){
                @Override
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                @Override
                public void onSuccess(Object o) {
                    Boolean re = (Boolean)o;
                    if(re){
                        MessageBox.alert(msgs.tips(), msgs.save_success(), null);
                        AppEvent event = new AppEvent(AppEvents.CONTENT_MANAGER_SUCCESS);
                        forwardToView(view, event);
                    } else{
                        MessageBox.alert(msgs.warn(), msgs.save_fail(), null);
                    }
                }
            });
        } else if(AppEvents.CONTENT_MANAGER_ADD == appEvent.getType()){
            final String type = appEvent.getData();
            service.getContentById(null, AppState.westTreeItemObj, new AsyncCallback(){
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Content content = (Content)o;
                    content.setType(type);
                    AppEvent event = new AppEvent(AppEvents.CONTENT_MANAGER_ADD, content);
                    forwardToView(view, event);
                }
            });
        } else if(AppEvents.CONTENT_MANAGER_EDIT == appEvent.getType()){
        	Long id = appEvent.getData();
            service.getContentById(id, AppState.westTreeItemObj, new AsyncCallback(){
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Content category = (Content)o;
                    AppEvent event = new AppEvent(AppEvents.CONTENT_MANAGER_EDIT, category);
                    forwardToView(view, event);
                }
            });
        }
    }

    @Override
    protected void initialize() {
        view = new ContentView(this);
    }
}
