package com.opencms.engine.publish.category.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.impl.FreemarkerEngineImpl;
import com.opencms.engine.model.CategoryModel;
import com.opencms.engine.model.Model;
import com.opencms.engine.model.SiteModel;
import com.opencms.engine.util.PathUtils;
import com.opencms.template.TemplateHelper;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class CategoryEngineImpl extends FreemarkerEngineImpl<SiteModel> {

	@Resource
    private ModelMapper mapper;

    @Resource
    private TemplateHelper templateHelper;
    
    @Resource
    private PathUtils pathUtils;

	public String engine(Model model) throws IOException, TemplateException {
		CategoryModel category = calculate(model);
        Template template = templateHelper.getTemplate(category.getObject());
        String html = render(template, templateModel.getModel());
        if(category.getEngineInfo().isCreate())
        	create(html, pathUtils.getCategoryPath(category.getObject(), category.getEngineInfo().getPage()));
        return html;
	}
	
	protected CategoryModel calculate(Model model) {
		if(!(model instanceof CategoryModel)) {
			throw new IllegalArgumentException();
		}
		CategoryModel category = (CategoryModel) model;
        templateModel.clean();
        CategoryBean categoryBean = category.getObject();
        templateModel.addModel(mapper.map(categoryBean, category));
        SiteBean siteBean = category.getObject().getSite();
        templateModel.addModel(mapper.map(siteBean, new SiteModel(siteBean)));
        return category;
	}

	@Override
	@PostConstruct
	public void init() {
		
	}

}
