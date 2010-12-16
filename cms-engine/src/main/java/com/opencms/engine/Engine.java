package com.opencms.engine;

import com.opencms.engine.model.EngineInfo;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午9:55
 * To change this template use File | Settings | File Templates.
 */
public interface Engine {

    public String engine(EngineInfo info) throws IOException, TemplateException;

    public String engineSite(EngineInfo info) throws IOException, TemplateException;

    public String engineCategory(EngineInfo info);

    public String engineContent(EngineInfo info);

}
