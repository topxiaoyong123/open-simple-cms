package com.opencms.wcm.client.mvc.file;

import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.AppEventType;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.widget.file.FilePanel;
import com.opencms.wcm.client.widget.site.SiteListPanel;

public class FileView extends View {

	private FilePanel filePanel;
	
	WcmMessages msgs = GWT.create(WcmMessages.class);
	
	public FileView(Controller controller) {
		super(controller);
	}

	@Override
	protected void handleEvent(AppEvent appEvent) {
		AppEventType etype = (AppEventType)appEvent.getType();
        if(AppEvents.FILE_MANAGER == appEvent.getType()){
        	filePanel = new FilePanel();
            Dispatcher.forwardEvent(
                    AppEvents.OTHER_MANAGER_CHANGE_EVENT,
                    new Entry(
                            etype.getId(),
                            msgs.site_manager_header(),
                            AppState.OWNER_OTHER_MANAGER_CALLBACK,
                            filePanel,
                            true,
                            true,
                            etype,
                            false));
        }
	}

}
