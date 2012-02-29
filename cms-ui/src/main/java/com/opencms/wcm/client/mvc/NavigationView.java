package com.opencms.wcm.client.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.DataList;
import com.extjs.gxt.ui.client.widget.DataListItem;
import com.extjs.gxt.ui.client.widget.DataListSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.WcmServiceAsync;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.model.WcmApp;
import com.opencms.wcm.client.model.WcmNodeModel;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 14:20:26
 * To change this template use File | Settings | File Templates.
 */
public class NavigationView extends View {

    private ContentPanel westPanel;

    private TreePanel<WcmNodeModel> tree;
    private final DataList otherManageDataList = new DataList();
    private final DataListSelectionModel smotherManageDLSM = otherManageDataList.getSelectionModel();


    final WcmServiceAsync service = (WcmServiceAsync) Registry.get(WcmService.SERVICE);// 定义所要引用的异步服务

    WcmMessages msgs = GWT.create(WcmMessages.class);
    
    public NavigationView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        westPanel = Registry.get(AppView.WEST);
        westPanel.setHeading(msgs.wcm_navigation());
        AccordionLayout layout = new AccordionLayout();
        westPanel.setLayout(layout);
        ContentPanel or = this.createOtherManagerPanel();
        westPanel.add(or);
        ContentPanel ar = this.createArticleManagerPanel();
        westPanel.add(ar);
        layout.setActiveItem(ar);
    }

    private ContentPanel createArticleManagerPanel() {
        ContentPanel topicTreePanel = new ContentPanel();
        topicTreePanel.setHeading(msgs.wcm_information_management());
        topicTreePanel.setScrollMode(Style.Scroll.AUTO);

        // data proxy
        RpcProxy<List<WcmNodeModel>> proxy = new RpcProxy<List<WcmNodeModel>>() {
            protected void load(Object loadConfig, AsyncCallback<List<WcmNodeModel>> callback) {
                service.getNodeChildren((WcmNodeModel)loadConfig, callback);
            }
        };
        // tree loader
        TreeLoader<WcmNodeModel> loader = new BaseTreeLoader<WcmNodeModel>(proxy) {
            @Override
            public boolean hasChildren(WcmNodeModel parent) {
                if(!(parent instanceof WcmNodeModel)){
                    return false;
                }
                return !"-1".equals(((WcmNodeModel)parent).getNodetype());
            }
        };
        // trees store
        TreeStore<WcmNodeModel> store = new TreeStore<WcmNodeModel>(loader);

        tree = new TreePanel<WcmNodeModel>(store);
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.setIconProvider(new ModelIconProvider<WcmNodeModel>() {
            //节点是根节点还是栏目节点
			@Override
			public AbstractImagePrototype getIcon(WcmNodeModel model) {
				if ((model instanceof WcmNodeModel)) {
                    String ext = model.getNodetype();
                    if ("0".equals(ext)) {
                        return IconHelper.createPath("images/icons/application_home.png");
                    }
                    if ("-1".equals(ext)) {
                        return IconHelper.createPath("images/icons/page_white.png");
                    }
                }
				return null;
			}
        });
        tree.setCaching(false);
        tree.setDisplayProperty("title");
        tree.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<WcmNodeModel>() {
            public void selectionChanged(SelectionChangedEvent<WcmNodeModel> se) {
                if (!se.getSelection().isEmpty()) {
                    WcmNodeModel node = (WcmNodeModel) se.getSelection().get(0);
                    Dispatcher.forwardEvent(AppEvents.ARTICLE_MANAGER_CHANGE_CATEGORY, node);
                }
            }
        });
        loader.load(null);
        topicTreePanel.add(tree);
        
        return topicTreePanel;
    }

    private ContentPanel createOtherManagerPanel() {
        ContentPanel otherManage = new ContentPanel();
        otherManage.setHeading(msgs.wcm_general_management());
        otherManage.setLayout(new FitLayout());

        otherManageDataList.setSelectionModel(smotherManageDLSM);
        otherManageDataList.setBorders(false);
        otherManageDataList.addListener(Events.SelectionChange, new Listener<ComponentEvent>(){
            public void handleEvent(ComponentEvent be) {
                DataList list = (DataList) be.getComponent();
                if (null == list.getSelectedItem()) return;
                Entry entry = list.getSelectedItem().getData("entry");
                Dispatcher.forwardEvent(entry.getEventType(), entry);
            }
        });
        juge();
        otherManage.add(otherManageDataList);
        return otherManage;
    }
    
    private void juge(){
        DataListItem dataListItem = new DataListItem();
        dataListItem.setText(msgs.site_manager_header());
        dataListItem.setId(AppEvents.SITE_MANAGER.getId());
        dataListItem.setIconStyle("icon-site");
        dataListItem.setData("entry", new Entry(AppEvents.SITE_MANAGER.getId(), msgs.site_manager_header(), AppState.OWNER_OTHER_MANAGER, null, true, true, AppEvents.SITE_MANAGER, false));
        otherManageDataList.add(dataListItem);
        service.getWcmApps(new AsyncCallback(){
            public void onFailure(Throwable throwable) {
            }
            public void onSuccess(Object o) {
                Map<Long, WcmApp> wcmApps = (Map<Long, WcmApp>)o;
                AppState.wcmApps = wcmApps;
                DataListItem dataListItem = null;
                for(WcmApp wcmApp : wcmApps.values()){
                    dataListItem = new DataListItem();
                    dataListItem.setText(wcmApp.getName());
                    dataListItem.setId(String.valueOf(wcmApp.getId()));
                    dataListItem.setIconStyle(wcmApp.getIcon());
                    dataListItem.setData("entry", new Entry(String.valueOf(wcmApp.getId()), wcmApp.getName(), AppState.OWNER_OTHER_MANAGER, null, true, true, AppEvents.OTHER_MANAGER_CHANGE_EVENT, true));
                    otherManageDataList.add(dataListItem);
                }
            }
        });
    }

    protected void handleEvent(AppEvent appEvent) {
        if(AppEvents.OTHER_MANAGER_ITEM_SELECTION == appEvent.getType()){
            Entry entry = (Entry)appEvent.getData();
            smotherManageDLSM.select(otherManageDataList.getItemByItemId(entry.getId()), true);
        }
        if(AppEvents.OTHER_MANAGER_ITEM_SELECTION_NONE == appEvent.getType()){
            smotherManageDLSM.deselectAll();
        }
        if(AppEvents.ARTICLE_MANAGER_ITEM_SELECTION_NONE == appEvent.getType()){
            tree.getSelectionModel().setSelection(new ArrayList());
        }
    }
}
