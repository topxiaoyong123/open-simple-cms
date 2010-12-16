package com.opencms.engine.model;

import com.opencms.util.CmsType;
import com.opencms.util.common.page.PageBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-16
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
public class EngineInfo<T extends Model> {

    public EngineInfo(CmsType type, String id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public EngineInfo() {
    }

    private String id;

    private String name;

    private CmsType type;

    private int page = 1;

    private int pageSize = PageBean.DEFAULT_SIZE;

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

    public CmsType getType() {
        return type;
    }

    public void setType(CmsType type) {
        this.type = type;
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
}
