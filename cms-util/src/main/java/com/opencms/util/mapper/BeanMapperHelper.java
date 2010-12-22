package com.opencms.util.mapper;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;

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

    public void clientSimpleMap(Object source, Object target){
        if(source == null || target == null){
            return;
        }
        Class sClass = source.getClass();
        Class tClass = target.getClass();
        for(Field sField : sClass.getDeclaredFields()){
            String fieldName = sField.getName();
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            String getter = null;
            String setter = "set" + fieldName;
            if(sField.getType().getName().equalsIgnoreCase(boolean.class.getName()) || sField.getType().getName().equalsIgnoreCase(Boolean.class.getName())){
                getter = "is" + fieldName;
            } else{
                getter = "get" + fieldName;
            }
            try{
                Object getterResult = sClass.getMethod(getter).invoke(source);
                tClass.getMethod(setter, sField.getType()).invoke(target, getterResult);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
