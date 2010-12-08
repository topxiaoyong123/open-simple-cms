package com.opencms.wcm.client;

import com.extjs.gxt.ui.client.event.EventType;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-8
 * Time: 14:06:38
 * To change this template use File | Settings | File Templates.
 */
public class AppEventType extends EventType {

    private static int count1 = 0;

    private String eid;

    public String getId(){
        return eid;    
    }

    public AppEventType() {
        super();
        eid = String.valueOf(count1++);
    }
}
