package com.opencms.template.bean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-17
 * Time: 上午11:35
 * To change this template use File | Settings | File Templates.
 */
public class CmsTemplateBean {

    private String name;

    private String title;

    private String description;

    private String shortcut;

    private String type;          //0，站点模板；1，首页模板；2，栏目模板；3，文章模板

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
