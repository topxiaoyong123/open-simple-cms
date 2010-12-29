package com.opencms.wcm.client.widget.content;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.*;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.WcmServiceAsync;
import com.opencms.wcm.client.model.content.Content;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-27
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class ContentAuditingListPanel extends com.extjs.gxt.ui.client.widget.ContentPanel {

    final WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);// 定义所要引用的异步服务

    private RpcProxy<PagingLoadResult<Content>> proxy;

    private static Grid<BeanModel> grid = null;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public void refresh() {
        grid.getStore().getLoader().load();
    }

    public ContentAuditingListPanel(final Content content) {
        if (service == null) {
            MessageBox box = new MessageBox();
            box.setButtons(MessageBox.OK);
            box.setIcon(MessageBox.INFO);
            box.setTitle("Information");
            box.setMessage("No service detected");
            box.show();
            return;
        }
        proxy = new RpcProxy<PagingLoadResult<Content>>() {
            @Override
            protected void load(Object loadConfig,
                                AsyncCallback<PagingLoadResult<Content>> callback) {
                service.PagingLoadContentList((PagingLoadConfig) loadConfig, content, callback);
            }
        };
        final BasePagingLoader<PagingLoadResult<Content>> loader = new BasePagingLoader<PagingLoadResult<Content>>(proxy, new BeanModelReader());
        final ListStore<BeanModel> store = new ListStore<BeanModel>(loader);

        final PagingToolBar toolBar = new PagingToolBar(20);
        toolBar.bind(loader);
        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

        RowNumberer rnum = new RowNumberer();
        final CheckBoxSelectionModel<BeanModel> smm = new CheckBoxSelectionModel<BeanModel>();
        columns.add(smm.getColumn());
        columns.add(rnum);
        columns.add(new ColumnConfig("title", msgs.content_title(), 80));
        columns.add(new ColumnConfig("author", msgs.content_author(), 80));
        columns.add(new ColumnConfig("source", msgs.content_source(), 80));
        columns.add(new ColumnConfig("type", msgs.content_type(), 80));
        columns.add(new ColumnConfig("no", msgs.content_no(), 40));

        final ColumnModel cm = new ColumnModel(columns);
        grid = new Grid<BeanModel>(store, cm) {
            boolean loaded;
            @Override
            protected void onAttach() {
                super.onAttach();
                if(!loaded){
                    loaded = true;
                    loader.load(0, 20);
                }
            }
        };
        grid.setLoadMask(true);
        grid.setBorders(false);
        grid.setMinColumnWidth(80);
        grid.setAutoExpandColumn("title");

        grid.getView().setForceFit(true);
        grid.setSelectionModel(smm);
        grid.addPlugin(smm);
        grid.setWidth("100%");
        grid.setHeight("100%");
        grid.setAutoWidth(true);

        ToolBar toolBars = new ToolBar();
        Button pass = new Button(msgs.content_auditing_pass());
        pass.setIconStyle("icon-pass");
        pass.addSelectionListener(new SelectionListener() {
            public void componentSelected(ComponentEvent ce) {
                if(smm.getSelectedItems().size() <= 0){
                    MessageBox.alert(msgs.warn(), msgs.choose(), null);
                } else{
                    List<Content> contents = new ArrayList<Content>();
                    for(BeanModel model : smm.getSelectedItems()){
                        contents.add(model.<Content>getBean());
                    }
                    Dispatcher.forwardEvent(AppEvents.CONTENT_AUDITING_MANAGER_PASS, contents);
                }
            }
        });
        toolBars.add(pass);
        Button reject = new Button(msgs.content_auditing_reject());
        reject.setIconStyle("icon-delete");
        reject.addSelectionListener(new SelectionListener() {
            public void componentSelected(ComponentEvent ce) {
                if(smm.getSelectedItems().size() <= 0){
                    MessageBox.alert(msgs.warn(), msgs.choose(), null);
                } else{
                    List<Content> contents = new ArrayList<Content>();
                    for(BeanModel model : smm.getSelectedItems()){
                        contents.add(model.<Content>getBean());
                    }
                    Dispatcher.forwardEvent(AppEvents.CONTENT_AUDITING_MANAGER_REJECT, contents);
                }
            }
        });
        toolBars.add(reject);

        Menu contextMenu = new Menu();
        contextMenu.setWidth(130);
        MenuItem passMenu = new MenuItem();
        passMenu.setText(msgs.content_auditing_pass());
        passMenu.setIconStyle("icon-pass");
        passMenu.addSelectionListener(new SelectionListener() {
            public void componentSelected(ComponentEvent ce) {
                if (smm.getSelectedItems().size() <= 0) {
                    MessageBox.alert(msgs.warn(), msgs.choose(), null);
                } else {
                    List<Content> contents = new ArrayList<Content>();
                    for (BeanModel model : smm.getSelectedItems()) {
                        contents.add(model.<Content>getBean());
                    }
                    Dispatcher.forwardEvent(AppEvents.CONTENT_AUDITING_MANAGER_PASS, contents);
                }
            }
        });
        contextMenu.add(passMenu);
        MenuItem rejectMenu = new MenuItem();
        rejectMenu.setText(msgs.content_auditing_reject());
        rejectMenu.setIconStyle("icon-delete");
        rejectMenu.addSelectionListener(new SelectionListener() {
            public void componentSelected(ComponentEvent ce) {
                if (smm.getSelectedItems().size() <= 0) {
                    MessageBox.alert(msgs.warn(), msgs.choose(), null);
                } else {
                    List<Content> contents = new ArrayList<Content>();
                    for (BeanModel model : smm.getSelectedItems()) {
                        contents.add(model.<Content>getBean());
                    }
                    Dispatcher.forwardEvent(AppEvents.CONTENT_AUDITING_MANAGER_REJECT, contents);
                }
            }
        });
        contextMenu.add(rejectMenu);

        grid.setContextMenu(contextMenu);

        this.setHeaderVisible(false);
        this.setBodyBorder(false);
        this.setCollapsible(false);
        this.setAnimCollapse(false);
        this.setIconStyle("icon-table");
        this.setLayout(new FitLayout());
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);
        this.setSize("100%", "100%");
        this.add(grid);
        this.setTopComponent(toolBars);
        this.setBottomComponent(toolBar);
    }
}
