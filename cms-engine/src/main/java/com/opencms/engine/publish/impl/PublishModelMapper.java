package com.opencms.engine.publish.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;
import com.opencms.util.CmsUtils;
import com.opencms.util.ContextThreadLocal;
import com.opencms.util.common.DateUtil;
import com.opencms.util.common.page.PageBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午8:01
 * To change this template use File | Settings | File Templates.
 */
@Component
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

    public Category map(CategoryBean categoryBean, int page, int pagesize, int totalcount){
        Category category = map(categoryBean);
        category.setPage(new PageBean(pagesize, page, totalcount));
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
        return ContextThreadLocal.getRequest().getContextPath() + "/site/" + siteBean.getName() + "/index.html";
    }

    public String getCategoryURL(CategoryBean categoryBean) {
        if(categoryBean.getUrl() != null && !"".equals(categoryBean.getUrl())){
            return categoryBean.getUrl();
        }
        return ContextThreadLocal.getRequest().getContextPath() + "/category" + "/1/" + PageBean.DEFAULT_SIZE + "/" + categoryBean.getId() + ".html";
    }

    public String getCategoryURL(CategoryBean categoryBean, int page, int pageSize) {
        return ContextThreadLocal.getRequest().getContextPath() + "/category" + "/" + page + "/" + pageSize + "/" + categoryBean.getId() + ".html";
    }

    public String getContentURL(ContentBean contentBean) {
        if(contentBean.getUrl() != null && !"".equals(contentBean.getUrl())){
            return contentBean.getUrl();
        }
        return ContextThreadLocal.getRequest().getContextPath() + "/content/" + DateUtil.getYear(contentBean.getCreationDate()) + "/" + DateUtil.getMonth(contentBean.getCreationDate()) + "/" + contentBean.getId() + ".html";
    }

    public String getContentPath(ContentBean contentBean) {
        String basePath = cmsUtils.getResourceHelper().getCmsResource().getOutputPath() + "/" + DateUtil.getYear(contentBean.getCreationDate()) + "/" + DateUtil.getMonth(contentBean.getCreationDate());
        File folder = new File(basePath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        return basePath + "/" + contentBean.getId() + ".html";
    }
}
