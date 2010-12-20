package com.opencms.util.mapper;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午8:45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BeanMapperHelper {

    @Resource(name = "mapper")
    private DozerBeanMapper mapper;

    public Object simpleMap(Object source, Class targetClass){
        return mapper.map(source, targetClass);
    }

    public void simpleMap(Object source, Object target){
        mapper.map(source, target);
    }

}
