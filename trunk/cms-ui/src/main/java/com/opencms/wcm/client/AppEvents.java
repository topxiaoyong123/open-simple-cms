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

    public static final EventType INIT_UI = new EventType();

    public static final EventType WELCOME = new EventType();

    public static final EventType OTHER_APP = new EventType();

    public static final EventType OTHER_MANAGER_CHANGE_EVENT = new EventType();

    public static final EventType OTHER_MANAGER_ITEM_SELECTION = new EventType();

    public static final EventType OTHER_MANAGER_ITEM_SELECTION_NONE = new EventType();

    public static final EventType ARTICLE_MANAGER_CHANGE_EVENT = new EventType();

    public static final EventType ARTICLE_MANAGER_ITEM_SELECTION_NONE = new EventType();

    public static final EventType ARTICLE_MANAGER_CHANGE_CATEGORY = new EventType();


    public final static EventType CONTENT_VIEWARTICLELIST = new EventType();
    public final static EventType CONTENT_AUDITINGLIST = new EventType();
    public final static EventType CONTENT_CONFIRMAUDITINGLIST = new EventType();
    public final static EventType CONTENT_PUBLISH = new EventType();
    public final static EventType CONTENT_MANAGERCONTENT_ALL = new EventType();
    public final static EventType CATEGORY_LIST = new EventType();
    public final static EventType OTHER_PUBLISH = new EventType();
}
