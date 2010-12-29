package com.opencms.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-29
 * Time: 上午11:50
 * To change this template use File | Settings | File Templates.
 */
public class DateUtil {

    static SimpleDateFormat f1 = new SimpleDateFormat("yyyy");

    static SimpleDateFormat f2 = new SimpleDateFormat("MM");

    static SimpleDateFormat f3 = new SimpleDateFormat("dd");

    public static String getYear(Date date){
        return f1.format(date);
    }

    public static String getMonth(Date date){
        return f2.format(date);
    }

    public static String getDate(Date date){
        return f3.format(date);
    }

}
