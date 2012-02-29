package com.opencms.wcm.client.model.file;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.BeanModelTag;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 11:50:42
 * To change this template use File | Settings | File Templates.
 */
public class WcmFile extends BaseModelData implements BeanModelTag, Serializable, IsSerializable {

    private String filename;

    private String path;

    private String url;

    private String type;     //0,文件夹；1,文件

    private String shortcut;

    private String template;

    private Long size;

    private Date lastModify;

    private String shortName;
    
    private String hasChild;

    public WcmFile() {
    }

    public WcmFile(String filename, String path, String url, String type, String shortcut, String template) {
        set("filename", filename);
        set("path", path);
        set("url", url);
        set("type", type);
        set("shortcut", shortcut);
        set("template", template);
    }

    public WcmFile(String filename, String path, String url, String type, String shortcut, String template, Long size, Date lastModify) {
        set("filename", filename);
        set("path", path);
        set("url", url);
        set("type", type);
        set("shortcut", shortcut);
        set("template", template);
        set("size", size);
        set("lastModify", lastModify);
    }

    public String getFilename() {
        return get("filename");
    }

    public String getPath() {
        return get("path");
    }

    public String getUrl() {
        return get("url");
    }

    public String getType() {
        return get("type");
    }

    public void setFilename(String filename) {
        set("filename", filename);
    }

    public void setPath(String path) {
        set("path", path);
    }

    public void setUrl(String url) {
        set("url", url);
    }

    public void setType(String type) {
        set("type", type);
    }

    public String getShortcut() {
        return get("shortcut");
    }

    public void setShortcut(String shortcut) {
        set("shortcut", shortcut);
    }

    public String getTemplate() {
        return get("template");
    }

    public void setTemplate(String template) {
        set("template", template);
    }

    public Long getSize() {
        return get("size");
    }

    public void setSize(Long size) {
        set("size", size);
    }

    public Date getLastModify() {
        return get("lastModify");
    }

    public void setLastModify(Date lastModify) {
        set("lastModify", lastModify);
    }

    public String getShortName() {
        return get("shortName");
    }

    public void setShortName(String shortName) {
        set("shortName", shortName);
    }
    

    public String getHasChild() {
		return get("hasChild");
	}

	public void setHasChild(String hasChild) {
		set("hasChild", hasChild);
	}

	@Override
    public String toString() {
        return "path:" + this.getPath() + ",type:" + this.getType();
    }
}
