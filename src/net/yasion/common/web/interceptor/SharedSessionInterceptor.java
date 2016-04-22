package net.yasion.common.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.utils.HttpUtils;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SharedSessionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession sharedSession = HttpInternalObjectManager.getSharedSession();
		HttpSession currentSession = HttpInternalObjectManager.getCurrentSession();
		if (null != sharedSession && null != currentSession) {
			HttpUtils.copyHttpInternalObjectAttribute(sharedSession, currentSession);
		}
		return true;
	}
}