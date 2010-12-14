package com.opencms.util.mapper;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午8:45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class BeanMapperHelper {

    @Autowired
    private DozerBeanMapper mapper;

    public Object map(Object source, Class targetClass){
        return mapper.map(source, targetClass);
    }

    public void map(Object source, Object target){
        mapper.map(source, target);
    }

}
