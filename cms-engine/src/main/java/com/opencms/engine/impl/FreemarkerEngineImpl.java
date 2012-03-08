package com.opencms.engine.impl;

import com.opencms.core.db.bean.CategoryBean;
import com.opencms.core.db.bean.ContentBean;
import com.opencms.core.db.bean.SiteBean;
import com.opencms.engine.Engine;
import com.opencms.engine.TemplateModel;
import com.opencms.engine.model.CategoryModel;
import com.opencms.engine.model.Model;
import com.opencms.util.CmsUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午11:19
 * To change this template use File | Settings | File Templates.
 */
public abstract class FreemarkerEngineImpl<T extends Model> implements Engine<Model> {

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerEngineImpl.class);

    @Resource
    private CmsUtils cmsUtils;

    @Resource
    protected TemplateModel templateModel;

    public abstract void init();
    
    protected abstract Model calculate(Model model);

    public String render(Template template, Map model) throws TemplateException, IOException {
		Writer out = new StringWriter();
		template.process(model, out);
		return out.toString();
    }
    
    protected void create(String html, String path) throws IOException {
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
