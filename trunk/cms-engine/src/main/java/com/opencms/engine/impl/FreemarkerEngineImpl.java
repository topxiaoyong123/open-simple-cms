package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.Engine;
import com.opencms.engine.TemplateModel;
import com.opencms.util.CmsUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
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

   @Resource
    private CmsUtils cmsUtils;

    @Resource
    protected TemplateModel templateModel;

    public abstract void init();

    public abstract String engineSite(SiteBean siteBean) throws IOException, TemplateException;

    public abstract String engineCategory(CategoryBean categoryBean) throws IOException, TemplateException;

    public abstract String engineCategory(CategoryBean categoryBean, int page, int pageSize) throws IOException, TemplateException;

    public abstract String engineContent(ContentBean contentBean, boolean create) throws IOException, TemplateException;

    public String render(Template template, Map model) throws TemplateException, IOException {
		Writer out = new StringWriter();
		template.process(model, out);
		return out.toString();
    }

}
