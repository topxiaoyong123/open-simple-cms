package com.opencms.util.resources;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 12:27:07
 * To change this template use File | Settings | File Templates.
 */
public class ResourceHelper {

    @Autowired
    private TemplateResource templateResource;

    @Autowired
    private WcmResource wcmResource;

    public TemplateResource getTemplateResource() {
        return templateResource;
    }

    public WcmResource getWcmResource() {
        return wcmResource;
    }
}
