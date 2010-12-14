package com.opencms.util;

import com.opencms.util.mapper.BeanMapperHelper;
import com.opencms.util.resources.ResourceHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午8:53
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CmsUtils {

    @Resource(name = "resourceHelper")
    private ResourceHelper resourceHelper;

    @Resource(name = "beanMapperHelper")
    private BeanMapperHelper beanMapperHelper;

    public ResourceHelper getResourceHelper() {
        return resourceHelper;
    }

    public BeanMapperHelper getBeanMapperHelper() {
        return beanMapperHelper;
    }
}
