package com.opencms.engine.model;

import com.opencms.core.db.bean.SimpleContentBean;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:15
 * To change this template use File | Settings | File Templates.
 */
public class Content extends SimpleContentBean implements Model {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
