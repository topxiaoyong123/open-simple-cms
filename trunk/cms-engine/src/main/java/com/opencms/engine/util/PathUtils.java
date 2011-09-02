package com.opencms.engine.util;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.util.common.Constants;
import com.opencms.util.common.DateUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 11-9-2
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
public class PathUtils {

    public static String getSitePath(SiteBean siteBean) {
        String basePath = PathUtils.class.getResource("/").getPath().replace("WEB-INF/classes", "") + siteBean.getName();
        File folder = new File(basePath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        return basePath + "/index" + Constants.DEFAULT_URL_EXTEND;
    }

    public static String getCategoryPath(CategoryBean categoryBean, int page) {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(categoryBean.getName());
        CategoryBean c = categoryBean.getParent();
        while (c != null) {
            paths.add(c.getName());
            c = c.getParent();
        }
        StringBuffer s = new StringBuffer(PathUtils.class.getResource("/").getPath().replace("WEB-INF/classes", ""));
        for(int i = paths.size() - 1; i >= 0; i --) {
            s.append(paths.get(i)).append("/");
        }
        File folder = new File(s.toString());
        if(!folder.exists()){
            folder.mkdirs();
        }
        s.append("index").append("_").append(page).append(Constants.DEFAULT_URL_EXTEND);
        return s.toString();
    }

    public static String getCategoryRelativePath(CategoryBean categoryBean, int page) {
        ArrayList<String> paths = new ArrayList<String>();
        paths.add(categoryBean.getName());
        CategoryBean c = categoryBean.getParent();
        while (c != null) {
            paths.add(c.getName());
            c = c.getParent();
        }
        StringBuffer s = new StringBuffer();
        for(int i = paths.size() - 1; i >= 0; i --) {
            s.append(paths.get(i)).append("/");
        }
        s.append("index").append("_").append(page).append(Constants.DEFAULT_URL_EXTEND);
        return s.toString();
    }

    public static String getContentPath(ContentBean contentBean) {
        String basePath = PathUtils.class.getResource("/").getPath().replace("WEB-INF/classes", Constants.PUBLISH_OUTPUT_PATH) + "/" + DateUtil.getYear(contentBean.getCreationDate()) + "/" + DateUtil.getMonth(contentBean.getCreationDate());
        File folder = new File(basePath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        return basePath + "/" + contentBean.getId() + Constants.DEFAULT_URL_EXTEND;
    }

}
