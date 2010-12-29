package com.opencms.wcm.client.mvc.category;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.*;
import com.opencms.wcm.client.model.category.Category;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-10
 * Time: 14:02:14
 * To change this template use File | Settings | File Templates.
 */
public class CategoryController extends Controller {

    final WcmMessages msgs = GWT.create(WcmMessages.class);

    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);

    private CategoryView view;

    public CategoryController() {
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER);
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER_ADD);
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER_EDIT);
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER_SAVE);
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER_CANCEL);
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER_SUCCESS);
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER_DELETE);
    }

    @Override
    public void handleEvent(AppEvent appEvent) {
        if(AppEvents.CATEGORY_MANAGER == appEvent.getType() || AppEvents.CATEGORY_MANAGER_CANCEL == appEvent.getType() || AppEvents.CATEGORY_MANAGER_SUCCESS == appEvent.getType()){
            this.forwardToView(view, appEvent);
        } else if(AppEvents.CATEGORY_MANAGER_SAVE == appEvent.getType()){
            Category category = appEvent.getData();
            service.addOrUpdateCategory(category, new AsyncCallback(){
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Boolean re = (Boolean)o;
                    if(re){
                        MessageBox.alert(msgs.tips(), msgs.save_success(), null);
                        AppEvent event = new AppEvent(AppEvents.CATEGORY_MANAGER_SUCCESS);
                        forwardToView(view, event);
                    } else{
                        MessageBox.alert(msgs.warn(), msgs.save_fail(), null);
                    }
                }
            });
        } else if(AppEvents.CATEGORY_MANAGER_ADD == appEvent.getType()){
            service.getCategoryById(null, AppState.westTreeItemObj, new AsyncCallback(){
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Category category = (Category)o;
                    AppEvent event = new AppEvent(AppEvents.CATEGORY_MANAGER_ADD, category);
                    forwardToView(view, event);
                }
            });
        } else if(AppEvents.CATEGORY_MANAGER_EDIT == appEvent.getType()){
            String id = appEvent.getData();
            service.getCategoryById(id, AppState.westTreeItemObj, new AsyncCallback(){
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }
                public void onSuccess(Object o) {
                    Category category = (Category)o;
                    AppEvent event = new AppEvent(AppEvents.CATEGORY_MANAGER_EDIT, category);
                    forwardToView(view, event);
                }
            });
        }
    }

    @Override
    protected void initialize() {
        view = new CategoryView(this);
    }
}
