package com.opencms.util.resources;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 12:27:07
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ResourceHelper {

    @Resource(name = "templateResource")
    private TemplateResource templateResource;

    @Resource(name = "wcmResource")
    private WcmResource wcmResource;

    public TemplateResource getTemplateResource() {
        return templateResource;
    }

    public WcmResource getWcmResource() {
        return wcmResource;
    }
}
