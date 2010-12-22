package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.bean.field.ContentField;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opencms.engine.EngineUtil;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.EngineInfo;
import com.opencms.template.TemplateHelper;
import com.opencms.engine.model.Site;
import com.opencms.util.CmsType;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午9:58
 * To change this template use File | Settings | File Templates.
 */
@Component("appEngine")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class AppEngineImpl extends FreemarkerEngineImpl implements Engine {

    @Resource(name = "templateHelper")
    private TemplateHelper templateHelper;

    @Resource(name = "appMapper")
    private ModelMapper mapper;

    @Resource(name = "appEngineUtil")
    private EngineUtil engineUtil;

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    public String engine(EngineInfo info) throws IOException, TemplateException {
        templateModel.setEngineUtil(engineUtil);
        if(CmsType.SITE == info.getType()){
            return engineSite(info);
        } else if(CmsType.CATEGORY == info.getType()){
            return engineCategory(info);
        } else if(CmsType.CONTENT == info.getType()){
            return engineContent(info);
        }
        return null;
    }

    public String engineSite(EngineInfo info) throws IOException, TemplateException {
        templateModel.clean();
        SiteBean siteBean = cmsManager.getSiteService().getSiteById(info.getId());
        Site site = mapper.map(siteBean);
        templateModel.setSite(site);
        Template template = templateHelper.getTemplate(site);
        return render(template, templateModel.getModel());
    }

    public String engineCategory(EngineInfo info) throws IOException, TemplateException {
        templateModel.clean();
        CategoryBean categoryBean = cmsManager.getCategoryService().getCategoryById(info.getId());
        int contentsCount = (int)cmsManager.getContentService().getCountByCategoryId(categoryBean.getId(), ContentField._STATE_PUBLISHED);
        Category category = mapper.map(categoryBean, info.getPage(), info.getPageSize(), contentsCount);
        templateModel.setCategory(category);
        Template template = templateHelper.getTemplate(category);
        return render(template, templateModel.getModel());
    }

    public String engineContent(EngineInfo info) throws IOException, TemplateException {
        templateModel.clean();
        ContentBean contentBean = cmsManager.getContentService().getContentById(info.getId());
        Content content = mapper.map(contentBean);
        Category category = mapper.map(contentBean.getCategory());
        templateModel.setContent(content);
        templateModel.setCategory(category);
        Template template = templateHelper.getTemplate(contentBean);
        return render(template, templateModel.getModel());
    }

}
