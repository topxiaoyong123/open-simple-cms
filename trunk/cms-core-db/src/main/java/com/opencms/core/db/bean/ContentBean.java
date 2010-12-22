package com.opencms.core.db.bean;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-22
 * Time: 上午8:58
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ContentBean extends SimpleContentBean {

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "content", nullable = false)
    private ContentDetailBean contentDetail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category", nullable = false)
    private CategoryBean category;

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public ContentDetailBean getContentDetail() {
        return contentDetail;
    }

    public void setContentDetail(ContentDetailBean contentDetail) {
        this.contentDetail = contentDetail;
    }
}
