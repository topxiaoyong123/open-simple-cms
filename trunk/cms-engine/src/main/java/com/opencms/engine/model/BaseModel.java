package com.opencms.engine.model;

public abstract class BaseModel implements Model {

	public BaseModel() {
	}
	
	public String getModelName(){
		String className = getClass().getSimpleName();
		String modelName = className.substring(0, 1).toLowerCase() + className.substring(1);
		return modelName;
	}
	
	protected Object object;
	
	public Object getObject(){
		return object;
	}

	private EngineInfo<Model> engineInfo = new EngineInfo<Model>(this);

	public EngineInfo<Model> getEngineInfo() {
		return engineInfo;
	}

	public void setEngineInfo(EngineInfo<Model> engineInfo) {
		this.engineInfo = engineInfo;
	}

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}
	
}
