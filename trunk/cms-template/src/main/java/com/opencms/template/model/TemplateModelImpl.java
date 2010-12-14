package com.opencms.template.model;

import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.service.CmsManager;
import com.opencms.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private CmsManager cmsManager;

    private Map model = Collections.synchronizedMap(new HashMap());

    public void initModel(){
        model.put("cmsManager", cmsManager);
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
