package net.yasion.common.core.http.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yasion.common.core.http.manager.HttpInternalObjectManager;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class HttpInternalObjectInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpInternalObjectManager.putRequest(request);
		HttpInternalObjectManager.putResponse(response);
		HttpInternalObjectManager.putSession(request.getSession());// 必须,确保能够捕捉的当前线程的session
		return true;
	}
}