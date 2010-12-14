package com.opencms.template.impl;

import com.opencms.template.Engine;
import com.opencms.util.CmsUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

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
