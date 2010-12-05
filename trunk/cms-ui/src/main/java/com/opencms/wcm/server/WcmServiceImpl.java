package com.opencms.wcm.server;

import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.ApplicationException;
import com.opencms.wcm.client.model.WcmApp;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.Content;
import com.opencms.wcm.client.model.User;
import com.opencms.core.db.service.CmsManager;
import com.opencms.core.db.bean.UserBean;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpSession;

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
        if (wcmApps == null) {
            ApplicationContext context = new ClassPathXmlApplicationContext("wcmAppsContext.xml");
            wcmApps = (Map) context.getBean("wcmApps");
        }
        logger.debug("获取功能列表, [{}]", wcmApps);
        return wcmApps;
    }

    private HttpSession getSession() {
        return ContextThreadLocal.getRequest().getSession();
    }

    @Autowired
    private CmsManager cmsManager;

    public User login(User user) throws ApplicationException {
        if (!user.getCheckcode().equals(this.getSession().getAttribute("checkcode"))) {
            throw new ApplicationException("验证码错误");
        }
        UserBean userBean = cmsManager.getUserService().getUserByUsername(user.getUsername());
        if (userBean == null) {
            throw new ApplicationException("无效的用户名！");
        } else if (!user.getPassword().equals(userBean.getPassword())) {
            throw new ApplicationException("无效的密码！");
        }
        User u = new User();
        try {
            BeanUtils.copyProperties(user, userBean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            logger.error(e.toString());
            throw new ApplicationException(e.toString());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            logger.error(e.toString());
            throw new ApplicationException(e.toString());
        }
        logger.debug(user.getUsername() + "登录成功");
        this.getSession().setAttribute("username", user.getUsername());
        return user;
    }

    public String checkLogin() throws ApplicationException {
        String username = String.valueOf(this.getSession().getAttribute("username"));
        if (username == null || ("").equals(username) || ("null").equals(username)) {
            throw new ApplicationException("session超时，请重新登陆");
        } else {
            return username;
        }
    }

    public List<WcmNodeModel> getNodeChildren(WcmNodeModel node) throws ApplicationException {
        logger.debug("获取左侧树, [{}]", node);
        List<WcmNodeModel> list = new ArrayList<WcmNodeModel>();
        if (node == null) {
            WcmNodeModel subnode = new WcmNodeModel("1", "site1", "站点", "0");
            list.add(subnode);
        } else {
            WcmNodeModel subnode = new WcmNodeModel("2", "oo", "栏目", "-1");
            list.add(subnode);
        }
        return list;
    }

    public PagingLoadResult<Content> PagingLoadArticleList(PagingLoadConfig config, Content content) throws ApplicationException {
        return new BasePagingLoadResult<Content>(new ArrayList<Content>(), config.getOffset(), 0);
    }
}