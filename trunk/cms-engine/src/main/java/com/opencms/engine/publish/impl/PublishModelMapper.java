package com.opencms.engine.publish.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
@Component("publishMapper")
public class PublishModelMapper implements ModelMapper {
    @Override
    public Site map(SiteBean siteBean) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Category map(CategoryBean categoryBean) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Content map(ContentBean contentBean) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}