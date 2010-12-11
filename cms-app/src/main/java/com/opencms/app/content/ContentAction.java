package com.opencms.app.content;

import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 12:45:19
 * To change this template use File | Settings | File Templates.
 */
public class ContentAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(ContentAction.class);

    private int year;

    private int month;

    private String id;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String view(){
        logger.debug("pares:[{}][{}][{}]", new Object[]{year, month, id});
        return SUCCESS;
    }

}
