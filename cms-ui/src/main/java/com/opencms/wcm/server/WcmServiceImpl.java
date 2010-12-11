package com.opencms.wcm.server;

import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.ApplicationException;
import com.opencms.wcm.client.model.*;
import com.opencms.wcm.client.model.site.Site;
import com.opencms.wcm.client.model.category.Category;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.server.message.MessageSourceHelper;
import com.opencms.core.db.service.CmsManager;
import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.bean.CategoryBean;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;

import java.util.*;
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

    public boolean setLocale(String locale) {
        getSession().setMaxInactiveInterval(-1);
        Locale l = new Locale("");
        if(locale != null){
            String[] a = locale.split("_");
            if(a.length == 3){
                l = new Locale(a[0], a[1], a[2]);
            } else if(a.length == 2){
                l = new Locale(a[0], a[1]);
            } else if(a.length == 1){
                l = new Locale(a[0]);
            }
        }
        getSession().setAttribute("locale", l);
        return true;
    }

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

    //cmsManager
    @Autowired
    private CmsManager cmsManager;

    //spring国际化处理
    @Autowired
    private MessageSourceHelper messageSourceHelper;

    public User login(User user) throws ApplicationException {
        if (!user.getCheckcode().equals(this.getSession().getAttribute("checkcode"))) {
            throw new ApplicationException(messageSourceHelper.getMessage("login.error", new String[]{messageSourceHelper.getMessage("login.validcode")}));
        }
        UserBean userBean = cmsManager.getUserService().getUserByUsername(user.getUsername());
        if (userBean == null) {
            throw new ApplicationException(messageSourceHelper.getMessage("login.notexist", new String[]{messageSourceHelper.getMessage("login.username")}));
        } else if (!user.getPassword().equals(userBean.getPassword())) {
            throw new ApplicationException(messageSourceHelper.getMessage("login.error", new String[]{messageSourceHelper.getMessage("login.password")}));
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
            Set<CategoryBean> categorys = null;
            if("0".equals(node.getNodetype())){
                SiteBean siteBean = cmsManager.getSiteService().getSiteById(node.getId());
                if(siteBean != null){
                    categorys = siteBean.getCategorys();    
                }
            } else{
                CategoryBean categoryBean = cmsManager.getCategoryService().getCategoryById(node.getId());
                if(categoryBean != null){
                    categorys = categoryBean.getChildren();        
                }
            }
            if(categorys != null){
                for(CategoryBean category : categorys){
                    if(category.getChildren().size() > 0){
                        WcmNodeModel subnode = new WcmNodeModel(category.getId(), category.getName(), category.getTitle(), "1");
                        list.add(subnode);
                    } else{
                        WcmNodeModel subnode = new WcmNodeModel(category.getId(), category.getName(), category.getTitle(), "-1");
                        list.add(subnode);
                    }
                }
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
            SiteBean siteBean;
            if(site.getId() != null){
                logger.debug("更新站点[{}]", site.getTitle());
                siteBean = cmsManager.getSiteService().getSiteById(site.getId());
            } else{
                logger.debug("新建站点[{}]",  site.getTitle());
                siteBean = new SiteBean();
            }
            try {
                BeanUtils.copyProperties(siteBean, site);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return cmsManager.getSiteService().addOrUpdateSite(siteBean);
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

    public List<Category> getCategorysByParent(WcmNodeModel parent) throws ApplicationException {
        List<Category> list = new ArrayList<Category>();
        if(parent != null){
            logger.debug("parent:{}", parent);
            if("0".equals(parent.getNodetype())){
                logger.debug("取一级栏目，通过所属站点取");
                SiteBean site = cmsManager.getSiteService().getSiteById(parent.getId());
                if(site != null){
                    for(CategoryBean categoryBean : site.getCategorys()){
                        Category category = new Category();
                        try {
                            BeanUtils.copyProperties(category, categoryBean);
                            category.setClientCreationDate(categoryBean.getCreationDate());
                            category.setSiteId(site.getId());
                            list.add(category);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else{
                logger.debug("取子级栏目，通过所属栏目取");
                CategoryBean pcategory = cmsManager.getCategoryService().getCategoryById(parent.getId());
                if(pcategory != null){
                    for(CategoryBean categoryBean : pcategory.getChildren()){
                        Category category = new Category();
                        try {
                            BeanUtils.copyProperties(category, categoryBean);
                            category.setClientCreationDate(categoryBean.getCreationDate());
                            category.setParentId(pcategory.getId());
                            list.add(category);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return list;
    }

    public boolean addOrUpdateCategory(Category category) throws ApplicationException {
        try {
            CategoryBean categoryBean;
            if(category.getId() != null){
                logger.debug("更新栏目[{}]", category.getTitle());
                categoryBean = cmsManager.getCategoryService().getCategoryById(category.getId());
            } else{
                logger.debug("新建栏目[{}]",  category.getTitle());
                categoryBean = new CategoryBean();
                if(category.getSiteId() != null){
                    categoryBean.setSite(cmsManager.getSiteService().getSiteById(category.getSiteId()));
                } else if(category.getParentId() != null){
                    categoryBean.setParent(cmsManager.getCategoryService().getCategoryById(category.getParentId()));
                }
            }
            try {
                BeanUtils.copyProperties(categoryBean, category);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return cmsManager.getCategoryService().addOrUpdateCategory(categoryBean);
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    public Category getCategoryById(String id) throws ApplicationException {
        try {
            if(id == null){
                logger.debug("id为空");
                return new Category();
            }
            CategoryBean categoryBean = cmsManager.getCategoryService().getCategoryById(id);
            Category category = new Category();
            try {
                BeanUtils.copyProperties(category, categoryBean);
                category.setClientCreationDate(categoryBean.getCreationDate());
                if(categoryBean.getSite() != null){
                    category.setSiteId(categoryBean.getSite().getId());
                } else if(categoryBean.getParent() != null){
                    category.setParentId(categoryBean.getParent().getId());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return category;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }
}