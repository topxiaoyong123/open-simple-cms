package com.opencms.wcm.server.message;

import com.opencms.wcm.server.ContextThreadLocal;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-9
 * Time: 20:18:27
 * To change this template use File | Settings | File Templates.
 */
public class LocaleHelper {

    public static Locale getLocale(){
        try{
            Locale locale = (Locale)ContextThreadLocal.getRequest().getSession().getAttribute("locale");
            if(locale != null){
                return locale;
            } else{
                return new Locale("");
            }
        } catch(Exception e){
            e.printStackTrace();
            return new Locale("");
        }
    }

}
