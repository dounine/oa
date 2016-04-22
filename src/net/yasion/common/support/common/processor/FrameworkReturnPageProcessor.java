package net.yasion.common.support.common.processor;

import net.yasion.common.constant.CommonConstants;

import org.springframework.web.servlet.ModelAndView;

public class FrameworkReturnPageProcessor extends BaseReturnPageProcessor {

	public FrameworkReturnPageProcessor() {
		super();
	}

	public FrameworkReturnPageProcessor(String viewName) {
		super(viewName, null, null);
		String styleName = CommonConstants.FRAMEWORK_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/framework");
	}

	public FrameworkReturnPageProcessor(String viewName, ModelAndView modelAndView) {
		super(viewName, null, null, modelAndView);
		String styleName = CommonConstants.FRAMEWORK_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/framework");
	}

	public FrameworkReturnPageProcessor(String viewName, Object modelObject, ModelAndView modelAndView) {
		super(viewName, null, null, modelObject, modelAndView);
		String styleName = CommonConstants.FRAMEWORK_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/framework");
	}
}