package com.opencms.engine;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Menu;
import com.opencms.engine.model.Site;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-20
 * Time: 上午11:01
 * To change this template use File | Settings | File Templates.
 */
public interface EngineUtil {

    public Menu getSiteMenu(String siteId);

    public Menu getCategoryMenu(String categoryId);

    public List<Content> getContents(String categoryId, int firstResult, int maxResults);

    public String getSiteURL(SiteBean siteBean);

    public String getCategoryURL(CategoryBean categoryBean);

    public String getCategoryURL(CategoryBean categoryBean, int page, int pageSize);

    public String getContentURL(ContentBean contentBean);

}
