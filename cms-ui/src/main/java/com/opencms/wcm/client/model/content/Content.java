package com.opencms.wcm.client.model.content;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Date;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-10-4
 * Time: 12:16:55
 * To change this template use File | Settings | File Templates.
 */
public class Content implements Serializable, IsSerializable {

    public Content() {
        super();
    }

    private String id;

    private String title;

    private String keywords;

    private String description;

    private String content;

    private Date creationDate;

    private String categoryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
