package com.opencms.wcm.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.Content;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 10:59:25
 * To change this template use File | Settings | File Templates.
 */
public interface WcmServiceAsync {
    void getWcmApps(AsyncCallback async);

    void getNodeChildren(WcmNodeModel node, AsyncCallback async);

    void PagingLoadArticleList(PagingLoadConfig config, Content content, AsyncCallback async);

    public static class Util {
        private static WcmServiceAsync instance;

        public static WcmServiceAsync getInstance() {
            if (instance == null) {
                instance = (WcmServiceAsync) GWT.create(WcmService.class);
            }
            return instance;
        }
    }
}
