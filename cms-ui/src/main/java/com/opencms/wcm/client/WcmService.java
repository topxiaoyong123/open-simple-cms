package com.opencms.wcm.client;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.opencms.wcm.client.model.User;
import com.opencms.wcm.client.model.WcmApp;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.category.Category;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.model.file.WcmFile;
import com.opencms.wcm.client.model.site.Site;
import com.opencms.wcm.client.model.template.CmsTemplate;

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
@SuppressWarnings({"ALL"})
@RemoteServiceRelativePath(WcmService.SERVICE_URI)
public interface WcmService extends RemoteService {

    public static final String SERVICE = "wcmService";

    public static final String SERVICE_URI = WcmService.SERVICE;

    /**
     * 设置从客户端传过来的Locale
     * @param locale
     * @return
     */
    public boolean setLocale(String locale);

    /**
     * 取得综合管理中，外部应用程序
     * @return
     * @throws ApplicationException
     */
    public Map<Long, WcmApp> getWcmApps() throws ApplicationException;

    /**
     * 管理员登陆
     * @param user
     * @return
     * @throws ApplicationException
     */
    public User login(User user) throws ApplicationException;

    public boolean logout() throws ApplicationException;

    /**
     * 检查是否已经登录
     * @return
     * @throws ApplicationException
     */
    public String checkLogin() throws ApplicationException;

    /**
     * 树结构查询根据节点查询子节点
     * @param node
     * @return
     * @throws ApplicationException
     */
    public List<WcmNodeModel> getNodeChildren(WcmNodeModel node) throws ApplicationException;

    /**
     * 取得所有站点列表
     * @return
     * @throws ApplicationException
     */
    public List<Site> getAllSites() throws ApplicationException;

    /**
     * 新增或更新站点，判断id是否为空
     * @param site
     * @return
     * @throws ApplicationException
     */
    public boolean addOrUpdateSite(Site site) throws ApplicationException;

    /**
     * 通过id取得站点信息
     * @param id
     * @return
     * @throws ApplicationException
     */
    public Site getSiteById(Long id) throws ApplicationException;

    /**
     * 通过父节点，查询栏目列表
     * @param parent
     * @return
     * @throws ApplicationException
     */
    public List<Category> getCategorysByParent(WcmNodeModel parent) throws ApplicationException;

    /**
     * 新增或更新栏目信息
     * @param category
     * @return
     * @throws ApplicationException
     */
    public boolean addOrUpdateCategory(Category category) throws ApplicationException;

    /**
     * 通过id取得栏目信息
     * @param id
     * @return
     * @throws ApplicationException
     */
    public Category getCategoryById(Long id, WcmNodeModel parent) throws ApplicationException;

    public boolean publishingCategoryss(List<Category> categories, boolean createHtml) throws ApplicationException;

    /**
     * 新增或更新文章
     * @param content
     * @return
     * @throws ApplicationException
     */
    public boolean addOrUpdateContent(Content content) throws ApplicationException;

    /**
     * 根据ID取得文章信息
     * @param id
     * @param parent
     * @return
     * @throws ApplicationException
     */
    public Content getContentById(Long id, WcmNodeModel parent) throws ApplicationException;

    /**
     * 分页取得文章列表
     * @param config
     * @param content
     * @return
     * @throws ApplicationException
     */
    public PagingLoadResult<Content> PagingLoadContentList(PagingLoadConfig config, Content content) throws ApplicationException;

    /**
     * 文章审核
     * @param contents
     * @param pass    是否审核通过
     * @return
     * @throws ApplicationException
     */
    public boolean auditingContents(List<Content> contents, boolean pass) throws ApplicationException;

    public boolean publishingContents(List<Content> contents, boolean createHtml) throws ApplicationException;

    public int[] getPublishingProcess() throws ApplicationException;

    /**
     * 取得模板列表
     * @param baseTemplate
     * @param name
     * @param type
     * @return
     */
    public List<CmsTemplate> getCmsTemplates(String baseTemplate, String type);

    public List<WcmFile> getFileForders(WcmFile f) throws ApplicationException;

    public List<WcmFile> getFiles(WcmFile f) throws ApplicationException;

    public WcmFile createForder(WcmFile f, String name) throws ApplicationException;

    public boolean deleteFiles(List<WcmFile> files) throws ApplicationException;
    
    public WcmFile editFile(WcmFile file) throws ApplicationException;
    
    public boolean saveFile(WcmFile file) throws ApplicationException;
}
