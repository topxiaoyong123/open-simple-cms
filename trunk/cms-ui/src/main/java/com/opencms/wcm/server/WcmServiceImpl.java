package com.opencms.wcm.server;

import com.opencms.core.db.bean.ContentBean;
import com.opencms.util.resources.ResourceHelper;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.ApplicationException;
import com.opencms.wcm.client.model.*;
import com.opencms.wcm.client.model.file.WcmFile;
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

import java.io.File;
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

    @Autowired
    private ResourceHelper resourceHelper;

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
            Collection<CategoryBean> categorys = null;
            if("0".equals(node.getNodetype())){
                SiteBean siteBean = cmsManager.getSiteService().getSiteById(node.getId());
                if(siteBean != null){
                    categorys = cmsManager.getCategoryService().getTopCategorysBySiteId(siteBean.getId());
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
                List<CategoryBean> categorys = cmsManager.getCategoryService().getTopCategorysBySiteId(parent.getId());
                if(categorys != null){
                    for(CategoryBean categoryBean : categorys){
                        Category category = new Category();
                        try {
                            BeanUtils.copyProperties(category, categoryBean);
                            category.setClientCreationDate(categoryBean.getCreationDate());
                            category.setSiteId(parent.getId());
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
                            category.setSiteId(pcategory.getSite().getId());
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
                    CategoryBean parent = cmsManager.getCategoryService().getCategoryById(category.getParentId());
                    categoryBean.setParent(parent);
                    categoryBean.setSite(cmsManager.getSiteService().getSiteById(parent.getSite().getId()));
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

    public Category getCategoryById(String id, WcmNodeModel parent) throws ApplicationException {
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
                category.setSiteId(categoryBean.getSite().getId());
                if(categoryBean.getParent() != null){
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

    public boolean addOrUpdateContent(Content content) throws ApplicationException {
        try {
            ContentBean contentBean;
            if(content.getId() != null){
                logger.debug("更新文章[{}]", content.getTitle());
                contentBean = cmsManager.getContentService().getContentById(content.getId());
            } else{
                logger.debug("新建文章[{}]",  content.getTitle());
                contentBean = new ContentBean();
                contentBean.setCategory(cmsManager.getCategoryService().getCategoryById(content.getCategoryId()));
            }
            try {
                BeanUtils.copyProperties(contentBean, content);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            contentBean.setState("0");
            return cmsManager.getContentService().addOrUpdateContent(contentBean);
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    public Content getContentById(String id, WcmNodeModel parent) throws ApplicationException {
        try {
            if(id == null){
                logger.debug("id为空");
                if("0".equals(parent.getNodetype())){
                    logger.warn("站点不能添加文章");
                    throw new ApplicationException(messageSourceHelper.getMessage("content.add.sitenotallow"));
                }
                return new Content();
            }
            ContentBean contentBean = cmsManager.getContentService().getContentById(id);
            Content content = new Content();
            try {
                BeanUtils.copyProperties(content, contentBean);
                content.setClientCreationDate(contentBean.getCreationDate());
                content.setClientModificationDate(contentBean.getModificationDate());
                content.setCategoryId(contentBean.getCategory().getId());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return content;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    public PagingLoadResult<Content> PagingLoadArticleList(PagingLoadConfig config, Content content) throws ApplicationException {
        try {
            List<ContentBean> list = cmsManager.getContentService().getContentsByCategoryIdAndPage(content.getCategoryId(), config.getOffset(), config.getLimit());
            long count = cmsManager.getContentService().getCountByCategoryId(content.getCategoryId());
            List<Content> contents = new ArrayList<Content>();
            for(ContentBean contentBean : list){
                Content c = new Content();
                try {
                    BeanUtils.copyProperties(c, contentBean);
                    c.setClientCreationDate(contentBean.getCreationDate());
                    c.setCategoryId(contentBean.getCategory().getId());
                    contents.add(c);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return new BasePagingLoadResult<Content>(contents, config.getOffset(), (int)count);      
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());    
        }
    }

    public List<WcmFile> getFileForders(WcmFile f) throws ApplicationException {
        String templatePath = resourceHelper.getTemplateResource().getTemplatePath();
        List<WcmFile> list = new ArrayList<WcmFile>();
        if (f != null) {
            if ("0".equals(f.getType())) {
                File parent = new File(f.getPath());
                File[] fs = parent.listFiles();
                for (File file : fs) {
                    if (file.isDirectory() && !file.isHidden()) {
                        WcmFile wf = new WcmFile(file.getName(), file.getAbsolutePath(), "", "0", "", f.getTemplate());
                        list.add(wf);
                    }
                }
            }
        } else {
            File parent = new File(templatePath);
            File[] fs = parent.listFiles();
            for (File file : fs) {
                if (file.isDirectory() && !file.isHidden()) {
                    WcmFile wf = new WcmFile(file.getName(), file.getAbsolutePath(), "", "0", "", file.getName());
                    list.add(wf);
                }
            }
        }
        return list;
    }

    public List<WcmFile> getFiles(WcmFile f) throws ApplicationException {
        String hosturl = resourceHelper.getWcmResource().getOutputUrl();
        List<WcmFile> list = new ArrayList<WcmFile>();
        logger.debug("getFiles, parent:{}", f);
        if (f != null && !"0".equals(f.getPath())) {
            File parent = new File(f.getPath());
            File[] fs = parent.listFiles();
            for (File file : fs) {
                if (file.isFile() && !file.isHidden()) {
                    String filename = file.getName();
                    String filetype = filename.substring(filename.lastIndexOf(".") + 1);
                    String shortcut = null;
                    String url = hosturl + "templates/" + file.getAbsolutePath().substring(file.getAbsolutePath().indexOf(f.getTemplate())).replace("\\", "/");
                    if (("jpg").equalsIgnoreCase(filetype) || ("bmp").equalsIgnoreCase(filetype) || ("gif").equalsIgnoreCase(filetype) || ("png").equalsIgnoreCase(filetype)) {
                        shortcut = url;
                    } else if ("doc".equalsIgnoreCase(filetype) || "docx".equalsIgnoreCase(filetype)) {
                        shortcut = "images/shortcut/word.jpg";
                    } else if ("xls".equalsIgnoreCase(filetype) || "xlsx".equalsIgnoreCase(filetype) || "csv".equalsIgnoreCase(filetype)) {
                        shortcut = "images/shortcut/excel.jpg";
                    } else if ("ppt".equalsIgnoreCase(filetype) || "pptx".equalsIgnoreCase(filetype)) {
                        shortcut = "images/shortcut/ppt.jpg";
                    } else if ("pdf".equalsIgnoreCase(filetype)) {
                        shortcut = "images/shortcut/pdf.jpg";
                    } else if ("html".equalsIgnoreCase(filetype) || "htm".equalsIgnoreCase(filetype) || "shtml".equalsIgnoreCase(filetype)) {
                        shortcut = "images/shortcut/html.jpg";
                    } else if ("css".equalsIgnoreCase(filetype) || "js".equalsIgnoreCase(filetype) || "txt".equalsIgnoreCase(filetype) || "text".equalsIgnoreCase(filetype)) {
                        shortcut = "images/shortcut/txt.jpg";
                    } else if ("rar".equalsIgnoreCase(filetype) || "zip".equalsIgnoreCase(filetype) || "war".equalsIgnoreCase(filetype) || "jar".equalsIgnoreCase(filetype)) {
                        shortcut = "images/shortcut/rar.jpg";
                    } else if ("exe".equalsIgnoreCase(filetype)) {
                        shortcut = "images/shortcut/exe.jpg";
                    } else {
                        shortcut = "images/shortcut/other.jpg";
                    }
                    WcmFile wf = new WcmFile(file.getName(), file.getAbsolutePath(), url, "1", shortcut, f.getTemplate(), file.length(), new Date(file.lastModified()));
                    list.add(wf);
                }
            }
        }
        logger.debug("getFiles, filelist:{}", list);
        return list;
    }

    public WcmFile createForder(WcmFile f, String name) throws ApplicationException {
        if (f != null) {
            File parent = new File(f.getPath());
            File tar = new File(parent, name);
            if (tar.mkdirs()) {
                return new WcmFile(tar.getName(), tar.getAbsolutePath(), "", "0", "", tar.getName());
            }
        } else {
            String templatePath = resourceHelper.getTemplateResource().getTemplatePath();
            File parent = new File(templatePath);
            File tar = new File(parent, name);
            if (tar.mkdirs()) {
                return new WcmFile(tar.getName(), tar.getAbsolutePath(), "", "0", "", tar.getName());
            }
        }
        return null;
    }

    public boolean deleteFiles(List<WcmFile> files) throws ApplicationException {
        int i = 0;
        logger.debug("删除文件：{}", files.toString());
        try {
            for (WcmFile file : files) {
                File f = new File(file.getPath());
                if (f.exists() && f.isDirectory() && f.listFiles().length > 0) {
                    return false;
                }
                f.delete();
                i++;
            }
        } catch (Exception e) {
        }
        return true;
    }
}