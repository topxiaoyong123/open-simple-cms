package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;
import com.opencms.util.mapper.BeanMapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:00
 * To change this template use File | Settings | File Templates.
 */
@Component("cmsMapper")
public class CmsModelMapper implements ModelMapper {

    @Resource(name = "beanMapperHelper")
    private BeanMapperHelper beanMapperHelper;

    @Override
    public Site map(SiteBean siteBean) {
        Site site = (Site)beanMapperHelper.map(siteBean, Site.class);
        return site;
    }

    @Override
    public Category map(CategoryBean categoryBean) {
        Category category = (Category)beanMapperHelper.map(categoryBean, Category.class);
        return category;
    }

    @Override
    public Content map(ContentBean contentBean) {
        Content content = (Content)beanMapperHelper.map(contentBean, Content.class);
        return content;
    }
}
