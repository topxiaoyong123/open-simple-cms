package com.opencms.engine.publish.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;
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

    public Site map(SiteBean siteBean) {
        Site site = (Site)cmsUtils.getBeanMapperHelper().simpleMap(siteBean, Site.class);
        site.setUrl(getSiteURL(siteBean));
        return site;
    }

    public Category map(CategoryBean categoryBean) {
        Category category = (Category)cmsUtils.getBeanMapperHelper().simpleMap(categoryBean, Category.class);
        category.setUrl(getCategoryURL(categoryBean));
        return category;
    }

    public Category map(CategoryBean categoryBean, int page, int pageSize, int totalCount){
        Category category = map(categoryBean);
        category.setPage(new PageBean(pageSize, page, totalCount));
        return category;
    }

    public Content map(ContentBean contentBean) {
        return map(contentBean, true);
    }

    public Content map(ContentBean contentBean, boolean loadContent) {
        Content content = (Content)cmsUtils.getBeanMapperHelper().simpleMap(contentBean, Content.class);
        content.setUrl(getContentURL(contentBean));
        if(loadContent){
            content.setContent(contentBean.getContentDetail().getContent());
        }
        return content;
    }

    public List<Content> mapContents(List<ContentBean> contentBeans) {
        return mapContents(contentBeans, false);
    }

    public List<Content> mapContents(List<ContentBean> contentBeans, boolean loadContent) {
        List<Content> list = new ArrayList<Content>();
        for(ContentBean contentBean : contentBeans){
            list.add(map(contentBean, loadContent));
        }
        return list;
    }

    public String getSiteURL(SiteBean siteBean) {
        if(siteBean.getUrl() != null && !"".equals(siteBean.getUrl())){
            return siteBean.getUrl();
        }
        return getContextPath() + "/" + siteBean.getName() + "/index.html";
    }

    public String getCategoryURL(CategoryBean categoryBean) {
        return getCategoryURL(categoryBean, 1);
    }

    public String getCategoryURL(CategoryBean categoryBean, int page) {
        if(categoryBean.getUrl() != null && !"".equals(categoryBean.getUrl())){
            return categoryBean.getUrl();
        }
        if(categoryBean.isStaticCategory()) {
            return getContextPath() + "/" + PathUtils.getCategoryRelativePath(categoryBean, page);
        }
        return getCategoryURL(categoryBean, page, PageBean.DEFAULT_SIZE);
    }

    public String getCategoryURL(CategoryBean categoryBean, int page, int pageSize) {
        return getContextPath() + "/category" + "/" + page + "/" + pageSize + "/" + categoryBean.getId() + ".html";
    }

    public String getContentURL(ContentBean contentBean) {
        if(contentBean.getUrl() != null && !"".equals(contentBean.getUrl())){
            return contentBean.getUrl();
        }
        return getContextPath() + "/" + Constants.PUBLISH_OUTPUT_PATH + "/" + DateUtil.getYear(contentBean.getCreationDate()) + "/" + DateUtil.getMonth(contentBean.getCreationDate()) + "/" + contentBean.getId() + Constants.DEFAULT_URL_EXTEND;
    }

    private String getContextPath(){
        if(ContextThreadLocal.getRequest() != null){
            return ContextThreadLocal.getRequest().getContextPath();
        } else{
            return "{contextPath}";
        }
    }
}
