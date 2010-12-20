package com.opencms.app.category;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opencms.engine.model.EngineInfo;
import com.opencms.util.CmsType;
import com.opencms.util.common.page.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 13:28:18
 * To change this template use File | Settings | File Templates.
 */
public class CategoryAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(CategoryAction.class);

    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int page = 1;

    private int pagesize = PageBean.DEFAULT_SIZE;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    @Resource(name = "appEngine")
    private Engine cmsEngine;

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    public String view(){
        logger.debug("category-id:[{}] page:[{}] pagesize:[{}]", new Object[]{id, page, pagesize});
        try{
            CategoryBean categoryBean = cmsManager.getCategoryService().getCategoryById(id);
            if(categoryBean != null){
                EngineInfo info = new EngineInfo(CmsType.CATEGORY, categoryBean.getId(), categoryBean.getName());
                html = cmsEngine.engine(info);
            } else {
                return "404";
            }
        } catch (Exception e){
            logger.error("engine category error : ", e);
            return ERROR;
        }
        return SUCCESS;
    }

}
