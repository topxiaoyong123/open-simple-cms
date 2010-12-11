package com.opencms.app.category;

import com.opensymphony.xwork2.ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-11
 * Time: 13:28:18
 * To change this template use File | Settings | File Templates.
 */
public class CategoryAction extends ActionSupport {

    private static Logger logger = LoggerFactory.getLogger(CategoryAction.class);

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String view(){
        logger.debug("pares:[{}]", id);
        return SUCCESS;
    }

}
