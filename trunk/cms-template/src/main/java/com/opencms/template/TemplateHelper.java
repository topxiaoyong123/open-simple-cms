package com.opencms.template;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.template.bean.CmsTemplateBean;
import freemarker.template.Template;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:02
 * To change this template use File | Settings | File Templates.
 */
public interface TemplateHelper {

    public Template getTemplate(SiteBean siteBean) throws IOException;

    public Template getTemplate(CategoryBean categoryBean) throws IOException;

    public Template getTemplate(ContentBean contentBean) throws IOException;

    public List<CmsTemplateBean> getTemplateBeans(String baseTemplate, String type);

}
