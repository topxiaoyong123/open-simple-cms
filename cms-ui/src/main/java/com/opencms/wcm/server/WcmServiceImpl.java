package com.opencms.wcm.server;

import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.model.WcmApp;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.Content;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 10:59:25
 * To change this template use File | Settings | File Templates.
 */
public class WcmServiceImpl implements WcmService {

    private static Logger logger = LoggerFactory.getLogger(WcmServiceImpl.class);

    private static Map<Long, WcmApp> wcmApps;

    public Map<Long, WcmApp> getWcmApps() {
        if(wcmApps == null){
            ApplicationContext context = new ClassPathXmlApplicationContext("wcmAppsContext.xml");
            wcmApps = (Map)context.getBean("wcmApps");
        }
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