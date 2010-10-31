package com.hundsun.fund.website.wcm.client;

import com.extjs.gxt.ui.client.event.EventType;
import com.hundsun.fund.website.wcm.client.model.WcmApp;
import com.hundsun.fund.website.wcm.client.model.WcmNodeModel;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 20:43:20
 * To change this template use File | Settings | File Templates.
 */
public class AppState {

    public static String westBarId = AppState.OWNER_WESTBAR_INIT;

     public static int centerEventId = AppConstant.WELCOME;

    public static EventType centerEventType = AppEvents.WELCOME;

    public static String westTreeItemId = "";
    public static WcmNodeModel westTreeItemObj = null;

    public static final String OWNER_WESTBAR_INIT = "";
    public static final String OWNER_ARTICLE_MANAGER = "articleManager";
    public static final String OWNER_OTHER_MANAGER = "otherManager";
    public static final String OWNER_ARTICLE_MANAGER_CALLBACK = "articleManagerCallback";
    public static final String OWNER_OTHER_MANAGER_CALLBACK = "otherManagerCallback";

    public static boolean isWestBarChanged(String targetWestBarId) {
        if (AppState.westBarId.equalsIgnoreCase(targetWestBarId)) {
            return false;
        } else {
            return true;
        }
    }

    public static Map<Long, WcmApp> wcmApps = new LinkedHashMap<Long, WcmApp>();

}
