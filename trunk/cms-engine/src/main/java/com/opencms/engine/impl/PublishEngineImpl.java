package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.Engine;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Component("publishEngine")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PublishEngineImpl extends FreemarkerEngineImpl implements Engine {

    @Override
    public String engineSite(String siteId) throws IOException, TemplateException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String engineCategory(String categoryId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String engineContent(String contentId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
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
