package com.opencms.wcm.client;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.opencms.wcm.client.model.User;
import com.opencms.wcm.client.model.WcmNodeModel;
import com.opencms.wcm.client.model.category.Category;
import com.opencms.wcm.client.model.content.Content;
import com.opencms.wcm.client.model.file.WcmFile;
import com.opencms.wcm.client.model.site.Site;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 10:59:25
 * To change this template use File | Settings | File Templates.
 */
public interface WcmServiceAsync {

    void setLocale(String locale, AsyncCallback async);

    void getWcmApps(AsyncCallback async);

    void login(User user, AsyncCallback async);
    void logout(AsyncCallback async);

    void checkLogin(AsyncCallback async);

    void getNodeChildren(WcmNodeModel node, AsyncCallback async);

    void getAllSites(AsyncCallback async);

    void addOrUpdateSite(Site site, AsyncCallback async);

    void getSiteById(String id, AsyncCallback async);

    void getCategorysByParent(WcmNodeModel parent, AsyncCallback async);

    void addOrUpdateCategory(Category category, AsyncCallback async);

    void getCategoryById(String id, WcmNodeModel parent, AsyncCallback async);

    void publishingCategoryss(List<Category> categories, boolean createHtml, AsyncCallback async);

    void addOrUpdateContent(Content category, AsyncCallback async);

    void getContentById(String id, WcmNodeModel parent, AsyncCallback async);

    void PagingLoadContentList(PagingLoadConfig config, Content content, AsyncCallback async);

    void auditingContents(List<Content> contents, boolean pass, AsyncCallback async);

    void publishingContents(List<Content> contents, boolean createHtml, AsyncCallback async);

    void getPublishingProcess(AsyncCallback async);

    void getCmsTemplates(String baseTemplate, String type, AsyncCallback async);

    void getFileForders(WcmFile f, AsyncCallback async);

    void getFiles(WcmFile f, AsyncCallback async);

    void createForder(WcmFile f, String name, AsyncCallback async);

    void deleteFiles(List<WcmFile> files, AsyncCallback async);
    
    void editFile(WcmFile file, AsyncCallback async);
    
    void saveFile(WcmFile file, AsyncCallback async);

    public static class Util {
        private static WcmServiceAsync instance;

        public static WcmServiceAsync getInstance() {
            if (instance == null) {
                instance = (WcmServiceAsync) GWT.create(WcmService.class);
            }
            return instance;
        }
    }
}
