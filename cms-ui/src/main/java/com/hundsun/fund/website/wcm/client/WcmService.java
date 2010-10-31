package com.hundsun.fund.website.wcm.client;

import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import com.hundsun.fund.website.wcm.client.model.WcmApp;
import com.hundsun.fund.website.wcm.client.model.WcmNodeModel;
import com.hundsun.fund.website.wcm.client.model.Content;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 10:59:25
 * To change this template use File | Settings | File Templates.
 */
@RemoteServiceRelativePath(WcmService.SERVICE_URI)
public interface WcmService extends RemoteService {

    public static final String SERVICE = "wcmService";

    public static final String SERVICE_URI = WcmService.SERVICE;

    public Map<Long, WcmApp> getWcmApps();

    public List<WcmNodeModel> getNodeChildren(WcmNodeModel node);

    public PagingLoadResult<Content> PagingLoadArticleList(PagingLoadConfig config, Content content);

    /**
     * Utility/Convenience class.
     * Use WcmService.App.getInstance() to access static instance of WcmServiceAsync
     */

}
