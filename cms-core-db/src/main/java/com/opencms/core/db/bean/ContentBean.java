package com.opencms.core.db.bean;

import org.compass.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-22
 * Time: 上午8:58
 * To change this template use File | Settings | File Templates.
 */
@Searchable
@Entity
@Table(name = "cms_content")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContentBean extends BaseEntity {

    @SearchableProperty(index = Index.TOKENIZED, boost = 3F)
    @Column(nullable = false, length = 128)
    private String title;

    @SearchableProperty(index = Index.TOKENIZED, boost = 2F)
    @Column(length = 255)
    private String keywords;

    @SearchableProperty(index = Index.NO)
    @Column(length = 128)
    private String url;

    @SearchableProperty(index = Index.TOKENIZED, boost = 1.5F)
    @Column(length = 255)
    private String description;

    @SearchableProperty(index = Index.NO)
    @Column
    private double no;

    @SearchableProperty(store = Store.NO)
    @Column(nullable = false, length = 3)
    private String state;

    @SearchableProperty(store = Store.NO)
    @Column(length = 3)
    private String top;

    @SearchableProperty(index = Index.NO)
    @Column(nullable = false, length = 3)
    private String type;

    @SearchableProperty(index = Index.NO)
    @Column(length = 32)
    private String source;

    @Column(length = 32)
    private String template;

    @SearchableProperty(index = Index.NO)
    @Column(length = 32)
    private String author;

    @Column
    private boolean createHtml;

    @SearchableComponent
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "content", nullable = false)
    private ContentDetailBean contentDetail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category", nullable = false)
    private CategoryBean category;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getNo() {
        return no;
    }

    public void setNo(double no) {
        this.no = no;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isCreateHtml() {
        return createHtml;
    }

    public void setCreateHtml(boolean createHtml) {
        this.createHtml = createHtml;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

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
