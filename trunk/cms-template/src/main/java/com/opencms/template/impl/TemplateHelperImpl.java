package com.opencms.template.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.template.TemplateHelper;
import com.opencms.template.bean.CmsTemplateBean;
import com.opencms.util.CmsUtils;
import com.opencms.util.common.Constants;
import com.opencms.util.common.FileFilterImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午10:02
 * To change this template use File | Settings | File Templates.
 */
@Component
public class TemplateHelperImpl implements TemplateHelper {

    private static Logger logger = LoggerFactory.getLogger(TemplateHelperImpl.class);

    @Resource
    private CmsUtils cmsUtils;

    private Configuration freeMarkerConfiguration;

    @PostConstruct
    public void initFreemarkerEngine(){
        freeMarkerConfiguration = new Configuration();
		freeMarkerConfiguration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
		String templatePath = "classpath:/template/";
        try{
            templatePath = cmsUtils.getResourceHelper().getCmsResource().getTemplatePath();
            logger.debug("初始化FreeMarker模板目录:{}", templatePath);
            freeMarkerConfiguration.setDirectoryForTemplateLoading(new File(templatePath));
			freeMarkerConfiguration.setDefaultEncoding(cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding());
        } catch (IOException e) {
			logger.error("初始化FreeMarker模板目录:" + templatePath
					+ " 异常。");
			e.printStackTrace();
		}
    }

    public Template getTemplate(SiteBean siteBean) throws IOException {
        String basePath = getBasePath(siteBean);
        String indexTemplate = siteBean.getIndexTemplate() == null || "".equals(siteBean.getIndexTemplate()) ? Constants.DEFAULT_INDEX_TEMPLATE : siteBean.getIndexTemplate();
        try {
            return freeMarkerConfiguration.getTemplate(basePath + "/" + indexTemplate);
        } catch (IOException e) {
            logger.warn("模板找不到，采用默认模板", e);
            return freeMarkerConfiguration.getTemplate(basePath + "/" + Constants.DEFAULT_INDEX_TEMPLATE);
        }
    }

    public Template getTemplate(CategoryBean categoryBean) throws IOException {
        String basePath = getBasePath(categoryBean.getSite());
        String categoryTemplate = categoryBean.getTemplate() == null || "".equals(categoryBean.getTemplate()) ? Constants.DEFAULT_CATEGORY_TEMPLATE : categoryBean.getTemplate();
        try {
            return freeMarkerConfiguration.getTemplate(basePath + "/" + categoryTemplate);
        } catch (IOException e) {
            logger.warn("模板找不到，采用默认模板", e);
            return freeMarkerConfiguration.getTemplate(basePath + "/" + Constants.DEFAULT_CATEGORY_TEMPLATE);
        }
    }

    public Template getTemplate(ContentBean contentBean) throws IOException {
        String basePath = getBasePath(contentBean.getCategory().getSite());
        String contentTemplate = contentBean.getTemplate() == null || "".equals(contentBean.getTemplate()) ? Constants.DEFAULT_CONTENT_TEMPLATE : contentBean.getTemplate();
        try {
            return freeMarkerConfiguration.getTemplate(basePath + "/" + contentTemplate);
        } catch (IOException e) {
            logger.warn("模板找不到，采用默认模板", e);
            return freeMarkerConfiguration.getTemplate(basePath + "/" + Constants.DEFAULT_CONTENT_TEMPLATE);
        }
    }

    private String getBasePath(SiteBean siteBean){
        String template = siteBean.getTemplate();
        String basePath = null;
        if(template != null && !"".equals(template)){
            basePath = template;
        } else{
            basePath = Constants.DEFAULT_BASE_TEMPLATE_PATH;
        }
        return basePath;
    }

    public List<CmsTemplateBean> getTemplateBeans(String baseTemplate, String type) {
        if("0".equals(type)){
            return getSiteTemplates();
        } else if("1".equals(type)){
            return getIndexTemplates(baseTemplate);
        } else if("2".equals(type)){
            return getCategoryTemplates(baseTemplate);
        } else if("3".equals(type)){
            return getContentTemplates(baseTemplate);
        }
        return new ArrayList<CmsTemplateBean>();
    }

    private List<CmsTemplateBean> getSiteTemplates(){
        logger.debug("取得所有站点模板");
        List<CmsTemplateBean> list = new ArrayList<CmsTemplateBean>();
        String templatePath = cmsUtils.getResourceHelper().getCmsResource().getTemplatePath();
        File base = new File(templatePath);
        FileFilter fileFilter = new FileFilterImpl(false, true, Constants.BASE_TEMPLATE_PATH_FILTER_START_WITH, null);
        File[] subFiles = base.listFiles(fileFilter);
        for(File subFile : subFiles){
            CmsTemplateBean cmsTemplateBean = new CmsTemplateBean();
            cmsTemplateBean.setName(subFile.getName());
            cmsTemplateBean.setShortcut(getTemplateUrl(subFile.getName()) + Constants.BASE_TEMPLATE_SHORTCUT);
            cmsTemplateBean.setType("0");
            try {
                Properties prop = new Properties();
                prop.load(new BufferedInputStream(new FileInputStream(new File(subFile, Constants.BASE_TEMPLATE_PROP_FILE))));
                cmsTemplateBean.setTitle(new String(prop.getProperty("template.title", subFile.getName()).getBytes("ISO8859-1"), cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding()));
                cmsTemplateBean.setDescription(new String(prop.getProperty("template.description", subFile.getName()).getBytes("ISO8859-1"), cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(cmsTemplateBean);
        }
        return list;
    }

    private List<CmsTemplateBean> getIndexTemplates(String baseTemplate){
        logger.debug("取得站点[{}]下所有首页模板", baseTemplate);
        List<CmsTemplateBean> list = new ArrayList<CmsTemplateBean>();
        String templatePath = cmsUtils.getResourceHelper().getCmsResource().getTemplatePath() + "/" + baseTemplate;
        File base = new File(templatePath);
        FileFilter fileFilter = new FileFilterImpl(true, false, Constants.INDEX_TEMPLATE_FILTER_START_WITH, Constants.TEMPLATE_FILTER_END_WITH);
        File[] subFiles = base.listFiles(fileFilter);
        for(File subFile : subFiles){
            CmsTemplateBean cmsTemplateBean = new CmsTemplateBean();
            cmsTemplateBean.setName(subFile.getName());
            cmsTemplateBean.setShortcut(getTemplateUrl(baseTemplate) + subFile.getName().replace(Constants.TEMPLATE_FILTER_END_WITH, Constants.TEMPLATE_SHORTCUT_TYPE));
            cmsTemplateBean.setType("1");
            try {
                Properties prop = new Properties();
                prop.load(new BufferedInputStream(new FileInputStream(new File(templatePath, subFile.getName().replace(Constants.TEMPLATE_FILTER_END_WITH, Constants.TEMPLATE_PROP_TYPE)))));
                cmsTemplateBean.setTitle(new String(prop.getProperty("template.title", subFile.getName()).getBytes("ISO8859-1"), cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding()));
                cmsTemplateBean.setDescription(new String(prop.getProperty("template.description", subFile.getName()).getBytes("ISO8859-1"), cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(cmsTemplateBean);
        }
        return list;
    }

    private List<CmsTemplateBean> getCategoryTemplates(String baseTemplate){
        logger.debug("取得站点[{}]下所有栏目模板", baseTemplate);
        List<CmsTemplateBean> list = new ArrayList<CmsTemplateBean>();
        String templatePath = cmsUtils.getResourceHelper().getCmsResource().getTemplatePath() + "/" + baseTemplate;
        File base = new File(templatePath);
        FileFilter fileFilter = new FileFilterImpl(true, false, Constants.CATEGORY_TEMPLATE_FILTER_START_WITH, Constants.TEMPLATE_FILTER_END_WITH);
        File[] subFiles = base.listFiles(fileFilter);
        for(File subFile : subFiles){
            CmsTemplateBean cmsTemplateBean = new CmsTemplateBean();
            cmsTemplateBean.setName(subFile.getName());
            cmsTemplateBean.setShortcut(getTemplateUrl(baseTemplate) + subFile.getName().replace(Constants.TEMPLATE_FILTER_END_WITH, Constants.TEMPLATE_SHORTCUT_TYPE));
            cmsTemplateBean.setType("2");
            try {
                Properties prop = new Properties();
                prop.load(new BufferedInputStream(new FileInputStream(new File(templatePath, subFile.getName().replace(Constants.TEMPLATE_FILTER_END_WITH, Constants.TEMPLATE_PROP_TYPE)))));
                cmsTemplateBean.setTitle(new String(prop.getProperty("template.title", subFile.getName()).getBytes("ISO8859-1"), cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding()));
                cmsTemplateBean.setDescription(new String(prop.getProperty("template.description", subFile.getName()).getBytes("ISO8859-1"), cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(cmsTemplateBean);
        }
        return list;
    }

    private List<CmsTemplateBean> getContentTemplates(String baseTemplate){
        logger.debug("取得站点[{}]下所有文章模板", baseTemplate);
        List<CmsTemplateBean> list = new ArrayList<CmsTemplateBean>();
        String templatePath = cmsUtils.getResourceHelper().getCmsResource().getTemplatePath() + "/" + baseTemplate;
        File base = new File(templatePath);
        FileFilter fileFilter = new FileFilterImpl(true, false, Constants.CONTENT_TEMPLATE_FILTER_START_WITH, Constants.TEMPLATE_FILTER_END_WITH);
        File[] subFiles = base.listFiles(fileFilter);
        for(File subFile : subFiles){
            CmsTemplateBean cmsTemplateBean = new CmsTemplateBean();
            cmsTemplateBean.setName(subFile.getName());
            cmsTemplateBean.setShortcut(getTemplateUrl(baseTemplate) + subFile.getName().replace(Constants.TEMPLATE_FILTER_END_WITH, Constants.TEMPLATE_SHORTCUT_TYPE));
            cmsTemplateBean.setType("3");
            try {
                Properties prop = new Properties();
                prop.load(new BufferedInputStream(new FileInputStream(new File(templatePath, subFile.getName().replace(Constants.TEMPLATE_FILTER_END_WITH, Constants.TEMPLATE_PROP_TYPE)))));
                cmsTemplateBean.setTitle(new String(prop.getProperty("template.title", subFile.getName()).getBytes("ISO8859-1"), cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding()));
                cmsTemplateBean.setDescription(new String(prop.getProperty("template.description", subFile.getName()).getBytes("ISO8859-1"), cmsUtils.getResourceHelper().getCmsResource().getDefaultEncoding()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(cmsTemplateBean);
        }
        return list;
    }

    private String getTemplateUrl(String baseTemplate){
        return cmsUtils.getResourceHelper().getCmsResource().getOutputUrl() + "/" + Constants.TEMPLATE_PARENT_PATH + "/" + baseTemplate + "/";
    }
}
