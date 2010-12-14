package com.opencms.engine;

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

    public String engineSite(String siteId) throws IOException, TemplateException;

    public String engineCategory(String categoryId);

    public String engineContent(String contentId);

}
