package com.opencms.wcm.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.opencms.wcm.client.model.*;
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
//@SuppressWarnings({"ALL"})
@RemoteServiceRelativePath(WcmService.SERVICE_URI)
public interface WcmService extends RemoteService {

    public static final String SERVICE = "wcmService";

    public static final String SERVICE_URI = WcmService.SERVICE;

    public Map<Long, WcmApp> getWcmApps() throws ApplicationException;

    public User login(User user) throws ApplicationException;

    public String checkLogin() throws ApplicationException;

    public List<WcmNodeModel> getNodeChildren(WcmNodeModel node) throws ApplicationException;

    public PagingLoadResult<Content> PagingLoadArticleList(PagingLoadConfig config, Content content) throws ApplicationException;

    public List<Site> getAllSites() throws ApplicationException;

    public boolean addOrUpdateSite(Site site) throws ApplicationException;

    public Site getSiteById(String id) throws ApplicationException;
}
