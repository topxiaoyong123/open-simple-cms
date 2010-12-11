package com.opencms.wcm.client.widget.content;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.extjs.gxt.ui.client.widget.grid.*;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.data.*;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.WcmServiceAsync;
import com.opencms.wcm.client.WcmService;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-8
 * Time: 9:00:45
 * To change this template use File | Settings | File Templates.
 */
public class ContentListPanel extends ContentPanel {

    final WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);// 定义所要引用的异步服务

    private RpcProxy<PagingLoadResult<Content>> proxy;

    private static Grid<BeanModel> grid = null;

    WcmMessages msgs = GWT.create(WcmMessages.class);

    public void refresh() {
        grid.getStore().getLoader().load();
    }

    public ContentListPanel(final Content content) {
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
                service.PagingLoadArticleList((PagingLoadConfig) loadConfig, content, callback);
            }
        };
        final BasePagingLoader<PagingLoadResult<Content>> loader = new BasePagingLoader<PagingLoadResult<Content>>(proxy, new BeanModelReader());
        final ListStore<BeanModel> store = new ListStore<BeanModel>(loader);

        final PagingToolBar toolBar = new PagingToolBar(20);
        toolBar.bind(loader);
        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

        RowNumberer rnum = new RowNumberer();
        final CheckBoxSelectionModel smm = new CheckBoxSelectionModel();
        columns.add(smm.getColumn());
        columns.add(rnum);
        columns.add(new ColumnConfig("title", msgs.content_title(), 80));
        columns.add(new ColumnConfig("author", msgs.content_author(), 80));
        columns.add(new ColumnConfig("source", msgs.content_source(), 80));
        columns.add(new ColumnConfig("state", msgs.content_state(), 80));
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
        Button add = new Button(msgs.add());
        add.setIconStyle("article-zengjia");
        Menu addmenu = new Menu();
        MenuItem normal = new MenuItem(msgs.content_type_normal());
        normal.addListener(Events.Select, new Listener<ComponentEvent>() {
            @Override
            public void handleEvent(ComponentEvent componentEvent) {
                AppEvent evt = new AppEvent(AppEvents.CONTENT_MANAGER_ADD, "0");
                Dispatcher.forwardEvent(evt);
            }
        });
        addmenu.add(normal);
        MenuItem outlink = new MenuItem(msgs.content_type_outlink());
        outlink.addListener(Events.Select, new Listener<ComponentEvent>() {
            @Override
            public void handleEvent(ComponentEvent componentEvent) {
                AppEvent evt = new AppEvent(AppEvents.CONTENT_MANAGER_ADD, "1");
                Dispatcher.forwardEvent(evt);
            }
        });
        addmenu.add(outlink);
        MenuItem download = new MenuItem(msgs.content_type_download());
        download.addListener(Events.Select, new Listener<ComponentEvent>() {
            @Override
            public void handleEvent(ComponentEvent componentEvent) {
                AppEvent evt = new AppEvent(AppEvents.CONTENT_MANAGER_ADD, "2");
                Dispatcher.forwardEvent(evt);
            }
        });
        addmenu.add(download);

        add.setMenu(addmenu);
        toolBars.add(add);
        Button edit = new Button(msgs.edit());
        edit.setIconStyle("article-zengjia");
        edit.addListener(Events.Select, new Listener<ComponentEvent>() {
            @Override
            public void handleEvent(ComponentEvent componentEvent) {
                if(smm.getSelectedItems().size() != 1){
                    MessageBox.alert(msgs.warn(), msgs.choose_one(), null);
                } else{
                    String id = smm.getSelectedItem().get("id");
                    AppEvent evt = new AppEvent(AppEvents.CONTENT_MANAGER_EDIT, id);
                    Dispatcher.forwardEvent(evt);
                }
            }
        });
        toolBars.add(edit);

        this.setHeaderVisible(false);
        this.setBodyBorder(false);
        this.setCollapsible(false);
        this.setAnimCollapse(false);
        this.setIconStyle("icon-table");
        this.setLayout(new RowLayout(Style.Orientation.VERTICAL));
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);
        this.setSize("100%", "100%");
        this.add(grid);
        this.setTopComponent(toolBars);
        this.setBottomComponent(toolBar);
    }
}
