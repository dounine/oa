package net.yasion.common.support.common.processor;

import net.yasion.common.constant.CommonConstants;

import org.springframework.web.servlet.ModelAndView;

public class CustomReturnPageProcessor extends BaseReturnPageProcessor {

	public CustomReturnPageProcessor() {
		super();
	}

	public CustomReturnPageProcessor(String viewName, String basePath) {
		super(viewName, basePath, null);
		String styleName = CommonConstants.CUSTOM_STYLE;
		this.setStyleName(styleName);
	}

	public CustomReturnPageProcessor(String viewName, String basePath, String styleName) {
		super(viewName, basePath, styleName);
	}

	public CustomReturnPageProcessor(String viewName, String basePath, String styleName, ModelAndView modelAndView) {
		super(viewName, basePath, styleName, modelAndView);
	}

	public CustomReturnPageProcessor(String viewName, String basePath, String styleName, Object modelObject, ModelAndView modelAndView) {
		super(viewName, basePath, styleName, modelObject, modelAndView);
	}
}