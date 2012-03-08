package com.opencms.engine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.EngineUtil;
import com.opencms.engine.TemplateModel;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TemplateModelImpl extends BaseTemplateModel implements TemplateModel {

    private static final Logger logger = LoggerFactory.getLogger(TemplateModelImpl.class);

    @Resource
    private CmsManager cmsManager;

    @Resource
    private EngineUtil engineUtil;

    private Map outModel = Collections.synchronizedMap(new HashMap());
    
    public void initModel(){
    	outModel.put("engineUtil", engineUtil);
        for(Model model : models) {
        	logger.debug("{0} init", model.getModelName());
        	outModel.put(model.getModelName(), model);
        }
    }

    public Map getModel() {
        initModel();
        return outModel;
    }

    public void clean() {
    	outModel.clear();
        models.clear();
    }
}
