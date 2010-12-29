package com.opencms.util.resources;

import com.opencms.util.ContextThreadLocal;
import com.opencms.util.common.Constants;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 2010-12-12
 * Time: 15:30:38
 * To change this template use File | Settings | File Templates.
 */
public class CmsResource {

    private String outputUrl;

    public String getOutputUrl() {
        return ContextThreadLocal.getRequest().getContextPath();
    }

    public void setOutputUrl(String outputUrl) {
        this.outputUrl = outputUrl;
    }

    private String templatePath;

    private String outputPath;

    private String defaultEncoding;

    private String inputEncoding;

    private String outputEncoding;

    public String getTemplatePath() {
        return getClass().getResource("/").getPath().replace("WEB-INF/classes", Constants.TEMPLATE_PARENT_PATH);
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getOutputPath() {
        return getClass().getResource("/").getPath().replace("WEB-INF/classes", Constants.PUBLISH_OUTPUT_PATH);
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public String getInputEncoding() {
        return inputEncoding;
    }

    public void setInputEncoding(String inputEncoding) {
        this.inputEncoding = inputEncoding;
    }

    public String getOutputEncoding() {
        return outputEncoding;
    }

    public void setOutputEncoding(String outputEncoding) {
        this.outputEncoding = outputEncoding;
    }
}
