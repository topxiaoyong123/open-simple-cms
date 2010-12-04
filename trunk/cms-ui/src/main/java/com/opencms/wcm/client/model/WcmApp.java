package com.opencms.wcm.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-3
 * Time: 11:49:42
 * To change this template use File | Settings | File Templates.
 */
public class WcmApp implements Serializable, IsSerializable {

    private Long id;

    private String name;

    private String url;

    private String icon;

    public WcmApp(Long id, String name, String url, String icon) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.icon = icon;
    }

    public WcmApp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "(id:" + id + ", name:" + name + ", url:" + url + ", icon:" + icon + ")";
    }
}
