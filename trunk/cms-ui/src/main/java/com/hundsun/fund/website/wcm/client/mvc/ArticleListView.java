package com.hundsun.fund.website.wcm.client.mvc;

import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.hundsun.fund.website.wcm.client.AppEvents;
import com.hundsun.fund.website.wcm.client.AppState;
import com.hundsun.fund.website.wcm.client.AppConstant;
import com.hundsun.fund.website.wcm.client.model.Content;
import com.hundsun.fund.website.wcm.client.model.Entry;
import com.hundsun.fund.website.wcm.client.widget.ArticleListPanel;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-8
 * Time: 8:55:38
 * To change this template use File | Settings | File Templates.
 */
public class ArticleListView extends View {

    private ArticleListPanel artcilelisttpanel;

    public ArticleListView(Controller controller) {
        super(controller);
    }

    protected void handleEvent(AppEvent appEvent) {
        if(appEvent.getType() == AppEvents.CONTENT_VIEWARTICLELIST){
            Content content = (Content) appEvent.getData();
            artcilelisttpanel = new ArticleListPanel(content, true);
            Dispatcher.forwardEvent(
                    AppEvents.ARTICLE_MANAGER_CHANGE_EVENT,
                    new Entry(
                            String.valueOf(AppConstant.CONTENT_VIEWARTICLELIST),
                            "文章采编",
                            AppState.OWNER_ARTICLE_MANAGER_CALLBACK,
                            artcilelisttpanel,
                            true,
                            false,
                            AppEvents.CONTENT_VIEWARTICLELIST,
                            false));
        }
    }
}
