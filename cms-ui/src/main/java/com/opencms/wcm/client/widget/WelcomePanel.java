package com.opencms.wcm.client.widget;

import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.HTML;
import com.opencms.wcm.client.WcmMessages;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 15:08:12
 * To change this template use File | Settings | File Templates.
 */
public class WelcomePanel extends ContentPanel {

    WcmMessages msgs = GWT.create(WcmMessages.class);
    
    public WelcomePanel() {
        this.setLayout(new FitLayout());
        this.setHeaderVisible(false);
        this.setFrame(false);
        this.setBodyBorder(false);

         ToolBar toolBars = new ToolBar();
        Button addBookmark = new Button(msgs.wcm_bookmark());
        addBookmark.setIconStyle("welcome-bookmark");
        addBookmark.addListener(Events.Select, new Listener<ComponentEvent>(){
            public void handleEvent(ComponentEvent componentEvent) {
                addBookmark(msgs.wcm_bookmark_title());
            }
        });
        Button about = new Button(msgs.wcm_about());
        about.setIconStyle("welcome-about");
        about.addListener(Events.Select, new Listener<ComponentEvent>(){
            public void handleEvent(ComponentEvent componentEvent) {
                final Window window = new Window();
                window.setButtonAlign(Style.HorizontalAlignment.RIGHT);
                window.setHeading(msgs.wcm_about());
                window.setModal(true);
                window.setSize("490px", "230px");
                window.setResizable(false);
                window.setLayout(new RowLayout(Style.Orientation.VERTICAL));
                window.add(new HTML("<div style=\"background:url(images/about_bg1.gif) no-repeat; width:488px; padding:106px 0 0 0\">\n" +
                        "    <div style=\"border-top:1px solid #ccc; background:#efefef; color:#666; padding:10px 20px; height:50px; line-height:22px; position:relative\">\n" +
                        "    <span style=\"font-family:Verdana; font-weight:bold; position:absolute; top:-53px; left:23px\">" + msgs.wcm_version() + "</span>\n" +
                        msgs.wcm_copyright() + 
                        "<br />\n" +
                        "        <a class=\"address\" target=\"_blank\" href=\"#\">http://www.demo.com</a>\n" +
                        "    </div>\n" +
                        "</div>"));
                Button b = new Button(msgs.ok());
                b.addListener(Events.Select, new Listener<ComponentEvent>(){
                    public void handleEvent(ComponentEvent componentEvent) {
                        window.hide();
                    }
                });
                window.addButton(b);
                window.show();
            }
        });
        toolBars.add(addBookmark);
        toolBars.add(new SeparatorToolItem());
        toolBars.add(about);

        this.setTopComponent(toolBars);

        final HTML welcome = new HTML();
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, GWT.getHostPageBaseURL() + "info.jsp?temp=" + Math.random());
        builder.setHeader("Content-type", "application/x-www-form-urlencoded");
        builder.setCallback(new RequestCallback() {
            public void onResponseReceived(Request request, Response response) {
                welcome.setHTML(response.getText());
            }
            public void onError(Request request, Throwable throwable) {
            }
        });
        try {
            builder.send();
        } catch (RequestException e) {
            e.printStackTrace();
        }
        this.add(welcome);
    }

    public native void addBookmark(String title)/*-{
        var url = top.location.href;
        if (window.sidebar) {
            window.sidebar.addPanel(title, url,"");
        } else if( document.all ) {
            window.external.AddFavorite( url, title);
        } else if( window.opera && window.print ) {
            return true;
        }
    }-*/;

}
