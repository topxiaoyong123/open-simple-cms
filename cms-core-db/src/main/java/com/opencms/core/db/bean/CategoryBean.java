package com.opencms.core.db.bean;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-6
 * Time: 20:33:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "cms_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoryBean implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    private String id;

    @Column(nullable = false, length = 128)
    private String title;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(length = 255)
    private String keywords;

    @Column(length = 255)
    private String description;

    @Column(length = 128)
    private String url;

    @Column(length = 32)
    private String template;

    @Column
    private double no = 0;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    private boolean visible;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "parent")
    private CategoryBean parent;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "parent")
    @OrderBy("no")
    private Set<CategoryBean> children = new LinkedHashSet<CategoryBean>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "site", nullable = false)
    private SiteBean site;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public double getNo() {
        return no;
    }

    public void setNo(double no) {
        this.no = no;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public CategoryBean getParent() {
        return parent;
    }

    public void setParent(CategoryBean parent) {
        this.parent = parent;
    }

    public Set<CategoryBean> getChildren() {
        return children;
    }

    public void setChildren(Set<CategoryBean> children) {
        this.children = children;
    }

    public SiteBean getSite() {
        return site;
    }

    public void setSite(SiteBean site) {
        this.site = site;
    }

    public void addChild(CategoryBean child){
        if(!children.contains(child)){
            children.add(child);
        }
    }

    public void removeChild(CategoryBean child){
        children.remove(child);
    }
}