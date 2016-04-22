package net.yasion.common.support.common.processor;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

public class BaseReturnPageProcessor {

	private String viewName;
	private String basePath;
	private String styleName;
	private Object modelObject;
	private ModelAndView modelAndView;

	public BaseReturnPageProcessor() {
		super();
	}

	public BaseReturnPageProcessor(String viewName, String basePath, String styleName) {
		super();
		this.viewName = viewName;
		this.basePath = basePath;
		this.styleName = styleName;
	}

	public BaseReturnPageProcessor(String viewName, String basePath, String styleName, ModelAndView modelAndView) {
		super();
		this.viewName = viewName;
		this.basePath = basePath;
		this.styleName = styleName;
		this.modelAndView = modelAndView;
	}

	public BaseReturnPageProcessor(String viewName, String basePath, String styleName, Object modelObject, ModelAndView modelAndView) {
		super();
		this.viewName = viewName;
		this.basePath = basePath;
		this.styleName = styleName;
		this.modelObject = modelObject;
		this.modelAndView = modelAndView;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public Object getModelObject() {
		return modelObject;
	}

	public void setModelObject(Object modelObject) {
		this.modelObject = modelObject;
	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}

	public void setModelAndView(ModelAndView modelAndView) {
		this.modelAndView = modelAndView;
	}

	public String returnViewName() {
		if (this.viewName.startsWith("/")) {
			return this.viewName;
		} else {
			return getRealViewName();
		}
	}

	@SuppressWarnings("unchecked")
	public ModelAndView returnModelAndView() {
		if (null == this.modelAndView) {
			this.modelAndView = new ModelAndView();
			String realViewName = getRealViewName();
			this.modelAndView.setViewName(realViewName);
		}
		if (!this.modelAndView.hasView()) {
			String realViewName = getRealViewName();
			this.modelAndView.setViewName(realViewName);
		}
		if (this.modelObject instanceof Map) {
			this.modelAndView.addAllObjects((Map<String, ?>) this.modelObject);
		} else {
			this.modelAndView.addObject(modelObject);
		}
		return this.modelAndView;
	}

	protected String getRealViewName() {
		String thisBasePath = this.basePath;
		String thisViewName = this.viewName;
		thisBasePath = (thisBasePath.endsWith("/") ? thisBasePath : thisBasePath + "/");
		thisBasePath = (thisBasePath.startsWith("/") ? thisBasePath.substring(1) : thisBasePath);
		thisViewName = ("/" + this.viewName);
		return thisBasePath + this.styleName + thisViewName;
	}
}