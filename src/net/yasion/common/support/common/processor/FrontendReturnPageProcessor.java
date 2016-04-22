package net.yasion.common.support.common.processor;

import net.yasion.common.constant.CommonConstants;

import org.springframework.web.servlet.ModelAndView;

public class FrontendReturnPageProcessor extends BaseReturnPageProcessor {

	public FrontendReturnPageProcessor() {
		super();
	}

	public FrontendReturnPageProcessor(String viewName) {
		super(viewName, null, null);
		String styleName = CommonConstants.FRONTEND_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/frontend");
	}

	public FrontendReturnPageProcessor(String viewName, ModelAndView modelAndView) {
		super(viewName, null, null, modelAndView);
		String styleName = CommonConstants.FRONTEND_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/frontend");
	}

	public FrontendReturnPageProcessor(String viewName, Object modelObject, ModelAndView modelAndView) {
		super(viewName, null, null, modelObject, modelAndView);
		String styleName = CommonConstants.FRONTEND_STYLE;
		this.setStyleName(styleName);
		this.setBasePath("/frontend");
	}
}