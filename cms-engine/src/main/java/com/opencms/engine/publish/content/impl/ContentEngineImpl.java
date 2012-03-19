package com.opencms.engine.publish.content.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.impl.FreemarkerEngineImpl;
import com.opencms.engine.model.CategoryModel;
import com.opencms.engine.model.ContentModel;
import com.opencms.engine.model.SiteModel;
import com.opencms.engine.util.PathUtils;
import com.opencms.template.TemplateHelper;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class ContentEngineImpl extends FreemarkerEngineImpl<ContentModel> {

	@Resource
    private ModelMapper mapper;

    @Resource
    private TemplateHelper templateHelper;
    
    @Resource
    private PathUtils pathUtils;

	public String doEngine(ContentModel model) throws IOException, TemplateException {
		ContentModel content = calculate(model);
        Template template = templateHelper.getTemplate(content.getObject());
        String html = render(template, templateModel.getModel());
        if(content.getEngineInfo().isCreate())
        	create(html, pathUtils.getContentPath(content.getObject()));
        return html;
	}
	
	protected ContentModel calculate(ContentModel model) {
		ContentModel content = model;
        templateModel.clean();
        ContentBean contentBean = content.getObject();
        templateModel.addModel(mapper.map(contentBean, content));
        CategoryBean categoryBean = content.getObject().getCategory();
        templateModel.addModel(mapper.map(categoryBean, new CategoryModel(categoryBean)));
        SiteBean siteBean = categoryBean.getSite();
        templateModel.addModel(mapper.map(siteBean, new SiteModel(siteBean)));
        return content;
	}

	@Override
	@PostConstruct
	public void init() {
		
	}

}
