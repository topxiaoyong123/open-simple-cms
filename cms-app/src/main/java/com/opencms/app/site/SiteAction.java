package com.opencms.app.site;

import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午11:54
 * To change this template use File | Settings | File Templates.
 */
public class SiteAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(SiteAction.class);

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String html;

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    @Resource(name = "cmsEngine")
    private Engine cmsEngine;

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    public String view(){
        try{
            SiteBean siteBean = cmsManager.getSiteService().getSiteByName(name);
            if(siteBean != null){
                html = cmsEngine.engineSite(siteBean.getId());
            } else {
                return "404";
            }
        } catch (Exception e){
            logger.error("engine site error : ", e);
            return ERROR;
        }
        return SUCCESS;
    }
}
