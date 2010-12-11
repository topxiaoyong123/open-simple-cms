package com.opencms.wcm.client.mvc.category;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.Window;
import com.opencms.wcm.client.widget.category.CategoryListPanel;
import com.opencms.wcm.client.widget.category.CategoryPanel;
import com.opencms.wcm.client.WcmMessages;
import com.opencms.wcm.client.AppEventType;
import com.opencms.wcm.client.AppEvents;
import com.opencms.wcm.client.AppState;
import com.opencms.wcm.client.model.Entry;
import com.opencms.wcm.client.model.category.Category;
import com.google.gwt.core.client.GWT;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-10
 * Time: 14:02:39
 * To change this template use File | Settings | File Templates.
 */
public class CategoryView extends View {

    private Window window = new Window();

    private CategoryListPanel categoryListPanel ;

    WcmMessages msgs = GWT.create(WcmMessages.class);
    
    public CategoryView(Controller controller) {
        super(controller);
    }

    @Override
    protected void handleEvent(AppEvent appEvent) {
        AppEventType etype = (AppEventType)appEvent.getType();
        if(AppEvents.CATEGORY_MANAGER == appEvent.getType()){
            categoryListPanel = new CategoryListPanel(AppState.westTreeItemObj);
            Dispatcher.forwardEvent(
                    AppEvents.ARTICLE_MANAGER_CHANGE_EVENT,
                    new Entry(
                            etype.getId(),
                            msgs.category_manager_header(),
                            AppState.OWNER_ARTICLE_MANAGER_CALLBACK,
                            categoryListPanel,
                            true,
                            false,
                            etype,
                            false));
        } else if(AppEvents.CATEGORY_MANAGER_CANCEL == appEvent.getType() || AppEvents.CATEGORY_MANAGER_SUCCESS == appEvent.getType()){
            window.hide();
            categoryListPanel.refresh();
        } else if(AppEvents.CATEGORY_MANAGER_ADD == appEvent.getType()){
            Category c = appEvent.getData();
            CategoryPanel cp = new CategoryPanel(c, AppState.westTreeItemObj);
            window.removeAll();
            window.add(cp);
            window.setHeading(msgs.category_add_header());
            window.setWidth("500px");
            window.show();
        } else if(AppEvents.CATEGORY_MANAGER_EDIT == appEvent.getType()){
            Category c = appEvent.getData();
            CategoryPanel cp = new CategoryPanel(c, AppState.westTreeItemObj);
            window.removeAll();
            window.add(cp);
            window.setHeading(msgs.category_edit_header());
            window.setWidth("500px");
            window.show();
        } else if(AppEvents.CATEGORY_MANAGER_DELETE == appEvent.getType()){

        }
    }
}
