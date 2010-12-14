package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opencms.engine.ModelMapper;
import com.opencms.template.TemplateHelper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    @Resource(name = "templateHelper")
    private TemplateHelper templateHelper;

    @Resource(name = "cmsMapper")
    private ModelMapper mapper;

    @Override
    public String engineSite(String siteId) throws IOException, TemplateException {
        SiteBean siteBean = cmsManager.getSiteService().getSiteById(siteId);
        if(siteBean != null){
            templateModel.clean();
            templateModel.setSite(mapper.map(siteBean));
            String template = templateHelper.getTemplate(siteBean);
            return render(template, templateModel.getModel());
        }
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
