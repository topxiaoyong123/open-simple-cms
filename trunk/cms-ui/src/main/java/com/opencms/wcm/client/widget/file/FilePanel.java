package com.opencms.wcm.client.widget.file;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.LabelField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.file.WcmFile;
import com.opencms.wcm.client.widget.extend.ExtendedHtmlEditor;

public class FilePanel extends FormPanel {

	WcmMessages msgs = GWT.create(WcmMessages.class);
	
	public FilePanel(final WcmFile file) {
		this.setFrame(true);
        this.setHeaderVisible(false);
        this.setBodyBorder(false);
        this.setLabelAlign(FormPanel.LabelAlign.TOP);
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);

        LabelField name = new LabelField();
        name.setValue(file.getPath());
        name.setName("name");
        name.setFieldLabel(msgs.file_name());
        this.add(name, new FormData("100%"));
        
		final ExtendedHtmlEditor a = new ExtendedHtmlEditor();
        a.setFieldLabel(msgs.file_content());
        a.setName("content");
        a.setHeight(200);
        a.setWidth(600);
        a.setValue(file.getContent());
        this.add(a, new FormData("100%"));
        
        Button save = new Button(msgs.ok());
        this.addButton(save);
        save.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                file.setContent(a.getValue());
                Dispatcher.forwardEvent(AppEvents.FILE_MANAGER_SAVE, file);
            }
        });
        Button cancel = new Button(msgs.cancel());
        cancel.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                Dispatcher.forwardEvent(AppEvents.FILE_MANAGER_CANCEL);
            }
        });
        this.addButton(cancel);
	}
}
