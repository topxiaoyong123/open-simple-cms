package com.opencms.wcm.client.mvc.site;

import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.opencms.wcm.client.*;
import com.opencms.wcm.client.model.site.Site;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-8
 * Time: 13:18:33
 * To change this template use File | Settings | File Templates.
 */
public class SiteController extends Controller {

    final WcmMessages msgs = GWT.create(WcmMessages.class);
    
    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);
    
    private SiteView view;

    public SiteController() {
        this.registerEventTypes(AppEvents.SITE_MANAGER);
        this.registerEventTypes(AppEvents.SITE_MANAGER_ADD);
        this.registerEventTypes(AppEvents.SITE_MANAGER_EDIT);
        this.registerEventTypes(AppEvents.SITE_MANAGER_SAVE);
        this.registerEventTypes(AppEvents.SITE_MANAGER_CANCEL);
        this.registerEventTypes(AppEvents.SITE_MANAGER_SUCCESS);
        this.registerEventTypes(AppEvents.SITE_MANAGER_DELETE);
    }

    @Override
    public void handleEvent(AppEvent appEvent) {
        if(AppEvents.SITE_MANAGER == appEvent.getType() || AppEvents.SITE_MANAGER_CANCEL == appEvent.getType() || AppEvents.SITE_MANAGER_SUCCESS == appEvent.getType()){
            this.forwardToView(view, appEvent);
        } else if(AppEvents.SITE_MANAGER_SAVE == appEvent.getType()){
            Site site = appEvent.getData();
            service.addOrUpdateSite(site, new AsyncCallback(){
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Boolean re = (Boolean)o;
                    if(re){
                        MessageBox.alert(msgs.tips(), msgs.save_success(), null);
                        AppEvent event = new AppEvent(AppEvents.SITE_MANAGER_SUCCESS);
                        forwardToView(view, event);
                    } else{
                        MessageBox.alert(msgs.warn(), msgs.save_fail(), null);    
                    }
                }
            });
        } else if(AppEvents.SITE_MANAGER_ADD == appEvent.getType()){
            service.getSiteById(null, new AsyncCallback(){
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Site site = (Site)o;
                    AppEvent event = new AppEvent(AppEvents.SITE_MANAGER_ADD, site);
                    forwardToView(view, event); 
                }
            });
        } else if(AppEvents.SITE_MANAGER_EDIT == appEvent.getType()){
            String id = appEvent.getData();
            service.getSiteById(id, new AsyncCallback(){
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Site site = (Site)o;
                    AppEvent event = new AppEvent(AppEvents.SITE_MANAGER_EDIT, site);
                    forwardToView(view, event);
                }
            });
        }
    }

    @Override
    protected void initialize() {
        this.view = new SiteView(this);
    }
}
