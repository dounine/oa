package net.yasion.common.web.view;

import javax.servlet.http.HttpServletRequest;

import net.yasion.common.constant.CommonConstants;

import org.springframework.web.servlet.view.InternalResourceView;

public class ExtInternalResourceView extends InternalResourceView {

	/**
	 * 在request中增加部署路径,共享session字符串,sessionId等属性,方便处理部署路径问题。
	 * 
	 * @param request
	 *            请求对象
	 */
	protected void exposeHelpers(HttpServletRequest request) throws Exception {
		super.exposeHelpers(request);
		request.setAttribute(CommonConstants.CONTEXT_PATH_ATTRIBUTE_NAME, request.getContextPath());
		request.setAttribute(CommonConstants.JSESSION_ID, ";jsessionid=" + request.getSession().getId());
		request.setAttribute(CommonConstants.SESSION_ID, request.getSession().getId());
	}
}