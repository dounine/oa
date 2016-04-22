package net.yasion.common.support.common.processor;

import net.yasion.common.constant.CommonConstants;

import org.springframework.web.servlet.ModelAndView;

public class CommonReturnPageProcessor extends BaseReturnPageProcessor {

	public CommonReturnPageProcessor() {
		super();
	}

	public CommonReturnPageProcessor(String viewName) {
		super(viewName, null, null);
		String styleName = CommonConstants.COMMON_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/common");
	}

	public CommonReturnPageProcessor(String viewName, ModelAndView modelAndView) {
		super(viewName, null, null, modelAndView);
		String styleName = CommonConstants.COMMON_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/common");
	}

	public CommonReturnPageProcessor(String viewName, Object modelObject, ModelAndView modelAndView) {
		super(viewName, null, null, modelObject, modelAndView);
		String styleName = CommonConstants.COMMON_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/common");
	}
}