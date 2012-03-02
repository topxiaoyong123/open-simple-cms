package com.opencms.wcm.client.mvc.file;

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
import com.opencms.wcm.client.model.file.WcmFile;

public class FileController extends Controller {

	final WcmMessages msgs = GWT.create(WcmMessages.class);
    
    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);
    
    private FileView view;
    
    public FileController() {
    	registerEventTypes(AppEvents.FILE_MANAGER);
    	registerEventTypes(AppEvents.FILE_MANAGER_EDIT);
    	registerEventTypes(AppEvents.FILE_MANAGER_SAVE);
    	registerEventTypes(AppEvents.FILE_MANAGER_CANCEL);
    	registerEventTypes(AppEvents.FILE_MANAGER_SUCCESS);
    }
    
	@Override
	public void handleEvent(AppEvent appEvent) {
		if(AppEvents.FILE_MANAGER == appEvent.getType()
				|| AppEvents.FILE_MANAGER_CANCEL == appEvent.getType()
				|| AppEvents.FILE_MANAGER_SUCCESS == appEvent.getType()){
            this.forwardToView(view, appEvent);
        }
		if(AppEvents.FILE_MANAGER_EDIT == appEvent.getType()){
			service.editFile((WcmFile)appEvent.getData(), new AsyncCallback<WcmFile>(){
				@Override
				public void onFailure(Throwable throwable) {
					MessageBox.alert(msgs.error(), throwable.getMessage(), null);
				}
				@Override
				public void onSuccess(WcmFile file) {
					AppEvent event = new AppEvent(AppEvents.FILE_MANAGER_EDIT, file);
					forwardToView(view, event);
				}
        	});
        }
		if(AppEvents.FILE_MANAGER_SAVE == appEvent.getType()){
			service.saveFile((WcmFile)appEvent.getData(), new AsyncCallback<Boolean>(){
				@Override
				public void onFailure(Throwable throwable) {
					MessageBox.alert(msgs.error(), throwable.getMessage(), null);
				}
				@Override
				public void onSuccess(Boolean re) {
					if(re){
                        MessageBox.alert(msgs.tips(), msgs.save_success(), null);
                        AppEvent event = new AppEvent(AppEvents.FILE_MANAGER_SUCCESS);
                        forwardToView(view, event);
                    } else{
                        MessageBox.alert(msgs.warn(), msgs.save_fail(), null);
                    }
				}
        	});
        }
	}
	
	@Override
	protected void initialize() {
		view = new FileView(this);
	}
}
