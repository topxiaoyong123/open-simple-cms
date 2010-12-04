package com.opencms.wcm.client.model;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-4
 * Time: 10:04:25
 * To change this template use File | Settings | File Templates.
 */
public class WcmNodeModel extends BaseModelData implements BeanModelTag, Serializable, IsSerializable {

    private String uuid;

    private String name;

    private String nodetype;   //0是站点，1是栏目

    private String title;


    public WcmNodeModel() {
    }


    //0 is site,1 is topic,2 is article
    public WcmNodeModel(String uuid, String name, String title,String nodetype) {
        set("uuid", uuid);
        set("name", name);
        set("title", title);
        set("nodetype", nodetype);
    }


    public String getNodetype() {
        return (String) get("nodetype");
    }

    public String getPath() {
        return (String) get("path");
    }

    public String getDescription() {
        return (String) get("description");
    }

    public String getUuid() {
        return (String) get("uuid");
    }

    public String getName() {
        return (String) get("name");
    }

    public String getTitle() {
        return (String) get("title");
    }

    public String getUrl() {
        return (String) get("url");
    }

    public String toString() {
        return getUuid() + "|" + getName() + "|" + getTitle() + "|" + getDescription() + "|" + getUrl();
    }
}
