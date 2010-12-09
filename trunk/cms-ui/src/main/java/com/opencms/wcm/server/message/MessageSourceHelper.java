package com.opencms.wcm.server.message;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-9
 * Time: 21:37:55
 * To change this template use File | Settings | File Templates.
 */
@Component("messageSourceHelper")
public class MessageSourceHelper {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String msg = messageSource.getMessage(code, args, defaultMessage, locale);
        return msg != null ? msg.trim() : msg;
    }

    public String getMessage(String code, Object[] args, Locale locale) {
        String msg = messageSource.getMessage(code, args, "", locale);
        return msg != null ? msg.trim() : msg;
    }

    public String getMessage(String code, Locale locale) {
        String msg = messageSource.getMessage(code, null, "", locale);
        return msg != null ? msg.trim() : msg;
    }

    public String getMessage(String code){
        String msg = messageSource.getMessage(code, null, "", LocaleHelper.getLocale());
        return msg != null ? msg.trim() : msg;
    }

    public String getMessage(String code, Object[] args) {
        String msg = messageSource.getMessage(code, args, "", LocaleHelper.getLocale());
        return msg != null ? msg.trim() : msg;
    }

}
