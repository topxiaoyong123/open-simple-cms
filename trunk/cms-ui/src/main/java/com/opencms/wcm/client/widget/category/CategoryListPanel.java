package com.opencms.wcm.client.widget.category;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.data.*;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.WcmServiceAsync;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.category.Category;
import com.opencms.wcm.client.model.site.Site;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-10
 * Time: 11:15:30
 * To change this template use File | Settings | File Templates.
 */
public class CategoryListPanel extends ContentPanel {

    WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);

    private static Grid<BeanModel> grid = null;

    final WcmMessages msgs = GWT.create(WcmMessages.class);

    public void refresh() {
        grid.getStore().getLoader().load();
    }

    public CategoryListPanel(final WcmNodeModel parent) {
        if (service == null) {
            MessageBox box = new MessageBox();
            box.setButtons(MessageBox.OK);
            box.setIcon(MessageBox.INFO);
            box.setTitle("Information");
            box.setMessage("No service detected");
            box.show();
            return;
        }
        RpcProxy<Site> proxy = new RpcProxy<Site>() {
            @Override
            protected void load(Object loadConfig,
                                AsyncCallback<Site> callback) {
                service.getCategorysByParent(parent, callback);
            }
        };
        // loader
        BeanModelReader reader = new BeanModelReader();
        // loader and store
        ListLoader loader = new BaseListLoader(proxy, reader);
        ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
        loader.load();
        List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
        final CheckBoxSelectionModel<BeanModel> smm = new CheckBoxSelectionModel<BeanModel>();
        columns.add(smm.getColumn());
        columns.add(new ColumnConfig("name", msgs.category_name(), 150));
        columns.add(new ColumnConfig("title", msgs.category_title(), 100));
        columns.add(new ColumnConfig("keywords", msgs.category_keywords(), 100));
        ColumnConfig date = new ColumnConfig("creationDate", msgs.category_creationDate(), 100);
        date.setDateTimeFormat(DateTimeFormat.getFormat("yyyy-MM-dd"));
        columns.add(date);
        ColumnModel cm = new ColumnModel(columns);
        grid = new Grid<BeanModel>(store, cm);
        grid.setAutoExpandColumn("name");
        grid.setLoadMask(true);
        grid.setBorders(true);
        grid.setSelectionModel(smm);
        grid.addPlugin(smm);
        grid.getView().setForceFit(true);
        ToolBar toolBars = new ToolBar();
        Button add = new Button(msgs.add());
        add.setIconStyle("icon-add");
        add.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                AppEvent evt = new AppEvent(AppEvents.CATEGORY_MANAGER_ADD);
                Dispatcher.forwardEvent(evt);
            }
        });
        Button remove = new Button(msgs.delete());
        remove.setIconStyle("icon-delete");
        Button edit = new Button(msgs.edit());
        edit.setIconStyle("icon-plugin");
        edit.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                if(smm.getSelectedItems().size() != 1){
                    MessageBox.alert(msgs.warn(), msgs.choose_one(), null);
                } else{
                    String id = smm.getSelectedItem().get("id");
                    AppEvent evt = new AppEvent(AppEvents.CATEGORY_MANAGER_EDIT, id);
                    Dispatcher.forwardEvent(evt);
                }
            }
        });
        Button preview = new Button(msgs.preview());
        preview.setIconStyle("icon-text");

        Button publish = new Button(msgs.content_publishing_do());
        publish.setIconStyle("article-wenzhangfubu");
        publish.addSelectionListener(new SelectionListener() {
            public void componentSelected(ComponentEvent ce) {
                if(smm.getSelectedItems().size() <= 0){
                    MessageBox.alert(msgs.warn(), msgs.choose(), null);
                } else{
                    List<Category> categories = new ArrayList<Category>();
                    for(BeanModel model : smm.getSelectedItems()){
                        categories.add(model.<Category>getBean());
                    }
                    Dispatcher.forwardEvent(AppEvents.CATEGORY_MANAGER_PUBLISHING, categories);
                }
            }
        });

        toolBars.add(add);
        toolBars.add(new SeparatorToolItem());
        toolBars.add(remove);
        toolBars.add(new SeparatorToolItem());
        toolBars.add(edit);
        toolBars.add(new SeparatorToolItem());
        toolBars.add(preview);
        toolBars.add(new SeparatorToolItem());
        toolBars.add(publish);

        Menu contextMenu = new Menu();
        contextMenu.setWidth(130);
        MenuItem addCategory = new MenuItem();
        addCategory.setText(msgs.add());
        addCategory.setIconStyle("icon-add");
        addCategory.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                AppEvent evt = new AppEvent(AppEvents.CATEGORY_MANAGER_ADD);
                Dispatcher.forwardEvent(evt);
            }
        });
        contextMenu.add(addCategory);
        MenuItem editCategory = new MenuItem();
        editCategory.setText(msgs.edit());
        editCategory.setIconStyle("icon-plugin");
        editCategory.addListener(Events.Select, new Listener<ComponentEvent>() {
            public void handleEvent(ComponentEvent componentEvent) {
                if(smm.getSelectedItems().size() != 1){
                    MessageBox.alert(msgs.warn(), msgs.choose_one(), null);
                } else{
                    String id = smm.getSelectedItem().get("id");
                    AppEvent evt = new AppEvent(AppEvents.CATEGORY_MANAGER_EDIT, id);
                    Dispatcher.forwardEvent(evt);
                }
            }
        });
        contextMenu.add(editCategory);
        MenuItem deleteCategory = new MenuItem();
        deleteCategory.setText(msgs.delete());
        deleteCategory.setIconStyle("icon-delete");
        contextMenu.add(deleteCategory);
        MenuItem previewCategory = new MenuItem();
        previewCategory.setText(msgs.preview());
        previewCategory.setIconStyle("icon-text");
        contextMenu.add(previewCategory);
        MenuItem publishCategory = new MenuItem();
        publishCategory.setTitle(msgs.content_publishing_do());
        publishCategory.setIconStyle("article-wenzhangfubu");
        publishCategory.addSelectionListener(new SelectionListener() {
            public void componentSelected(ComponentEvent ce) {
                if(smm.getSelectedItems().size() <= 0){
                    MessageBox.alert(msgs.warn(), msgs.choose(), null);
                } else{
                    List<Category> categories = new ArrayList<Category>();
                    for(BeanModel model : smm.getSelectedItems()){
                        categories.add(model.<Category>getBean());
                    }
                    Dispatcher.forwardEvent(AppEvents.CATEGORY_MANAGER_PUBLISHING, categories);
                }
            }
        });
        contextMenu.add(publishCategory);
        grid.setContextMenu(contextMenu);
        this.setHeaderVisible(false);
        this.setFrame(false);
        this.setLayoutOnChange(true);
        this.setCollapsible(false);
        this.setAnimCollapse(false);
        this.setButtonAlign(Style.HorizontalAlignment.CENTER);
        this.setIconStyle("icon-table");
        this.setLayout(new FitLayout());
        this.setTopComponent(toolBars);
        this.setBodyBorder(false);
        grid.setBorders(false);
        this.add(grid);
    }
}
