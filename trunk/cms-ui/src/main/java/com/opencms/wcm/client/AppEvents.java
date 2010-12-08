package com.opencms.wcm.client;

import com.extjs.gxt.ui.client.event.EventType;

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

    //
    public final static AppEventType CONTENT_VIEWARTICLELIST = new AppEventType();
    public final static AppEventType CONTENT_AUDITINGLIST = new AppEventType();
    public final static AppEventType CONTENT_CONFIRMAUDITINGLIST = new AppEventType();
    public final static AppEventType CONTENT_PUBLISH = new AppEventType();
    public final static AppEventType CONTENT_MANAGERCONTENT_ALL = new AppEventType();
    public final static AppEventType CATEGORY_LIST = new AppEventType();
    public final static AppEventType OTHER_PUBLISH = new AppEventType();

    //站点管理
    public final static AppEventType SITE_MANAGER = new AppEventType();
    public final static AppEventType SITE_MANAGER_ADD = new AppEventType();
    public final static AppEventType SITE_MANAGER_EDIT = new AppEventType();
    public final static AppEventType SITE_MANAGER_DELETE = new AppEventType();
    public final static AppEventType SITE_MANAGER_SAVE = new AppEventType();
    public final static AppEventType SITE_MANAGER_CANCEL = new AppEventType();
    public final static AppEventType SITE_MANAGER_SUCCESS = new AppEventType();
}
