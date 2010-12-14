package com.opencms.template;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public interface TemplateModel {

    public Map getModel();

    public void clean();

}
