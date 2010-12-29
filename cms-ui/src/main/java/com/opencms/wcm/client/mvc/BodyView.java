package com.opencms.wcm.client.mvc;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TabPanelEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.widget.WelcomePanel;

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

    WcmMessages msgs = GWT.create(WcmMessages.class);

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
                if (entry.getId().equalsIgnoreCase(AppEvents.WELCOME.getId())) {
                    AppState.centerEventType = AppEvents.WELCOME;
                    initWelcomeTabPanel();
                    if (AppState.westBarId.equals(AppState.OWNER_OTHER_MANAGER)) {
                        Dispatcher.forwardEvent(AppEvents.OTHER_MANAGER_ITEM_SELECTION_NONE);
                    }
                } else if (AppState.OWNER_OTHER_MANAGER_CALLBACK.equals(entry.getOwner())) {
                    Dispatcher.forwardEvent(AppEvents.OTHER_MANAGER_ITEM_SELECTION, entry);
                } else if (AppState.OWNER_ARTICLE_MANAGER.equals(entry.getOwner())) {
                    Dispatcher.forwardEvent(AppEvents.ARTICLE_MANAGER_CHANGE_EVENT, entry);
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
                item.removeAll();
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
            TabItem welcome = tabPanel.getItemByItemId(AppEvents.WELCOME.getId());
            tabPanel.setSelection(welcome);
            if (list.size() > 1) {
                for (int i = list.size() - 1; i >= 0; i--) {
                    TabItem item = list.get(i);
                    if (item.getId().equals(AppEvents.WELCOME.getId())) {
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
        if (appEvent.getType() == AppEvents.WELCOME) {
            this.initWelcomeTabPanel();
        }
        if (appEvent.getType() == AppEvents.ARTICLE_MANAGER_CHANGE_EVENT) {
            doArticleManagerChangeEvent(appEvent);
        }
        if (appEvent.getType() == AppEvents.ARTICLE_MANAGER_CHANGE_CATEGORY) {
            doArticleManagerChangeCategory(appEvent);
        }
        if (appEvent.getType() == AppEvents.OTHER_MANAGER_CHANGE_EVENT) {
            doOtherManagerChangeEvent(appEvent);
        }
    }

    private void doArticleManagerChangeEvent(AppEvent appEvent) {
        Entry entry = (Entry) appEvent.getData();
        AppState.centerEventType = entry.getEventType();
        if (AppState.OWNER_ARTICLE_MANAGER_CALLBACK.equals(entry.getOwner())) {
            this.onShowPage(entry, true);
        } else {
            Content content = new Content();
            content.setCategoryId(AppState.westTreeItemId);
            Dispatcher.forwardEvent(entry.getEventType(), content);
        }
    }

    private void doArticleManagerChangeCategory(AppEvent appEvent) {
        WcmNodeModel node = (WcmNodeModel) appEvent.getData();
        AppState.westTreeItemId = node.getId();
        AppState.westTreeItemObj = node;
        if (AppState.isWestBarChanged(AppState.OWNER_ARTICLE_MANAGER)) {
            AppState.westBarId = AppState.OWNER_ARTICLE_MANAGER;
            this.initArticleManageTabPanel();
            Dispatcher.forwardEvent(AppEvents.OTHER_MANAGER_ITEM_SELECTION_NONE);
        }
        Content content = new Content();
        content.setCategoryId(node.getId());
        Dispatcher.forwardEvent(AppState.centerEventType, content);
    }

    private void doOtherManagerChangeEvent(AppEvent appEvent) {
        Entry entry = (Entry) appEvent.getData();
        AppState.centerEventType = entry.getEventType();
        if (AppState.isWestBarChanged(AppState.OWNER_OTHER_MANAGER)) {
            AppState.westBarId = AppState.OWNER_OTHER_MANAGER;
            this.removeBodyItems();
            Dispatcher.forwardEvent(AppEvents.ARTICLE_MANAGER_ITEM_SELECTION_NONE);
        }
        if (AppState.OWNER_OTHER_MANAGER_CALLBACK.equals(entry.getOwner())) {
            this.onShowPage(entry, true);
            if (AppState.westBarId.equals(AppState.OWNER_OTHER_MANAGER)) {
                Dispatcher.forwardEvent(AppEvents.OTHER_MANAGER_ITEM_SELECTION, entry);
            }
        } else if (entry.isOtherApp()) {
            Dispatcher.forwardEvent(AppEvents.OTHER_APP, entry);
        } else {
            Dispatcher.forwardEvent(entry.getEventType(), entry);
        }
    }

    private void initArticleManageTabPanel() {
        this.removeBodyItems();
        this.addShowPage(new Entry(AppEvents.CONTENT_MANAGER.getId(), msgs.content_editing_header(), AppState.OWNER_ARTICLE_MANAGER,
                null, true, false, AppEvents.CONTENT_MANAGER, false));
        this.addShowPage(new Entry(AppEvents.CONTENT_AUDITING_MANAGER.getId(), msgs.content_auditing_header(), AppState.OWNER_ARTICLE_MANAGER,
                null, true, false, AppEvents.CONTENT_AUDITING_MANAGER, false));
        this.addShowPage(new Entry(AppEvents.CONTENT_PUBLISHING_MANAGER.getId(), msgs.content_publishing_header(), AppState.OWNER_ARTICLE_MANAGER,
                null, true, false, AppEvents.CONTENT_PUBLISHING_MANAGER, false));
        this.addShowPage(new Entry(AppEvents.CONTENT_MANAGER_ALL.getId(), msgs.content_manager_header(), AppState.OWNER_ARTICLE_MANAGER,
                null, true, false, AppEvents.CONTENT_MANAGER_ALL, false));
        this.addShowPage(new Entry(AppEvents.CATEGORY_MANAGER.getId(), msgs.category_manager_header(), AppState.OWNER_ARTICLE_MANAGER,
                null, true, false, AppEvents.CATEGORY_MANAGER, false));
        this.addShowPage(new Entry(AppEvents.PUBLISHING_MANAGER.getId(), msgs.category_site_publishing_header(), AppState.OWNER_ARTICLE_MANAGER,
                null, true, false, AppEvents.PUBLISHING_MANAGER, false));
    }

    private void initWelcomeTabPanel() {
        this.onShowPage(new Entry(AppEvents.WELCOME.getId(), msgs.wcm_welcome(), AppState.OWNER_ARTICLE_MANAGER, new WelcomePanel(), true, false), true);
    }

    private void initArticleManagerJudge() {
    }
}
