package com.opencms.engine.publish.site.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.impl.FreemarkerEngineImpl;
import com.opencms.engine.model.Model;
import com.opencms.engine.model.SiteModel;
import com.opencms.engine.util.PathUtils;
import com.opencms.template.TemplateHelper;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class SiteEngineImpl extends FreemarkerEngineImpl<SiteModel> {

	@Resource
    private ModelMapper mapper;

    @Resource
    private TemplateHelper templateHelper;
    
    @Resource
    private PathUtils pathUtils;

	public String doEngine(SiteModel model) throws IOException, TemplateException {
		SiteModel site = calculate(model);
        Template template = templateHelper.getTemplate(site.getObject());
        String html = render(template, templateModel.getModel());
        if(site.getEngineInfo().isCreate())
        	create(html, pathUtils.getSitePath(site.getObject()));
        return html;
	}
	
	protected SiteModel calculate(SiteModel model) {
		SiteModel site = model;
        templateModel.clean();
        SiteBean siteBean = site.getObject();
        templateModel.addModel(mapper.map(siteBean, site));
        return site;
	}

	@Override
	@PostConstruct
	public void init() {
	}

}
