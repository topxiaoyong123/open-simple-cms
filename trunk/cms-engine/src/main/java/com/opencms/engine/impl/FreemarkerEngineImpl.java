package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.Engine;
import com.opencms.engine.TemplateModel;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.EngineInfo;
import com.opencms.engine.model.Site;
import com.opencms.util.CmsUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    private Configuration freeMarkerConfiguration;

    @Resource(name = "templateModel")
    protected TemplateModel templateModel;

    @PostConstruct
    public void initEngine(){
        freeMarkerConfiguration = new Configuration();
		freeMarkerConfiguration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
		String templatePath = "classpath:/template/";
        try{
            templatePath = cmsUtils.getResourceHelper().getWcmResource().getTemplatePath();
            logger.debug("初始化FreeMarker模板目录:{}", templatePath);
            freeMarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));
			freeMarkerConfiguration.setDefaultEncoding(cmsUtils.getResourceHelper().getWcmResource().getDefaultEncoding());
        } catch (IOException e) {
			logger.error("初始化FreeMarker模板目录:" + templatePath
					+ " 异常。");
			e.printStackTrace();
		}
    }

    public abstract String engine(EngineInfo info) throws IOException, TemplateException;

    public abstract String engineSite(EngineInfo info) throws IOException, TemplateException;

    public abstract String engineCategory(EngineInfo info);

    public abstract String engineContent(EngineInfo info);

    public String render(String template, Map model) throws TemplateException, IOException {
        Template temp;
		temp = freeMarkerConfiguration.getTemplate(template);
		Writer out = new StringWriter();
		temp.process(model, out);
		return out.toString();
    }

}
