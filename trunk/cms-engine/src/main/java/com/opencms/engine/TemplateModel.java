package com.opencms.engine;

import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;

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

    public void setSite(Site site);

    public void setCategory(Category category);

    public void setContent(Content content);

    public void setEngineUtil(EngineUtil engineUtil);

}
