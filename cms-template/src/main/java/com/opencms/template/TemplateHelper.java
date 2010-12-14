package com.opencms.template;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import freemarker.template.Template;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:02
 * To change this template use File | Settings | File Templates.
 */
public interface TemplateHelper {

    public String getTemplate(SiteBean siteBean);

    public String getTemplate(CategoryBean categoryBean);

    public String getTemplate(ContentBean contentBean);

}
