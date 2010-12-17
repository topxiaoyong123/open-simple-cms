package com.opencms.engine.impl;

import com.opencms.engine.Engine;
import com.opencms.engine.TemplateModel;
import com.opencms.engine.model.EngineInfo;
import com.opencms.util.CmsUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午11:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class FreemarkerEngineImpl implements Engine {

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerEngineImpl.class);

   @Resource(name = "cmsUtils")
    private CmsUtils cmsUtils;

    @Resource(name = "templateModel")
    protected TemplateModel templateModel;

    public abstract String engine(EngineInfo info) throws IOException, TemplateException;

    public abstract String engineSite(EngineInfo info) throws IOException, TemplateException;

    public abstract String engineCategory(EngineInfo info);

    public abstract String engineContent(EngineInfo info);

    public String render(Template template, Map model) throws TemplateException, IOException {
		Writer out = new StringWriter();
		template.process(model, out);
		return out.toString();
    }

}
