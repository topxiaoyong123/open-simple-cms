package com.opencms.engine.util;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.util.ContextThreadLocal;
import com.opencms.util.common.Constants;
import com.opencms.util.common.DateUtil;

import java.io.File;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-9-2
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PathUtils {

    public String getSitePath(SiteBean siteBean) {
        String basePath = getBasePath() + siteBean.getName();
        File folder = new File(basePath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        return basePath + "/index" + Constants.DEFAULT_URL_EXTEND;
    }

    public String getCategoryPath(CategoryBean categoryBean, int page) {
    	StringBuffer s = new StringBuffer(getBasePath());
    	s.append(getCategoryRelativePath(categoryBean, page));
        File folder = new File(s.toString());
        if(!folder.exists()){
            folder.mkdirs();
        }
        if(page == 1) {
        	s.append("index").append(Constants.DEFAULT_URL_EXTEND);
        } else {
        	s.append("index").append(page).append(Constants.DEFAULT_URL_EXTEND);
        }
        return s.toString();
    }

    public String getCategoryRelativePath(CategoryBean categoryBean, int page) {
    	ArrayList<String> paths = new ArrayList<String>();
        paths.add(categoryBean.getName());
        CategoryBean tmp = categoryBean;
        CategoryBean c = categoryBean.getParent();
        while (c != null) {
        	tmp = c;
            paths.add(c.getName());
            c = c.getParent();
        }
        paths.add(tmp.getSite().getName());
        StringBuffer s = new StringBuffer("category/");
        for(int i = paths.size() - 1; i >= 0; i --) {
            s.append(paths.get(i)).append("/");
        }
        return s.toString();
    }

    public String getContentPath(ContentBean contentBean) {
    	StringBuffer s = new StringBuffer(getBasePath()).append(Constants.PUBLISH_OUTPUT_PATH);
    	s.append("/").append(DateUtil.getYear(contentBean.getCreationDate()))
    		.append("/").append(DateUtil.getMonth(contentBean.getCreationDate()))
    		.append("/").append(DateUtil.getDate(contentBean.getCreationDate()));
        File folder = new File(s.toString());
        if(!folder.exists()){
            folder.mkdirs();
        }
        return s.append("/").append(contentBean.getId()).append(Constants.DEFAULT_URL_EXTEND).toString();
    }
    
    public static String getBasePath() {
    	return PathUtils.class.getResource("/").getPath().replace("WEB-INF/classes", "");
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
    	String url = getContextPath() + "/" + getCategoryRelativePath(categoryBean, page) + "index";
    	if(page != 1) {
    		url += page; 
    	}
        if(categoryBean.isStaticCategory()) {
            url += Constants.DEFAULT_URL_EXTEND;
        } else {
        	url += Constants.DYNAMIC_URL_EXTEND;
        }
        return url;
    }

    public String getCategoryURL(CategoryBean categoryBean, int page, int pageSize) {
    	return getCategoryURL(categoryBean, page);
    }

    public String getContentURL(ContentBean contentBean) {
        if(contentBean.getUrl() != null && !"".equals(contentBean.getUrl())){
            return contentBean.getUrl();
        }
        StringBuffer s = new StringBuffer();
        s.append(getContextPath()).append("/").append(Constants.PUBLISH_OUTPUT_PATH).append("/")
        	.append(DateUtil.getYear(contentBean.getCreationDate())).append("/")
        	.append(DateUtil.getMonth(contentBean.getCreationDate())).append("/")
        	.append(DateUtil.getDate(contentBean.getCreationDate())).append("/")
        	.append(contentBean.getId());
        if(contentBean.getCategory().isStaticContent()) {
        	s.append(Constants.DEFAULT_URL_EXTEND);
        } else {
        	s.append(Constants.DYNAMIC_URL_EXTEND);
        }
        return s.toString();
    }

    private String getContextPath(){
        if(ContextThreadLocal.getRequest() != null){
            return ContextThreadLocal.getRequest().getContextPath();
        } else{
            return "{contextPath}";
        }
    }

}
