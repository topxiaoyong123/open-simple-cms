package com.opencms.engine.model;

import com.opencms.util.common.page.PageBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-16
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public class EngineInfo<T extends Model> {

	private T model;
	
    public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public EngineInfo() {
	}

	public EngineInfo(T model) {
    	this.model = model;
    }

    private String id;

    private String name;

    private int page = 1;

    private int pageSize = PageBean.DEFAULT_SIZE;
    
    private long totalCount;
    
    private boolean create = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}
    
}
