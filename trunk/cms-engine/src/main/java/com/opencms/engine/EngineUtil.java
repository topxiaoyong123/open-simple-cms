package com.opencms.engine;

import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Menu;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-20
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
public interface EngineUtil {

    public CmsManager getCmsManager();

    public ModelMapper getMapper();

    public Menu getSiteMenu(String siteId);

    public Menu getCategoryMenu(String categoryId);

    public List<Content> getContents(String categoryId, int firstResult, int maxResults);

}
