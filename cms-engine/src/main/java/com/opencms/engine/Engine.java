package com.opencms.engine;

import java.io.IOException;

import com.opencms.engine.model.Model;

import freemarker.template.TemplateException;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-13
 * Time: 下午9:55
 * To change this template use File | Settings | File Templates.
 */
public interface Engine<T extends Model> {
	
	public String engine(T model) throws IOException, TemplateException;
	
}
