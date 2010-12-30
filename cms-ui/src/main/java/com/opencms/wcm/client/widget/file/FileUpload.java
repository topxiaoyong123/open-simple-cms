package com.opencms.wcm.client.widget.file;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HTML;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.file.WcmFile;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 12:18:05
 * To change this template use File | Settings | File Templates.
 */
public class FileUpload extends Dialog {

    final WcmMessages msgs = GWT.create(WcmMessages.class);

    public FileUpload(final WcmFile f){
        this.setId("img-chooser-dlg");
        this.setHeading(msgs.file_manager_upload_header());
        this.setMinWidth(500);
        this.setMinHeight(300);
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setBodyStyle("border: none;background: none");
        this.setBodyBorder(false);
        this.setButtons("");
        this.setScrollMode(Style.Scroll.AUTO);


        HTML html = new HTML("<form id=\"form1\" action=\"fileupload\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                "\t\t<table>\n" +
                "\t\t\t<tr valign=\"top\">\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<div>\n" +
                "\t\t\t\t\t\t<div class=\"fieldset flash\" id=\"fsUploadProgress1\">\n" +
                "\t\t\t\t\t\t\t<span class=\"legend\">" + msgs.file_manager_upload_header() +"</span>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div style=\"padding-left: 5px;\">\n" +
                "\t\t\t\t\t\t\t<span id=\"spanButtonPlaceholder1\"></span>\n" +
                "\t\t\t\t\t\t\t<input id=\"btnCancel1\" type=\"button\" value=\"Cancel Uploads\" onclick=\"cancelQueue(upload1);\" disabled=\"disabled\" style=\"margin-left: 2px; height: 22px; font-size: 8pt;\" />\n" +
                "\t\t\t\t\t\t\t<br />\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table>\n" +
                "\t</form>");
        this.add(html);
        final Button upload = new Button(msgs.file_manager_upload_start());
        final Button close = new Button(msgs.cancel());
        upload.addListener(Events.Select, new Listener<ComponentEvent>(){
            public void handleEvent(ComponentEvent componentEvent) {
                startUpload();
            }
        });
        close.addListener(Events.Select, new Listener<ComponentEvent>(){
            public void handleEvent(ComponentEvent componentEvent) {
                FileUpload.this.hide();
            }
        });
        this.addButton(upload);
        this.addButton(close);
    }

    public native void initJS(WcmFile f) /*-{
        alert(f.@com.opencms.wcm.client.model.file.WcmFile::getPath()());
        $wnd.upload1 = new $wnd.SWFUpload({
				// Backend Settings
				upload_url: "fileupload",
				post_params: {"path" : f.@com.opencms.wcm.client.model.file.WcmFile::getPath()()},

				// File Upload Settings
				file_size_limit : "102400",	// 100MB
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : "10",
				file_queue_limit : "0",

				// Event Handler Settings (all my handlers are in the Handler.js file)
				file_dialog_start_handler : $wnd.fileDialogStart,
				file_queued_handler : $wnd.fileQueued,
				file_queue_error_handler : $wnd.fileQueueError,
				file_dialog_complete_handler : $wnd.fileDialogComplete,
				upload_start_handler : $wnd.uploadStart,
				upload_progress_handler : $wnd.uploadProgress,
				upload_error_handler : $wnd.uploadError,
				upload_success_handler : $wnd.uploadSuccess,
				upload_complete_handler : $wnd.uploadComplete,

				// Button Settings
				button_image_url : "swfupload/XPButtonUploadText_61x22.png",
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width: 61,
				button_height: 22,

				// Flash Settings
				flash_url : "swfupload/swfupload.swf",


				custom_settings : {
					progressTarget : "fsUploadProgress1",
					cancelButtonId : "btnCancel1"
				},

				// Debug Settings
				debug: false
			});
    }-*/;

    private native void startUpload()/*-{
        $wnd.upload1.startUpload();
    }-*/;

}

