package com.opencms.engine.impl;

import com.opencms.engine.Engine;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.EngineInfo;
import com.opencms.template.TemplateHelper;
import com.opencms.engine.model.Site;
import com.opencms.util.CmsType;
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
@Component("cmsEngine")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CmsEngineImpl extends FreemarkerEngineImpl implements Engine {

    @Resource(name = "templateHelper")
    private TemplateHelper templateHelper;

    @Resource(name = "cmsMapper")
    private ModelMapper mapper;

    @Override
    public String engine(EngineInfo info) throws IOException, TemplateException {
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
        String template = templateHelper.getTemplate(site);
        return render(template, templateModel.getModel());
    }

    @Override
    public String engineCategory(EngineInfo info) {
        return null;
    }

    @Override
    public String engineContent(EngineInfo info) {
        return null;
    }

}
