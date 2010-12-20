package test;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.engine.model.Category;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-20
 * Time: 下午10:44
 * To change this template use File | Settings | File Templates.
 */
public class CreateMapper {

    public static void createMap(Class source, String sBeanName, Class destination, String dBeanName){
        for(Field sField : source.getDeclaredFields()){
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
                destination.getMethod(setter, sField.getType());
                source.getMethod(getter);
                System.out.println(dBeanName + "." + setter + "(" + sBeanName + "." + getter + "());");
            } catch (Exception e){
//                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        createMap(CategoryBean.class, "contentBean", Category.class, "content");
    }

}
