package com.hundsun.fund.website.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.*;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.hundsun.fund.website.wcm.client.widget.WelcomePanel;
import com.hundsun.fund.website.wcm.client.model.Entry;
import com.hundsun.fund.website.wcm.client.model.WcmNodeModel;
import com.hundsun.fund.website.wcm.client.model.Content;
import com.hundsun.fund.website.wcm.client.AppEvents;
import com.hundsun.fund.website.wcm.client.AppConstant;
import com.hundsun.fund.website.wcm.client.AppState;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 15:19:32
 * To change this template use File | Settings | File Templates.
 */
public class BodyView extends View {

    TabPanel tabPanel;
    private ContentPanel center = (ContentPanel) Registry.get(AppView.CENTER);

    public BodyView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        tabPanel = new TabPanel();
        tabPanel.setBorderStyle(false);
        tabPanel.setBodyBorder(false);
        tabPanel.setTabScroll(true);
        tabPanel.setAnimScroll(true);
        tabPanel.addListener(Events.Select, new Listener<TabPanelEvent>() {
            public void handleEvent(TabPanelEvent be) {
                Entry entry = (Entry) be.getItem().getData("body");
                if (entry.getId().equalsIgnoreCase(String.valueOf(AppConstant.WELCOME))){
                    AppState.centerEventType = AppEvents.WELCOME;
                    if(AppState.westBarId.equals(AppState.OWNER_OTHER_MANAGER)){
                        Dispatcher.forwardEvent(AppEvents.OTHER_MANAGER_ITEM_SELECTION_NONE);
                    }
                } else if(AppState.OWNER_OTHER_MANAGER_CALLBACK.equals(entry.getOwner())){
                    Dispatcher.forwardEvent(entry.getEventType(), entry);
                } else if(AppState.OWNER_ARTICLE_MANAGER.equals(entry.getOwner())){
                    Content content = new Content();
                    content.setCategoryId(AppState.westTreeItemId);
                    Dispatcher.forwardEvent(entry.getEventType(), content);
                }
            }
        });
        center.setBorders(false);
        center.add(tabPanel);
        this.initWelcomeTabPanel();
        this.initArticleManagerJudge();
    }

    public void onShowPage(Entry entry) {
        this.onShowPage(entry, false);
    }

    public void onShowPage(final Entry entry, boolean refresh) {
        TabItem item = tabPanel.findItem(entry.getId(), false);
        if (item == null) {
            item = new TabItem();
            item.setData("body", entry);
            item.setClosable(entry.isClosable());
            item.setId(entry.getId());
            item.setItemId(entry.getId());
            item.setText(entry.getName());
            item.setLayout(new FitLayout());
            if (null != entry.getBody()) {
                item.add(entry.getBody());
            }
            tabPanel.add(item);
        } else {
            if (refresh) {// refresh
//                item.removeAll();
                item.setLayout(new FitLayout());
                if (null != entry.getBody()) {
                    item.add(entry.getBody());
                }
                item.layout();
            }
        }
        tabPanel.setSelection(item);
    }

    public void addShowPage(Entry entry) {
        TabItem item = tabPanel.findItem(entry.getId(), false);
        if (item == null) {
            item = new TabItem();
            item.setData("body", entry);
            item.setClosable(entry.isClosable());
            item.setId(entry.getId());
            item.setItemId(entry.getId());
            item.setText(entry.getName());
            item.setLayout(new FitLayout());
            if (null != entry.getBody()) {
                item.add(entry.getBody());
            }
            tabPanel.add(item);
        }
    }

    private void removeBodyItems() {
        try {
            List<TabItem> list = tabPanel.getItems();
            TabItem welcome = tabPanel.getItemByItemId(String.valueOf(AppConstant.WELCOME));
            tabPanel.setSelection(welcome);
            if (list.size() > 1) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    TabItem item = list.get(i);
                    if (item.getId().equals(String.valueOf(AppConstant.WELCOME))) {
                        continue;
                    } else {
                        tabPanel.remove(item);
                    }
                }
            }
        } catch (Exception e) {
            tabPanel.removeAll();
        }
    }

    protected void handleEvent(AppEvent appEvent) {
        if(appEvent.getType() == AppEvents.WELCOME){
            this.initWelcomeTabPanel();
        }
        if(appEvent.getType() == AppEvents.ARTICLE_MANAGER_CHANGE_EVENT){
            doArticleManagerChangeEvent(appEvent);
        }
        if(appEvent.getType() == AppEvents.ARTICLE_MANAGER_CHANGE_CATEGORY){
            doArticleManagerChangeCategory(appEvent);
        }
        if(appEvent.getType() == AppEvents.OTHER_MANAGER_CHANGE_EVENT){
            doOtherManagerChangeEvent(appEvent);
        }
    }

    private void doArticleManagerChangeEvent(AppEvent appEvent){
        Entry entry = (Entry)appEvent.getData();
        AppState.centerEventType = entry.getEventType();
        if(AppState.OWNER_ARTICLE_MANAGER_CALLBACK.equals(entry.getOwner())){
            this.onShowPage(entry, true);
        } else{
                
        }
    }

    private void doArticleManagerChangeCategory(AppEvent appEvent){
        WcmNodeModel node = (WcmNodeModel)appEvent.getData();
        AppState.westTreeItemId = node.getUuid();
        if(AppState.isWestBarChanged(AppState.OWNER_ARTICLE_MANAGER)){
            AppState.westBarId = AppState.OWNER_ARTICLE_MANAGER;
            this.initArticleManageTabPanel();
            Dispatcher.forwardEvent(AppEvents.OTHER_MANAGER_ITEM_SELECTION_NONE);
        }
        Content content = new Content();
        content.setCategoryId(node.getUuid());
        Dispatcher.forwardEvent(AppState.centerEventType, content);
    }

    private void doOtherManagerChangeEvent(AppEvent appEvent){
        Entry entry = (Entry)appEvent.getData();
        AppState.centerEventType = entry.getEventType();
        if(AppState.isWestBarChanged(AppState.OWNER_OTHER_MANAGER)){
            AppState.westBarId = AppState.OWNER_OTHER_MANAGER;
            this.removeBodyItems();
            Dispatcher.forwardEvent(AppEvents.ARTICLE_MANAGER_ITEM_SELECTION_NONE);
        }
        if(AppState.OWNER_OTHER_MANAGER_CALLBACK.equals(entry.getOwner())){
            this.onShowPage(entry, true);
            if (AppState.westBarId.equals(AppState.OWNER_OTHER_MANAGER)) {
                Dispatcher.forwardEvent(AppEvents.OTHER_MANAGER_ITEM_SELECTION, entry);
            }
        } else if(entry.isOtherApp()){
            Dispatcher.forwardEvent(AppEvents.OTHER_APP, entry);    
        } else{
            //TODO
        }
    }

    boolean CONTENT_VIEWARTICLELIST_JUDGE = false;
    boolean CONTENT_AUDITINGLIST_JUDGE = false;
    boolean CONTENT_CONFIRMAUDITINGLIST_JUDGE = false;
    boolean CONTENT_PUBLISH_JUDGE = false;
    boolean CONTENT_MANAGERCONTENT_ALL_JUDGE = false;
    boolean CATEGORY_LIST_JUDGE = false;
    boolean OTHER_PUBLISH_JUDGE = false;

    private void initArticleManageTabPanel(){
        this.removeBodyItems();
        if (CONTENT_VIEWARTICLELIST_JUDGE) {
            this.addShowPage(new Entry(AppConstant.CONTENT_VIEWARTICLELIST + "", "文章采编", AppState.OWNER_ARTICLE_MANAGER,
                    null, true, false, AppEvents.CONTENT_VIEWARTICLELIST, false));
        }
        if (CONTENT_AUDITINGLIST_JUDGE) {
            this.addShowPage(new Entry(AppConstant.CONTENT_AUDITINGLIST + "", "文章审核", AppState.OWNER_ARTICLE_MANAGER,
                    null, true, false, AppEvents.CONTENT_AUDITINGLIST, false));
        }
        if (CONTENT_CONFIRMAUDITINGLIST_JUDGE) {
            this.addShowPage(new Entry(AppConstant.CONTENT_CONFIRMAUDITINGLIST + "", "文章签发", AppState.OWNER_ARTICLE_MANAGER,
                    null, true, false, AppEvents.CONTENT_CONFIRMAUDITINGLIST, false));
        }
        if (CONTENT_PUBLISH_JUDGE) {
            this.addShowPage(new Entry(AppConstant.CONTENT_PUBLISH + "", "文章发布", AppState.OWNER_ARTICLE_MANAGER,
                    null, true, false, AppEvents.CONTENT_PUBLISH, false));
        }
        if (CONTENT_MANAGERCONTENT_ALL_JUDGE) {
            this.addShowPage(new Entry(AppConstant.CONTENT_MANAGERCONTENT_ALL + "", "文章管理", AppState.OWNER_ARTICLE_MANAGER,
                    null, true, false, AppEvents.CONTENT_MANAGERCONTENT_ALL, false));
        }
        if (CATEGORY_LIST_JUDGE) {
            this.addShowPage(new Entry(AppConstant.CATEGORY_LIST + "", "栏目管理", AppState.OWNER_ARTICLE_MANAGER,
                    null, true, false, AppEvents.CATEGORY_LIST, false));
        }
        if (OTHER_PUBLISH_JUDGE) {
            this.addShowPage(new Entry(AppConstant.OTHER_PUBLISH + "", "栏目站点发布", AppState.OWNER_ARTICLE_MANAGER,
                    null, true, false, AppEvents.OTHER_PUBLISH, false));
        }
    }
    
    private void initWelcomeTabPanel() {
        this.onShowPage(new Entry(String.valueOf(AppConstant.WELCOME), "系统首页", AppState.OWNER_ARTICLE_MANAGER, new WelcomePanel(), true, false), true);
    }

    private void initArticleManagerJudge() {
        CONTENT_VIEWARTICLELIST_JUDGE = true;
        CONTENT_AUDITINGLIST_JUDGE = true;
        CONTENT_CONFIRMAUDITINGLIST_JUDGE = true;
        CONTENT_PUBLISH_JUDGE = true;
        CONTENT_MANAGERCONTENT_ALL_JUDGE = true;
        CATEGORY_LIST_JUDGE = true;
        OTHER_PUBLISH_JUDGE = true;
    }
}
