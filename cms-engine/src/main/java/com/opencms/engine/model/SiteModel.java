package com.opencms.engine.model;

import java.util.Date;

import com.opencms.core.db.bean.SiteBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:15
 * To change this template use File | Settings | File Templates.
 */
public class SiteModel extends BaseModel implements Model {

	public SiteBean getObject() {
    	if(!(object instanceof SiteBean)) {
    		throw new IllegalArgumentException();
    	}
		return (SiteBean)object;
	}
	
	public SiteModel(SiteBean siteBean) {
		this.object = siteBean;
	}
	
	private String id;

    private String title;

    private String name;

    private String keywords;

    private String description;

    private String url;

    private String template;

    private String indexTemplate;

    private Date creationDate;

    private Date modificationDate;

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

	public String getIndexTemplate() {
		return indexTemplate;
	}

	public void setIndexTemplate(String indexTemplate) {
		this.indexTemplate = indexTemplate;
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

	private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
