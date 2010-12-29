package com.opencms.app.content;

import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 12:45:19
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Scope("prototype")
public class ContentAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(ContentAction.class);

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

    @Resource
    private CmsManager cmsManager;

    @Resource
    private Engine engine;

    @Transactional(readOnly = true)
    public String view(){
        logger.debug("content-id:[{}]", id);
        try{
            ContentBean contentBean = cmsManager.getContentService().getContentById(id);
            if(contentBean != null){
                html = engine.engineContent(contentBean, false);
            } else{
                return "404";
            }
        } catch (Exception e){
            logger.error("engine content error : ", e);
            return ERROR;
        }
        return SUCCESS;
    }

}
