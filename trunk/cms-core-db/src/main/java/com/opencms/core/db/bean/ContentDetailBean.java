package com.opencms.core.db.bean;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-22
 * Time: 上午8:54
 * To change this template use File | Settings | File Templates.
 */
@Searchable
@Entity
@Table(name = "cms_content_detail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentDetailBean extends BaseEntity {

    public ContentDetailBean(String content) {
        this.content = content;
    }

    public ContentDetailBean() {
    }

    @Lob
    @SearchableProperty
    private String content;

    @OneToOne(cascade = {CascadeType.ALL}, mappedBy = "contentDetail")
    private ContentBean contentBean;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentBean getContentBean() {
        return contentBean;
    }

    public void setContentBean(ContentBean contentBean) {
        this.contentBean = contentBean;
    }
}
