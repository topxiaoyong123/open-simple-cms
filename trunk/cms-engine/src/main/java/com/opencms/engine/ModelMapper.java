package com.opencms.engine;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.model.CategoryModel;
import com.opencms.engine.model.ContentModel;
import com.opencms.engine.model.SiteModel;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
public interface ModelMapper {

    public SiteModel map(SiteBean siteBean, SiteModel site);

    public CategoryModel map(CategoryBean categoryBean, CategoryModel category);

    public ContentModel map(ContentBean contentBean, ContentModel content);

    public List<ContentModel> mapContents(List<ContentBean> contentBeans);

    public List<ContentModel> mapContents(List<ContentBean> contentBeans, boolean loadContent);

}
