package com.opencms.engine.publish.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.core.db.bean.field.ContentField;
import com.opencms.core.db.service.CmsManager;
import com.opencms.engine.Engine;
import com.opencms.engine.EngineUtil;
import com.opencms.engine.ModelMapper;
import com.opencms.engine.impl.FreemarkerEngineImpl;
import com.opencms.engine.model.Category;
import com.opencms.engine.model.Content;
import com.opencms.engine.model.Site;
import com.opencms.engine.util.PathUtils;
import com.opencms.template.TemplateHelper;
import com.opencms.util.CmsUtils;
import com.opencms.util.common.page.PageBean;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional(readOnly = true)
public class PublishEngineImpl extends FreemarkerEngineImpl implements Engine {

    private static Logger logger = LoggerFactory.getLogger(PublishEngineImpl.class);

    @Resource
    private ModelMapper mapper;

    @Resource
    private TemplateHelper templateHelper;

    @Resource
    private EngineUtil engineUtil;

    @Resource
    private CmsManager cmsManager;

    @Resource
    private CmsUtils cmsUtils;

    @PostConstruct
    public void init() {
        templateModel.setEngineUtil(engineUtil);
    }

    public String engineSite(SiteBean siteBean) throws IOException, TemplateException {
        templateModel.clean();
        Site site = mapper.map(siteBean);
        templateModel.setSite(site);
        Template template = templateHelper.getTemplate(siteBean);
        String html = render(template, templateModel.getModel());
        create(html, PathUtils.getSitePath(siteBean));
        return html;
    }

    public String engineCategory(CategoryBean categoryBean) throws IOException, TemplateException {
        return engineCategory(categoryBean, 1, PageBean.DEFAULT_SIZE);
    }

    public String engineCategory(CategoryBean categoryBean, boolean create) throws IOException, TemplateException {
        if(categoryBean.isStaticCategory() && create) {
            int contentsCount = (int) cmsManager.getContentService().getCountByCategoryId(categoryBean.getId(), ContentField._STATE_PUBLISHED);
            int pageSize = PageBean.DEFAULT_SIZE;
            int pageCount = contentsCount / pageSize + (contentsCount % pageSize == 0 ? 0 : 1);
            pageCount = pageCount == 0 ? 1 : pageCount;
            for (int page = 1; page <= pageCount; page ++) {
                String html = engineCategory(categoryBean, page, pageSize);
                create(html, PathUtils.getCategoryPath(categoryBean, page));
            }
        }
        return null;
    }

    public String engineCategory(CategoryBean categoryBean, int page, int pageSize) throws IOException, TemplateException {
        templateModel.clean();
        int contentsCount = (int) cmsManager.getContentService().getCountByCategoryId(categoryBean.getId(), ContentField._STATE_PUBLISHED);
        Category category = mapper.map(categoryBean, page, pageSize, contentsCount);
        Site site = mapper.map(categoryBean.getSite());
        templateModel.setCategory(category);
        templateModel.setSite(site);
        Template template = templateHelper.getTemplate(categoryBean);
        return render(template, templateModel.getModel());
    }

    public String engineContent(ContentBean contentBean, boolean create) throws IOException, TemplateException {
        templateModel.clean();
        Content content = mapper.map(contentBean);
        Category category = mapper.map(contentBean.getCategory());
        Site site = mapper.map(contentBean.getCategory().getSite());
        templateModel.setContent(content);
        templateModel.setCategory(category);
        templateModel.setSite(site);
        Template template = templateHelper.getTemplate(contentBean);
        String html = render(template, templateModel.getModel());
        if (create)
            create(html, PathUtils.getContentPath(contentBean));
        return html;
    }

    private void create(String html, String path) throws IOException {
        logger.debug("html内容为:{}", html);
        logger.debug("生成文件为:{}", path);
        File f = new File(path);
        //对文件加锁
        FileOutputStream outputStream = new FileOutputStream(f);
        FileChannel channel = outputStream.getChannel();
        FileLock lock = channel.tryLock(); //加锁
        Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, cmsUtils.getResourceHelper().getCmsResource().getOutputEncoding()));
        out.append(html);
        out.flush();
        lock.release();//解锁
        outputStream.close();
        out.close();
        logger.info("成功生成文件：{}", path);
    }

}
