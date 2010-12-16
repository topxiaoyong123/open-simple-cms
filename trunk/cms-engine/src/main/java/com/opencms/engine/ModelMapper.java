package com.opencms.engine;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.EngineInfo;
import com.opencms.engine.model.Site;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
public interface ModelMapper {

    public Site map(SiteBean siteBean);

    public Category map(CategoryBean categoryBean);

    public Object map(EngineInfo info);

    public Content map(ContentBean contentBean);

    public String getSiteURL(SiteBean siteBean);

    public String getCategoryURL(CategoryBean categoryBean);

    public String getContentURL(ContentBean contentBean);

}
