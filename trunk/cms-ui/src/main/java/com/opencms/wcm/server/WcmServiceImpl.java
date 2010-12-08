package com.opencms.wcm.server;

import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.ApplicationException;
import com.opencms.wcm.client.model.*;
import com.opencms.core.db.service.CmsManager;
import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.bean.CategoryBean;
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
import org.springframework.stereotype.Component;
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
@Component("wcmService")
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
            List<SiteBean> sites = cmsManager.getSiteService().getAllSites();
            for(SiteBean siteBean : sites){
                WcmNodeModel subnode = new WcmNodeModel(siteBean.getId(), siteBean.getName(), siteBean.getTitle(), "0");
                list.add(subnode);
            }
        } else {
            if("0".equals(node.getNodetype())){
                SiteBean siteBean = cmsManager.getSiteService().getSiteById(node.getId());
                if(siteBean != null){
                    for(CategoryBean category : siteBean.getCategorys()){
                        if(category.getChildren().size() > 0){
                            WcmNodeModel subnode = new WcmNodeModel(category.getId(), category.getName(), category.getTitle(), "1");
                            list.add(subnode);
                        } else{
                            WcmNodeModel subnode = new WcmNodeModel(category.getId(), category.getName(), category.getTitle(), "-1");
                            list.add(subnode);
                        }
                    }
                }
            } else{
                
            }
        }
        return list;
    }

    public PagingLoadResult<Content> PagingLoadArticleList(PagingLoadConfig config, Content content) throws ApplicationException {
        return new BasePagingLoadResult<Content>(new ArrayList<Content>(), config.getOffset(), 0);
    }

    public List<Site> getAllSites() throws ApplicationException {
        try {
            List<Site> list = new ArrayList<Site>();
            List<SiteBean> sites = cmsManager.getSiteService().getAllSites();
            for(SiteBean siteBean : sites){
                Site site = new Site();
                try {
                    BeanUtils.copyProperties(site, siteBean);
                    site.setClientCreationDate(siteBean.getCreationDate());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                list.add(site);
            }
            return list;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    public boolean addOrUpdateSite(Site site) throws ApplicationException {
        try {
            SiteBean siteBean = new SiteBean();
            try {
                BeanUtils.copyProperties(siteBean, site);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if(siteBean.getId() == null){
                logger.debug("新建站点[{}]", siteBean.getTitle());
                cmsManager.getSiteService().addSite(siteBean);
            } else{
                logger.debug("更新站点[{}]",  siteBean.getTitle());
                cmsManager.getSiteService().updateSite(siteBean);    
            }
            return true;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    public Site getSiteById(String id) throws ApplicationException {
        try {
            if(id == null){
                logger.debug("id为空");
                return new Site();
            }
            SiteBean siteBean = cmsManager.getSiteService().getSiteById(id);
            Site site = new Site();
            try {
                BeanUtils.copyProperties(site, siteBean);
                site.setClientCreationDate(siteBean.getCreationDate());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return site;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());    
        }
    }
}