package com.opencms.wcm.client.widget.content;

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
        GridCellRenderer<BeanModel> titleRender = new GridCellRenderer<BeanModel>() {
            public Object render(BeanModel model, String property, ColumnData config, int rowIndex, int colIndex, ListStore<BeanModel> beanModelListStore, Grid<BeanModel> beanModelGrid) {
                 String id = model.get("contentId");
                String title = model.get("title");
                return "<a onclick='return false' href='" + "?id=" + id
                        + "'>" + title + "</a>";
            }
        };
        ColumnConfig column = new ColumnConfig();
        column.setId("title");
        column.setHeader("主题");
        column.setWidth(260);
        column.setRenderer(titleRender);
        columns.add(column);
        columns.add(new ColumnConfig("author", "作者", 80));
        columns.add(new ColumnConfig("source", "来源", 80));
        columns.add(new ColumnConfig("state", "状态", 80));
        columns.add(new ColumnConfig("type", "展现方式", 80));
        columns.add(new ColumnConfig("no", "排序", 40));

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
        grid.getView().setEmptyText("<div class=\"cms_prompt\">此栏目没有文章，请添加!</div>");
        grid.setSelectionModel(smm);
        grid.addPlugin(smm);
        grid.setWidth("100%");
        grid.setHeight("100%");
        grid.setAutoWidth(true);

        ToolBar toolBars = new ToolBar();
        Button contentadd = new Button("增加");
        contentadd.setIconStyle("article-zengjia");
        Menu contentaddmenu = new Menu();
        MenuItem contenttype = new MenuItem("文章");
        contentaddmenu.add(contenttype);
        MenuItem linktype = new MenuItem("链接");
        contentaddmenu.add(linktype);
        MenuItem wenjiantype = new MenuItem("文件");
        contentaddmenu.add(wenjiantype);
        MenuItem multiupload = new MenuItem("批量添加文件");

        contentaddmenu.add(multiupload);
        contentadd.setMenu(contentaddmenu);
        toolBars.add(contentadd);

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
