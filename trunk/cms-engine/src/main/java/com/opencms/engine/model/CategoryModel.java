package com.opencms.engine.model;

import java.util.Date;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.util.common.page.PageBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:15
 * To change this template use File | Settings | File Templates.
 */
public class CategoryModel extends BaseModel {
	
    public CategoryBean getObject() {
    	if(!(object instanceof CategoryBean)) {
    		throw new IllegalArgumentException();
    	}
		return (CategoryBean)object;
	}

	public CategoryModel(CategoryBean categoryBean) {
		this.object = categoryBean;
	}
	
	public CategoryModel(CategoryBean categoryBean, int page, int pageSize, long totalCount) {
		this.object = categoryBean;
		this.getEngineInfo().setPage(page);
		this.getEngineInfo().setPageSize(pageSize);
		this.getEngineInfo().setTotalCount(totalCount);
	}

	private Long id;

    private String title;

    private String name;

    private String keywords;

    private String description;

    private String url;

    private String siteId;

    private String parentId;

    private double no;

    private Date creationDate;

    private Date modificationDate;

    private boolean visible;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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

    private PageBean page;

    private Menu menu;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
