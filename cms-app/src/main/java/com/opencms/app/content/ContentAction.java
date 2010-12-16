package com.opencms.app.content;

import com.opencms.core.db.service.CmsManager;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 12:45:19
 * To change this template use File | Settings | File Templates.
 */
public class ContentAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(ContentAction.class);

    private String id;

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String view(){
        logger.debug("content-id:[{}]", id);
        cmsManager.getContentService().getContentById(id);
        return SUCCESS;
    }

}
