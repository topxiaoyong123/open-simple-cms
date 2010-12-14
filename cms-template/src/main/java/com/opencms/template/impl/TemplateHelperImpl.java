package com.opencms.template.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.template.TemplateHelper;
import com.opencms.util.CmsUtils;
import com.opencms.util.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:02
 * To change this template use File | Settings | File Templates.
 */
@Component("templateHelper")
public class TemplateHelperImpl implements TemplateHelper {

    @Autowired
    private CmsUtils cmsUtils;

    public String getTemplate(SiteBean siteBean) {
        String basePath = getBasePath(siteBean);
        String indexTemplate = siteBean.getIndexTemplate() == null || "".equals(siteBean.getIndexTemplate()) ? Constants.DEFAULT_INDEX_TEMPLATE : siteBean.getIndexTemplate();
        return basePath + "/" + indexTemplate;
    }

    public String getTemplate(CategoryBean categoryBean) {
        String basePath = getBasePath(categoryBean.getSite());
        String categoryTemplate = categoryBean.getTemplate() == null || "".equals(categoryBean.getTemplate()) ? Constants.DEFAULT_CATEGORY_TEMPLATE : categoryBean.getTemplate();
        return basePath + "/" + categoryTemplate;
    }

    public String getTemplate(ContentBean contentBean) {
        String basePath = getBasePath(contentBean.getCategory().getSite());
        String contentTemplate = contentBean.getTemplate() == null || "".equals(contentBean.getTemplate()) ? Constants.DEFAULT_CONTENT_TEMPLATE : contentBean.getTemplate();
        return basePath + "/" + contentTemplate;
    }

    private String getBasePath(SiteBean siteBean){
        String template = siteBean.getTemplate();
        String basePath = null;
        if(template != null && !"".equals(template)){
            basePath = template;
        } else{
            basePath = Constants.DEFAULT_BASE_TEMPLATE_PATH;
        }
        return basePath;
    }
}
