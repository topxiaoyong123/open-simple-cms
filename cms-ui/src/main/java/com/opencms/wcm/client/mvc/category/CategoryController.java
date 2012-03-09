package com.opencms.wcm.client.mvc.category;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.*;
import com.opencms.wcm.client.model.category.Category;

import java.util.List;

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
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER_PUBLISHING);
        this.registerEventTypes(AppEvents.CATEGORY_MANAGER_PUBLISHING_SUCCESS);
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
            Long id = appEvent.getData();
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
        } else if (AppEvents.CATEGORY_MANAGER_PUBLISHING == appEvent.getType()) {
            List<Category> categories = (List<Category>) appEvent.getData();
            final MessageBox box = MessageBox.progress(msgs.please_wait(), msgs.publishing_process(), msgs.publishing_process_begin());
            final ProgressBar bar = box.getProgressBar();
            final Timer t = new Timer() {
                boolean finish = false;

                public void run() {
                    if (finish) {
                        cancel();
                        box.close();
                    }
                    service.getPublishingProcess(new AsyncCallback() {
                        public void onFailure(Throwable caught) {
                            cancel();
                            box.close();
                        }
                        public void onSuccess(Object result) {
                            int[] re = (int[]) result;
                            double rate = 0;
                            if (re != null) {
                                int finished = re[0];
                                int total = re[1];
                                if (finished == -1) {
                                    finish = true;
                                    bar.updateText(msgs.publishing_process_exception());
                                } else if (total != 0) {
                                    rate = ((double) finished) / total;
                                    if (rate == 1) {
                                        finish = true;
                                        bar.updateProgress(rate, msgs.publishing_process_end());
                                    } else {
                                        bar.updateProgress(rate, finished + "/" + total);
                                    }
                                }

                            }
                        }
                    });
                }
            };
            t.scheduleRepeating(500);
            service.publishingCategoryss(categories, true, new AsyncCallback() {
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }

                public void onSuccess(Object o) {
                    Boolean re = (Boolean) o;
                    if (re) {
                        MessageBox.alert(msgs.tips(), msgs.operation_success(), null);
                        AppEvent event = new AppEvent(AppEvents.CATEGORY_MANAGER_PUBLISHING_SUCCESS);
                        forwardToView(view, event);
                    } else {
                        MessageBox.alert(msgs.warn(), msgs.operation_fail(), null);
                    }
                }
            });
        } else {
            this.forwardToView(view, appEvent);
        }
    }

    @Override
    protected void initialize() {
        view = new CategoryView(this);
    }
}
