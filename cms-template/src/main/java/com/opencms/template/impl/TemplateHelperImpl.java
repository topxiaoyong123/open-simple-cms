package com.opencms.template.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.template.TemplateHelper;
import com.opencms.util.CmsUtils;
import com.opencms.util.common.Constants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:02
 * To change this template use File | Settings | File Templates.
 */
@Component("templateHelper")
public class TemplateHelperImpl implements TemplateHelper {

    @Resource(name = "cmsUtils")
    private CmsUtils cmsUtils;

    public String getTemplate(SiteBean siteBean) {
        String indexTemplate = siteBean.getIndexTemplate() == null || "".equals(siteBean.getIndexTemplate()) ? Constants.DEFAULT_INDEX_TEMPLATE : siteBean.getIndexTemplate();
        return indexTemplate;
    }

    public String getTemplate(CategoryBean categoryBean) {
        String categoryTemplate = categoryBean.getTemplate() == null || "".equals(categoryBean.getTemplate()) ? Constants.DEFAULT_CATEGORY_TEMPLATE : categoryBean.getTemplate();
        return categoryTemplate;
    }

    public String getTemplate(ContentBean contentBean) {
        String contentTemplate = contentBean.getTemplate() == null || "".equals(contentBean.getTemplate()) ? Constants.DEFAULT_CONTENT_TEMPLATE : contentBean.getTemplate();
        return contentTemplate;
    }

    public String getBasePath(SiteBean siteBean){
        String template = siteBean.getTemplate();
        String basePath = null;
        if(template != null && !"".equals(template)){
            basePath = template;
        } else{
            basePath = Constants.DEFAULT_BASE_TEMPLATE_PATH;
        }
        return cmsUtils.getResourceHelper().getTemplateResource().getTemplatePath() + "/" + basePath;
    }
}
