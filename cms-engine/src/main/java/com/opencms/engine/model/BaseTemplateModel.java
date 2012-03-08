package com.opencms.engine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-14
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
public class BaseTemplateModel {
	
	protected List<Model> models = Collections.synchronizedList(new ArrayList<Model>());

	public void addModel(Model model) {
		models.add(model);
	}
}
