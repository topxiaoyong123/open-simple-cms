package com.opencms.engine;

import java.util.List;

import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.model.ContentModel;
import com.opencms.engine.model.Menu;
import com.opencms.engine.util.PathUtils;

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
    
    public PathUtils getPathUtils();

    public Menu getSiteMenu(String siteId);

    public Menu getCategoryMenu(String categoryId);

    public List<ContentModel> getContents(String categoryId, int firstResult, int maxResults);

}
