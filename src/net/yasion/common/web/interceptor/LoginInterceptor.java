package net.yasion.common.web.interceptor;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.model.TbUser;
import net.yasion.common.utils.UserUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	/** 无权限时候重定向的地址,默认是/index.do */
	private String redirectUrl = "/index.do";

	private Set<String> excludeUrls;

	/** 默认站点的urls */
	private Set<String> defContextUrls;

	{// 初始化部分默认值
		defContextUrls = new HashSet<String>(Arrays.asList(new String[] { "/" }));
		excludeUrls = new HashSet<String>(Arrays.asList(new String[] { this.redirectUrl }));
		HashSet<String> constantsExcludeUrls = (StringUtils.isBlank(CommonConstants.LOGIN_EXCLUDE_URLS) ? new HashSet<String>() : new HashSet<String>(Arrays.asList(StringUtils.split(CommonConstants.LOGIN_EXCLUDE_URLS, ","))));
		excludeUrls.addAll(constantsExcludeUrls);
		excludeUrls.addAll(defContextUrls);
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		redirectUrl = StringUtils.trim(redirectUrl);
		redirectUrl = (null == redirectUrl ? "/index.do" : redirectUrl);
		redirectUrl = (redirectUrl.startsWith("/") ? redirectUrl : ("/" + redirectUrl));
		this.redirectUrl = redirectUrl;
	}

	public Set<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(Set<String> excludeUrls) {
		excludeUrls = (null == excludeUrls ? new HashSet<String>() : excludeUrls);
		excludeUrls.add(this.getRedirectUrl());
		excludeUrls.addAll(defContextUrls);
		this.excludeUrls = excludeUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		boolean exists = false;
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		int contextPathPos = StringUtils.indexOf(requestURI, contextPath);
		requestURI = requestURI.substring(contextPathPos + contextPath.length());
		requestURI = (requestURI.startsWith("/") ? requestURI : ("/" + requestURI));

		for (String str : excludeUrls) {
			str = StringUtils.trim(str);
			str = (str.startsWith("/") ? str : ("/" + str));
			if (requestURI.matches(str)) {
				exists = true;
				break;
			}
		}
		if (!exists) {
			TbUser user = UserUtils.getCurrentUser();
			if (null == user) {
				boolean isJson = request.getHeader("accept").contains("json");
				response.setCharacterEncoding("UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				if (!isJson) {
					response.setContentType("text/html;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("<script>if(confirm('登陆超时,请重新登录!'))window.top.location.href='" + contextPath + this.getRedirectUrl() + "'</script>");
					out.close();
				} else {
					response.setContentType("text/json;charset=UTF-8");
					PrintWriter out = response.getWriter();
					out.println("{\"msg\":\"Message:登陆超时,请重新登录!\"}");
					out.close();
				}
				return false;
			}
		}
		return true;
	}
}