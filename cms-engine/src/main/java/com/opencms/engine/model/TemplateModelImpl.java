package com.opencms.engine.model;

import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.TemplateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
@Component("templateModel")
public class TemplateModelImpl extends BaseTemplateModel implements TemplateModel {

    private static final Logger logger = LoggerFactory.getLogger(TemplateModelImpl.class);

    @Resource(name = "cmsManager")
    private CmsManager cmsManager;

    private Map model = Collections.synchronizedMap(new HashMap());

    public void initModel(){
        model.put("cmsManager", cmsManager);
        if(this.getSite() != null){
            logger.debug("siteModel init");
            model.put("siteModel", this.getSite());
        }
        if(this.getCategory() != null){
            logger.debug("categoryModel init");
            model.put("categoryModel", this.getCategory());
        }
        if(this.getContent() != null){
            logger.debug("contentModel init");
            model.put("contentModel", this.getContent());
        }
    }

    public Map getModel() {
        initModel();
        return model;
    }

    public void clean() {
        model.clear();
        this.setSite(null);
		this.setCategory(null);
		this.setContent(null);
    }
}
