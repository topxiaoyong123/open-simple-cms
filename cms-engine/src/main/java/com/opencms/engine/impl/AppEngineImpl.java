package com.opencms.engine.impl;

import com.opencms.engine.Engine;
import com.opencms.engine.EngineUtil;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.EngineInfo;
import com.opencms.template.TemplateHelper;
import com.opencms.engine.model.Site;
import com.opencms.util.CmsType;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
public class AppEngineImpl extends FreemarkerEngineImpl implements Engine {

    @Resource(name = "templateHelper")
    private TemplateHelper templateHelper;

    @Resource(name = "appMapper")
    private ModelMapper mapper;

    @Resource(name = "appEngineUtil")
    private EngineUtil engineUtil;

    @Override
    public String engine(EngineInfo info) throws IOException, TemplateException {
        templateModel.setEngineUtil(engineUtil);
        if(CmsType.SITE == info.getType()){
            return engineSite(info);
        } else if(CmsType.CATEGORY == info.getType()){
            return engineCategory(info);
        } else if(CmsType.CONTENT == info.getType()){
            return engineSite(info);
        }
        return null;
    }

    @Override
    public String engineSite(EngineInfo info) throws IOException, TemplateException {
        templateModel.clean();
        Site site = (Site)mapper.map(info);
        templateModel.setSite(site);
        Template template = templateHelper.getTemplate(site);
        return render(template, templateModel.getModel());
    }

    @Override
    public String engineCategory(EngineInfo info) throws IOException, TemplateException {
        templateModel.clean();
        Category category = (Category)mapper.map(info);
        templateModel.setCategory(category);
        Template template = templateHelper.getTemplate(category);
        return render(template, templateModel.getModel());
    }

    @Override
    public String engineContent(EngineInfo info) {
        templateModel.clean();
        return null;
    }

}
