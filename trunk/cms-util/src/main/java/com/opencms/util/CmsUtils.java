package com.opencms.util;

import com.opencms.util.mapper.BeanMapperHelper;
import com.opencms.util.resources.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午8:53
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CmsUtils {

    @Autowired
    private ResourceHelper resourceHelper;

    @Autowired
    private BeanMapperHelper beanMapperHelper;

    public ResourceHelper getResourceHelper() {
        return resourceHelper;
    }

    public BeanMapperHelper getBeanMapperHelper() {
        return beanMapperHelper;
    }
}
