package com.opencms.engine;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午9:55
 * To change this template use File | Settings | File Templates.
 */
public interface Engine {

    public String engineSite(SiteBean siteBean) throws IOException, TemplateException;

    public String engineCategory(CategoryBean categoryBean) throws IOException, TemplateException;

    public String engineCategory(CategoryBean categoryBean, int page, int pageSize) throws IOException, TemplateException;

    public String engineContent(ContentBean contentBean, boolean create) throws IOException, TemplateException;

}
