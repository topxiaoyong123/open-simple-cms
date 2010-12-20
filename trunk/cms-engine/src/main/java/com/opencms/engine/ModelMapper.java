package com.opencms.engine;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.EngineInfo;
import com.opencms.engine.model.Site;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
public interface ModelMapper {

    public Object map(EngineInfo info);

    public Site map(SiteBean siteBean);

    public Category map(CategoryBean categoryBean);

    public Content map(ContentBean contentBean);

    public List<Content> mapContents(List<ContentBean> contentBeans);

    public String getSiteURL(SiteBean siteBean);

    public String getCategoryURL(CategoryBean categoryBean);

    public String getCategoryURL(CategoryBean categoryBean, int page, int pageSize);

    public String getContentURL(ContentBean contentBean);

}
