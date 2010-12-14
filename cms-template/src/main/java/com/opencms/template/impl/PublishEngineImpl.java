package com.opencms.template.impl;

import com.opencms.template.Engine;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

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

}
