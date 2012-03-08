package com.opencms.engine.publish.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.CategoryModel;
import com.opencms.engine.model.ContentModel;
import com.opencms.engine.model.SiteModel;
import com.opencms.engine.util.PathUtils;
import com.opencms.util.CmsUtils;
import com.opencms.util.ContextThreadLocal;
import com.opencms.util.common.Constants;
import com.opencms.util.common.DateUtil;
import com.opencms.util.common.page.PageBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
@Component
@Transactional(readOnly = true)
public class PublishModelMapper implements ModelMapper {

    @Resource
    private CmsUtils cmsUtils;
    
    @Resource
    private PathUtils pathUtils;

    public SiteModel map(SiteBean siteBean, SiteModel site) {
        cmsUtils.getBeanMapperHelper().simpleMap(siteBean, site);
        site.setUrl(pathUtils.getSiteURL(siteBean));
        return site;
    }

    public CategoryModel map(CategoryBean categoryBean, CategoryModel category) {
        cmsUtils.getBeanMapperHelper().simpleMap(categoryBean, category);
        category.setPage(new PageBean(category.getEngineInfo().getPageSize(), category.getEngineInfo().getPage(), (int)category.getEngineInfo().getTotalCount()));
        category.setUrl(pathUtils.getCategoryURL(categoryBean));
        return category;
    }

    public ContentModel map(ContentBean contentBean, ContentModel content) {
        return map(contentBean, content, true);
    }

    public ContentModel map(ContentBean contentBean, ContentModel content, boolean loadContent) {
        cmsUtils.getBeanMapperHelper().simpleMap(contentBean, content);
        content.setUrl(pathUtils.getContentURL(contentBean));
        if(loadContent){
            content.setContent(contentBean.getContentDetail().getContent());
        }
        return content;
    }

    public List<ContentModel> mapContents(List<ContentBean> contentBeans) {
        return mapContents(contentBeans, false);
    }

    public List<ContentModel> mapContents(List<ContentBean> contentBeans, boolean loadContent) {
        List<ContentModel> list = new ArrayList<ContentModel>();
        for(ContentBean contentBean : contentBeans){
            list.add(map(contentBean, new ContentModel(contentBean), loadContent));
        }
        return list;
    }

}
