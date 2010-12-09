package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.WcmMessages;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 11:11:19
 * To change this template use File | Settings | File Templates.
 */
public class AppView extends View {

    public static final String WEST = "west";
    public static final String EAST = "east";
    public static final String CENTER = "center";
    public static final String NORTH = "north";
    public static final String SOUTH = "south";

    private Viewport viewport;
    private ContentPanel west;
    private HtmlContainer north;
    private ContentPanel center;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public AppView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent appEvent) {

    }

    @Override
    protected void initialize() {
        viewport = new Viewport();
        viewport.setLayout(new BorderLayout());

        createCenter();
        createNorth();
        createWest();

        RootPanel.get().add(viewport);
    }

    private void createNorth() {
        StringBuffer sb = new StringBuffer();
        sb.append("<table background=\"images/topbg.gif\" border='0' cellpadding='0' cellspacing='0' width='100%' height='100%'><tr><td><img src=\"images/logo1.gif\"></td><td></td><td valign='bottom'> <div class=b1 align=right></div></td></tr></table>");
        north = new HtmlContainer(sb.toString());

        HTML logout = new HTML("<a href='#' class='logout'>" + msgs.logout() + "</a>");
        north.add(logout, "div.b1");

        final Listener<MessageBoxEvent> listener = new Listener<MessageBoxEvent>() {
            public void handleEvent(MessageBoxEvent ce) {
                Button btn = ce.getButtonClicked();
                if (msgs.yes().equals(btn.getText())) {
                }
            }
        };
        logout.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                MessageBox.confirm(msgs.tips(), msgs.logout_confirm(), listener);
            }
        });
        BorderLayoutData data = new BorderLayoutData(Style.LayoutRegion.NORTH, 33);
        data.setMargins(new Margins());
        viewport.add(north, data);
        Registry.register(AppView.NORTH, north);
    }


    private void createWest() {
        west = new ContentPanel();
        BorderLayoutData westData = new BorderLayoutData(Style.LayoutRegion.WEST, 150, 100, 300);
        westData.setSplit(true);
        westData.setCollapsible(true);
        westData.setMargins(new Margins(5));
        westData.setFloatable(true);
        west.setBodyBorder(false);
        west.setBodyStyleName("navigation");
        west.setLayoutOnChange(true);
        viewport.add(west, westData);
        Registry.register(AppView.WEST, west);
    }

    private void createCenter() {
        center = new ContentPanel();

        center.setBorders(false);
        center.setHeaderVisible(false);
        center.setLayout(new FitLayout());

        BorderLayoutData centerData = new BorderLayoutData(Style.LayoutRegion.CENTER);
        centerData.setMargins(new Margins(5, 0, 5, 0));
        center.setLayoutOnChange(true);
        viewport.add(center, centerData);
        Registry.register(AppView.CENTER, center);
    }

    public static native void refeshBrowser() /*-{
        $wnd.location.reload(true);
    }-*/;
}
