package com.hundsun.fund.website.wcm.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hundsun.fund.website.wcm.client.WcmService;
import com.hundsun.fund.website.wcm.client.model.WcmApp;
import com.hundsun.fund.website.wcm.client.model.WcmNodeModel;
import com.hundsun.fund.website.wcm.client.model.Content;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 10:59:25
 * To change this template use File | Settings | File Templates.
 */
public class WcmServiceImpl extends RemoteServiceServlet implements WcmService {

    private static Logger logger = LoggerFactory.getLogger(WcmServiceImpl.class);

    private static Map wcmApps;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        ApplicationContext applicationContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletConfig.getServletContext());
        if(wcmApps == null){
            wcmApps = (Map)applicationContext.getBean("wcmApps");
        }
    }

    public Map<Long, WcmApp> getWcmApps() {
        logger.debug("获取功能列表, [{}]", wcmApps);
        return wcmApps;
    }

    public List<WcmNodeModel> getNodeChildren(WcmNodeModel node) {
        logger.debug("获取左侧树, [{}]", node);
        List<WcmNodeModel> list = new ArrayList<WcmNodeModel>();
        if (node == null){
            WcmNodeModel subnode = new WcmNodeModel("1", "site1", "站点", "0");
            list.add(subnode);
        } else{
            WcmNodeModel subnode = new WcmNodeModel("2", "oo", "栏目", "-1");
            list.add(subnode);
        }
        return list;
    }

    public PagingLoadResult<Content> PagingLoadArticleList(PagingLoadConfig config, Content content) {
        return new BasePagingLoadResult<Content>(new ArrayList<Content>(), config.getOffset(), 0);
    }
}