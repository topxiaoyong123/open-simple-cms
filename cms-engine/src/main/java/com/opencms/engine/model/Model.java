package com.opencms.engine.model;

/**
 * Created by IntelliJ IDEA.
 * User: Lij
 * Date: 10-12-16
 * Time: 下午4:29
 * To change this template use File | Settings | File Templates.
 */
public interface Model {
	
	public String getModelName();
	
	public Object getObject();
	
	public Long getId();
	
	public String getName();
	
}
