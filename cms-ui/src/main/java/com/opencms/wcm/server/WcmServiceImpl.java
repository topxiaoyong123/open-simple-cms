package com.opencms.wcm.server;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.ContentDetailBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.bean.UserBean;
import com.opencms.core.db.bean.field.ContentField;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opencms.engine.PublishStatus;
import com.opencms.engine.util.PathUtils;
import com.opencms.template.TemplateHelper;
import com.opencms.template.bean.CmsTemplateBean;
import com.opencms.util.CmsUtils;
import com.opencms.util.ContextThreadLocal;
import com.opencms.util.common.Constants;
import com.opencms.util.common.FileFilterImpl;
import com.opencms.wcm.client.ApplicationException;
import com.opencms.wcm.client.WcmService;
import com.opencms.wcm.client.model.User;
import com.opencms.wcm.client.model.WcmApp;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.category.Category;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.model.file.WcmFile;
import com.opencms.wcm.client.model.site.Site;
import com.opencms.wcm.client.model.template.CmsTemplate;
import com.opencms.wcm.server.message.MessageSourceHelper;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 10:59:25
 * To change this template use File | Settings | File Templates.
 */
@Component("wcmService")
@Scope("session")
@Transactional(readOnly = true)
public class WcmServiceImpl implements WcmService {

    private static Logger logger = LoggerFactory.getLogger(WcmServiceImpl.class);

    private static Map<Long, WcmApp> wcmApps;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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
    @Resource
    private CmsManager cmsManager;

    //spring国际化处理
    @Resource
    private MessageSourceHelper messageSourceHelper;

    @Resource
    private CmsUtils cmsUtils;

    @Resource
    private TemplateHelper templateHelper;

    @Resource
    private Engine engine;

    @Transactional(readOnly = true)
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
        cmsUtils.getBeanMapperHelper().simpleMap(userBean, user);
        logger.debug(user.getUsername() + "登录成功");
        this.getSession().setAttribute("username", user.getUsername());
        return user;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public boolean logout() throws ApplicationException {
        String username = String.valueOf(this.getSession().getAttribute("username"));
        logger.debug(username + "退出登录");
        this.getSession().removeAttribute("username");
        return true;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String checkLogin() throws ApplicationException {
        String username = String.valueOf(this.getSession().getAttribute("username"));
        if (username == null || ("").equals(username) || ("null").equals(username)) {
            throw new ApplicationException("session超时，请重新登陆");
        } else {
            return username;
        }
    }

    @Transactional(readOnly = true)
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
                    categorys = cmsManager.getCategoryService().getCategorysBySiteId(siteBean.getId(), false);
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

    @Transactional(readOnly = true)
    public List<Site> getAllSites() throws ApplicationException {
        try {
            List<Site> list = new ArrayList<Site>();
            List<SiteBean> sites = cmsManager.getSiteService().getAllSites();
            for(SiteBean siteBean : sites){
                Site site = new Site();
                cmsUtils.getBeanMapperHelper().simpleMap(siteBean, site);
                list.add(site);
            }
            return list;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
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
            cmsUtils.getBeanMapperHelper().simpleMap(site, siteBean);
            return cmsManager.getSiteService().addOrUpdateSite(siteBean);
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Site getSiteById(String id) throws ApplicationException {
        try {
            if(id == null){
                logger.debug("id为空");
                return new Site();
            }
            SiteBean siteBean = cmsManager.getSiteService().getSiteById(id);
            Site site = new Site();
            cmsUtils.getBeanMapperHelper().simpleMap(siteBean, site);
            return site;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());    
        }
    }

    @Transactional(readOnly = true)
    public List<Category> getCategorysByParent(WcmNodeModel parent) throws ApplicationException {
        List<Category> list = new ArrayList<Category>();
        if(parent != null){
            logger.debug("parent:{}", parent);
            if("0".equals(parent.getNodetype())){
                logger.debug("取一级栏目，通过所属站点取");
                List<CategoryBean> categorys = cmsManager.getCategoryService().getCategorysBySiteId(parent.getId(), false);
                if(categorys != null){
                    for(CategoryBean categoryBean : categorys){
                        Category category = new Category();
                        cmsUtils.getBeanMapperHelper().simpleMap(categoryBean, category);
                        category.setSiteId(parent.getId());
                        list.add(category);
                    }
                }
            } else{
                logger.debug("取子级栏目，通过所属栏目取");
                CategoryBean pcategory = cmsManager.getCategoryService().getCategoryById(parent.getId());
                if(pcategory != null){
                    for(CategoryBean categoryBean : pcategory.getChildren()){
                        Category category = new Category();
                        cmsUtils.getBeanMapperHelper().simpleMap(categoryBean, category);
                        category.setParentId(pcategory.getId());
                        category.setSiteId(pcategory.getSite().getId());
                        list.add(category);
                    }
                }
            }
        }
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
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
            cmsUtils.getBeanMapperHelper().simpleMap(category, categoryBean);
            return cmsManager.getCategoryService().addOrUpdateCategory(categoryBean);
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Category getCategoryById(String id, WcmNodeModel parent) throws ApplicationException {
        try {
            if(id == null){
                logger.debug("id为空");
                return new Category();
            }
            CategoryBean categoryBean = cmsManager.getCategoryService().getCategoryById(id);
            Category category = new Category();
            cmsUtils.getBeanMapperHelper().simpleMap(categoryBean, category);
            category.setSiteId(categoryBean.getSite().getId());
            if(categoryBean.getParent() != null){
                category.setParentId(categoryBean.getParent().getId());
            }
            return category;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean publishingCategoryss(List<Category> categories, boolean createHtml) throws ApplicationException {
        try {
            status.clean();
            status.setInitial(true);
            status.setTotal(categories.size());
            for(Category category : categories){
                CategoryBean categoryBean = cmsManager.getCategoryService().getCategoryById(category.getId());
                engine.engineCategory(categoryBean, createHtml);
//                contentBean.setState(ContentField._STATE_PUBLISHED);
//                contentBean.setCreateHtml(createHtml);
                cmsManager.getCategoryService().updateCategory(categoryBean);
                status.addFinished(1);
            }
            status.setEnd(true);
            return true;
        } catch(Exception e){
            status.setException(true);
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addOrUpdateContent(Content content) throws ApplicationException {
        try {
            ContentBean contentBean;
            if(content.getId() != null){
                logger.debug("更新文章[{}]", content.getTitle());
                contentBean = cmsManager.getContentService().getContentById(content.getId());
                contentBean.getContentDetail().setContent(content.getContent());
            } else{
                logger.debug("新建文章[{}]",  content.getTitle());
                contentBean = new ContentBean();
                contentBean.setCategory(cmsManager.getCategoryService().getCategoryById(content.getCategoryId()));
                ContentDetailBean contentDetailBean = new ContentDetailBean();
                contentDetailBean.setContent(content.getContent());
                contentBean.setContentDetail(contentDetailBean);
            }
            cmsUtils.getBeanMapperHelper().simpleMap(content, contentBean);
            contentBean.setState(ContentField._STATE_AUDITING_WAITING);
            return cmsManager.getContentService().addOrUpdateContent(contentBean);
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
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
            cmsUtils.getBeanMapperHelper().simpleMap(contentBean, content);
            content.setContent(contentBean.getContentDetail().getContent());
            content.setCategoryId(contentBean.getCategory().getId());
            return content;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public PagingLoadResult<Content> PagingLoadContentList(PagingLoadConfig config, Content content) throws ApplicationException {
        try {
            List<ContentBean> list;
            long count;
            if(content.getState() != null){
                list = cmsManager.getContentService().getContentsByCategoryIdAndPage(content.getCategoryId(), content.getState(), config.getOffset(), config.getLimit());
                count = cmsManager.getContentService().getCountByCategoryId(content.getCategoryId(), content.getState());
            } else if(content.getStates() != null){
                list = cmsManager.getContentService().getContentsByCategoryIdAndPage(content.getCategoryId(), content.getStates(), config.getOffset(), config.getLimit());
                count = cmsManager.getContentService().getCountByCategoryId(content.getCategoryId(), content.getStates());
            } else{
                list = cmsManager.getContentService().getContentsByCategoryIdAndPage(content.getCategoryId(), config.getOffset(), config.getLimit());
                count = cmsManager.getContentService().getCountByCategoryId(content.getCategoryId());
            }

            List<Content> contents = new ArrayList<Content>();
            for(ContentBean contentBean : list){
                Content c = new Content();
                cmsUtils.getBeanMapperHelper().simpleMap(contentBean, c);
                c.setCategoryId(contentBean.getCategory().getId());
                contents.add(c);
            }
            return new BasePagingLoadResult<Content>(contents, config.getOffset(), (int)count);      
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());    
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean auditingContents(List<Content> contents, boolean pass) throws ApplicationException {
        try {
            for(Content content : contents){
                ContentBean contentBean = cmsManager.getContentService().getContentById(content.getId());
                if(pass){
                    contentBean.setState(ContentField._STATE_PUBLISH_WAITING);
                } else {
                    contentBean.setState(ContentField._STATE_AUDITING_REJECT);
                }
                cmsManager.getContentService().updateContent(contentBean);
            }
            return true;
        } catch(Exception e){
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Resource
    private PublishStatus status;

    @Transactional(rollbackFor = Exception.class)
    public boolean publishingContents(List<Content> contents, boolean createHtml) throws ApplicationException {
        try {
            status.clean();
            status.setInitial(true);
            status.setTotal(contents.size());
            for(Content content : contents){
                ContentBean contentBean = cmsManager.getContentService().getContentById(content.getId());
                engine.engineContent(contentBean, createHtml);
                contentBean.setState(ContentField._STATE_PUBLISHED);
                contentBean.setCreateHtml(createHtml);
                cmsManager.getContentService().updateContent(contentBean);
                status.addFinished(1);
            }
            status.setEnd(true);
            return true;
        } catch(Exception e){
            status.setException(true);
            e.printStackTrace();
            logger.error("error:", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public int[] getPublishingProcess() throws ApplicationException {
        try{
            if(!status.isInitial()){
                return null;
            }
            if(status.isException()){
                return new int[]{-1, 0};
            }
            return new int[]{status.getFinished(), status.getTotal()};
        } finally {
            if(status.isEnd() || status.isException())
                status.clean();
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<CmsTemplate> getCmsTemplates(String baseTemplate, String type) {
        List<CmsTemplate> list = new ArrayList<CmsTemplate>();
        List<CmsTemplateBean> cmsTemplateBeans = templateHelper.getTemplateBeans(baseTemplate, type);
        for(CmsTemplateBean cmsTemplateBean : cmsTemplateBeans){
            CmsTemplate cmsTemplate = (CmsTemplate)cmsUtils.getBeanMapperHelper().simpleMap(cmsTemplateBean, CmsTemplate.class);
            list.add(cmsTemplate);
        }
        return list;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<WcmFile> getFileForders(WcmFile f) throws ApplicationException {
        String basePath = PathUtils.getBasePath();
        List<WcmFile> list = new ArrayList<WcmFile>();
        FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				if(file.isFile() || "WEB-INF".equalsIgnoreCase(file.getName())) {
					return false;
				}
				return true;
			}
        };
        if (f != null) {
            if ("0".equals(f.getType())) {
                File parent = new File(f.getPath());
                File[] fs = parent.listFiles(filter);
                for (File file : fs) {
                    if (file.isDirectory() && !file.isHidden()) {
                        WcmFile wf = new WcmFile(file.getName(), file.getAbsolutePath(), "", "0", "", f.getTemplate());
                        File[] ifs = file.listFiles(filter);
                        wf.setHasChild((ifs == null || ifs.length == 0) ? "0" : "1");
                        list.add(wf);
                    }
                }
            }
        } else {
            File parent = new File(basePath);
            File[] fs = parent.listFiles(filter);
            for (File file : fs) {
                if (file.isDirectory() && !file.isHidden()) {
                    WcmFile wf = new WcmFile(file.getName(), file.getAbsolutePath(), "", "0", "", file.getName());
                    File[] ifs = file.listFiles(filter);
                    wf.setHasChild((ifs == null || ifs.length == 0) ? "0" : "1");
                    list.add(wf);
                }
            }
        }
        return list;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<WcmFile> getFiles(WcmFile f) throws ApplicationException {
        String hosturl = cmsUtils.getResourceHelper().getCmsResource().getOutputUrl();
        List<WcmFile> list = new ArrayList<WcmFile>();
        logger.debug("getFiles, parent:{}", f);
        File parent;
        if (f != null && "0".equals(f.getType()) && f.getPath() != null) {
            parent = new File(f.getPath());
        } else {
        	parent = new File(PathUtils.getBasePath());
        }
        if(parent != null) {
        	FileFilter filter = new FileFilter() {
    			@Override
    			public boolean accept(File file) {
    				if(file.isFile() && !file.isHidden()) {
    					return true;
    				}
    				return false;
    			}
            };
        	File[] fs = parent.listFiles(filter);
            for (File file : fs) {
                String filename = file.getName();
                String filetype = filename.substring(filename.lastIndexOf(".") + 1);
                String shortcut = null;
                String url = hosturl + file.getAbsolutePath().substring(new File(PathUtils.getBasePath()).getAbsolutePath().length()).replace("\\", "/");
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
        logger.debug("getFiles, filelist:{}", list);
        return list;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public WcmFile createForder(WcmFile f, String name) throws ApplicationException {
        if (f != null) {
            File parent = new File(f.getPath());
            File tar = new File(parent, name);
            if (tar.mkdirs()) {
                return new WcmFile(tar.getName(), tar.getAbsolutePath(), "", "0", "", tar.getName());
            }
        } else {
            String templatePath = cmsUtils.getResourceHelper().getCmsResource().getTemplatePath();
            File parent = new File(templatePath);
            File tar = new File(parent, name);
            if (tar.mkdirs()) {
                return new WcmFile(tar.getName(), tar.getAbsolutePath(), "", "0", "", tar.getName());
            }
        }
        return null;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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