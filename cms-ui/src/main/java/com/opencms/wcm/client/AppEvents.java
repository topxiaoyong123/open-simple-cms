package com.opencms.wcm.client;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 11:14:10
 * To change this template use File | Settings | File Templates.
 */
public class AppEvents {

    public static final AppEventType INIT_UI = new AppEventType();

    public static final AppEventType LOGIN = new AppEventType();
    public static final AppEventType LOGIN_OK = new AppEventType();
    public static final AppEventType LOGIN_FAIL = new AppEventType();

    public static final AppEventType WELCOME = new AppEventType();

    public static final AppEventType OTHER_APP = new AppEventType();

    //基础布局
    public static final AppEventType OTHER_MANAGER_CHANGE_EVENT = new AppEventType();
    public static final AppEventType OTHER_MANAGER_ITEM_SELECTION = new AppEventType();
    public static final AppEventType OTHER_MANAGER_ITEM_SELECTION_NONE = new AppEventType();
    public static final AppEventType ARTICLE_MANAGER_CHANGE_EVENT = new AppEventType();
    public static final AppEventType ARTICLE_MANAGER_ITEM_SELECTION_NONE = new AppEventType();
    public static final AppEventType ARTICLE_MANAGER_CHANGE_CATEGORY = new AppEventType();

    //站点管理
    public final static AppEventType SITE_MANAGER = new AppEventType();
    public final static AppEventType SITE_MANAGER_ADD = new AppEventType();
    public final static AppEventType SITE_MANAGER_EDIT = new AppEventType();
    public final static AppEventType SITE_MANAGER_DELETE = new AppEventType();
    public final static AppEventType SITE_MANAGER_SAVE = new AppEventType();
    public final static AppEventType SITE_MANAGER_CANCEL = new AppEventType();
    public final static AppEventType SITE_MANAGER_SUCCESS = new AppEventType();

    //栏目管理
    public final static AppEventType CATEGORY_MANAGER = new AppEventType();
    public final static AppEventType CATEGORY_MANAGER_ADD = new AppEventType();
    public final static AppEventType CATEGORY_MANAGER_EDIT = new AppEventType();
    public final static AppEventType CATEGORY_MANAGER_DELETE = new AppEventType();
    public final static AppEventType CATEGORY_MANAGER_SAVE = new AppEventType();
    public final static AppEventType CATEGORY_MANAGER_CANCEL = new AppEventType();
    public final static AppEventType CATEGORY_MANAGER_SUCCESS = new AppEventType();
    public final static AppEventType CATEGORY_MANAGER_PUBLISHING = new AppEventType();
    public final static AppEventType CATEGORY_MANAGER_PUBLISHING_SUCCESS = new AppEventType();

    //文章管理
    public final static AppEventType CONTENT_MANAGER = new AppEventType();
    public final static AppEventType CONTENT_MANAGER_ALL = new AppEventType();
    public final static AppEventType CONTENT_MANAGER_ADD = new AppEventType();
    public final static AppEventType CONTENT_MANAGER_EDIT = new AppEventType();
    public final static AppEventType CONTENT_MANAGER_DELETE = new AppEventType();
    public final static AppEventType CONTENT_MANAGER_SAVE = new AppEventType();
    public final static AppEventType CONTENT_MANAGER_CANCEL = new AppEventType();
    public final static AppEventType CONTENT_MANAGER_SUCCESS = new AppEventType();

    //文章审核
    public final static AppEventType CONTENT_AUDITING_MANAGER = new AppEventType();
    public final static AppEventType CONTENT_AUDITING_MANAGER_PASS = new AppEventType();
    public final static AppEventType CONTENT_AUDITING_MANAGER_REJECT = new AppEventType();
    public final static AppEventType CONTENT_AUDITING_MANAGER_SUCCESS = new AppEventType();
    
    //发布生成
    public final static AppEventType PUBLISHING_MANAGER = new AppEventType();
    public final static AppEventType PUBLISHING_MANAGER_SITE = new AppEventType();
    public final static AppEventType PUBLISHING_MANAGER_CATEGORY = new AppEventType();
    public final static AppEventType PUBLISHING_MANAGER_CONTENT = new AppEventType();
    
    //文件管理
    public final static AppEventType FILE_MANAGER = new AppEventType();
    public final static AppEventType FILE_MANAGER_EDIT = new AppEventType();
    public final static AppEventType FILE_MANAGER_SAVE = new AppEventType();
    public final static AppEventType FILE_MANAGER_CANCEL = new AppEventType();
    public final static AppEventType FILE_MANAGER_SUCCESS = new AppEventType();
}
