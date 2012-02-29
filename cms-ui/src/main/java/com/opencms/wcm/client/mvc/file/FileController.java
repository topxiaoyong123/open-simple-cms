package com.opencms.wcm.client.mvc.file;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.WcmServiceAsync;

public class FileController extends Controller {

	final WcmMessages msgs = GWT.create(WcmMessages.class);
    
    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);
    
    private FileView view;
    
	@Override
	public void handleEvent(AppEvent event) {

	}
	@Override
	protected void initialize() {
		view = new FileView(this);
	}
}
