package com.opencms.engine.publish.impl;

import com.opencms.engine.Engine;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.impl.FreemarkerEngineImpl;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource(name = "publishMapper")
    private ModelMapper mapper;

    @Override
    public String engineSite(String siteId) throws IOException, TemplateException {
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

}
