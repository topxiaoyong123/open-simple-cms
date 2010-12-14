package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opencms.template.TemplateHelper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午9:58
 * To change this template use File | Settings | File Templates.
 */
@Component("cmsEngine")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CmsEngineImpl extends FreemarkerEngineImpl implements Engine {

    @Autowired
    private CmsManager cmsManager;

    @Autowired
    private TemplateHelper templateHelper;

    @Override
    public String engineSite(String siteId) throws IOException, TemplateException {
        SiteBean siteBean = cmsManager.getSiteService().getSiteById(siteId);
        if(siteBean != null){

        }
        return null;
    }

    @Override
    public String engineCategory(String categoryId) {
        return null;
    }

    @Override
    public String engineContent(String contentId) {
        return null;
    }

    @Override
    public Site map(SiteBean siteBean) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Category map(CategoryBean categoryBean) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Content map(ContentBean contentBean) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
