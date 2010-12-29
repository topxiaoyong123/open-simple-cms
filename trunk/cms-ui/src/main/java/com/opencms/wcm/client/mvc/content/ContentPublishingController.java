package com.opencms.wcm.client.mvc.content;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
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
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
public class ContentPublishingController extends Controller {

    private ContentPublishingView view;

    final WcmMessages msgs = GWT.create(WcmMessages.class);

    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);

    public ContentPublishingController() {
        registerEventTypes(AppEvents.CONTENT_PUBLISHING_MANAGER);
        registerEventTypes(AppEvents.CONTENT_PUBLISHING_MANAGER_DO);
        registerEventTypes(AppEvents.CONTENT_PUBLISHING_MANAGER_SUCCESS);
    }

    public void handleEvent(AppEvent appEvent) {
        if (AppEvents.CONTENT_PUBLISHING_MANAGER == appEvent.getType()) {
            forwardToView(view, appEvent);
        } else if (AppEvents.CONTENT_PUBLISHING_MANAGER_DO == appEvent.getType()) {
            List<Content> contents = (List<Content>) appEvent.getData();
            final MessageBox box = MessageBox.progress(msgs.please_wait(), msgs.publishing_process(), msgs.publishing_process_begin());
            final ProgressBar bar = box.getProgressBar();
            final Timer t = new Timer() {
                boolean finish = false;

                public void run() {
                    if (finish) {
                        cancel();
                        box.close();
                    }
                    service.getPublishingContentsProcess(new AsyncCallback() {
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
            service.publishingContents(contents, true, new AsyncCallback() {
                public void onFailure(Throwable throwable) {
                    MessageBox.alert(msgs.error(), throwable.getMessage(), null);
                }

                public void onSuccess(Object o) {
                    Boolean re = (Boolean) o;
                    if (re) {
                        MessageBox.alert(msgs.tips(), msgs.operation_success(), null);
                        AppEvent event = new AppEvent(AppEvents.CONTENT_PUBLISHING_MANAGER_SUCCESS);
                        forwardToView(view, event);
                    } else {
                        MessageBox.alert(msgs.warn(), msgs.operation_fail(), null);
                    }
                }
            });
        }
    }

    protected void initialize() {
        view = new ContentPublishingView(this);
    }
}
