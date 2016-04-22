package net.yasion.common.web.interceptor;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.model.TbPermission;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IRolePermissionService;
import net.yasion.common.service.IUserRoleService;
import net.yasion.common.service.impl.RolePermissionServiceImpl;
import net.yasion.common.service.impl.UserRoleServiceImpl;
import net.yasion.common.utils.UserUtils;
import net.yasion.common.web.tag.PermissionELFunction;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/** 权限控制拦截器 */
public class PermissionControllInterceptor extends HandlerInterceptorAdapter {

	/** 全部地址的匹配标志 */
	public static final String ALL_URLS = "##allUrls";

	/** 非全部地址的匹配标志 */
	public static final String ALL_NOT_URLS = "##allNotUrls";

	/** 无权限时候重定向的地址,默认是/index.do */
	private String redirectUrl;

	/** 不做控制的urls */
	private Set<String> excludeUrls;

	/** 默认站点的urls */
	private Set<String> defContextUrls;

	/** 默认白名单的urls */
	private Set<String> defWhiteUrls = (StringUtils.isBlank(CommonConstants.PERMISSION_DEF_WHITE_URLS) ? null : new HashSet<String>(Arrays.asList(StringUtils.split(CommonConstants.PERMISSION_DEF_WHITE_URLS, ","))));

	/** 默认黑名单的urls */
	private Set<String> defBlackUrls = (StringUtils.isBlank(CommonConstants.PERMISSION_DEF_BLACK_URLS) ? null : new HashSet<String>(Arrays.asList(StringUtils.split(CommonConstants.PERMISSION_DEF_BLACK_URLS, ","))));

	{// 初始化部分默认值
		redirectUrl = (StringUtils.isBlank(CommonConstants.PERMISSION_REDIRECT_URL) ? "/index.do" : CommonConstants.PERMISSION_REDIRECT_URL);
		defContextUrls = new HashSet<String>(Arrays.asList(new String[] { "/" }));
		excludeUrls = new HashSet<String>(Arrays.asList(new String[] { this.redirectUrl }));
		HashSet<String> constantsExcludeUrls = (StringUtils.isBlank(CommonConstants.PERMISSION_EXCLUDE_URLS) ? new HashSet<String>() : new HashSet<String>(Arrays.asList(StringUtils.split(CommonConstants.PERMISSION_EXCLUDE_URLS, ","))));
		excludeUrls.addAll(constantsExcludeUrls);
		excludeUrls.addAll(defContextUrls);
	}

	public Set<String> getExcludeUrls() {
		return excludeUrls;
	}

	/**
	 * 设置排除名单地址,不过该操作会覆盖配置文件的设置的值 *
	 * 
	 * @param excludeUrls
	 *            排除名单集合
	 */
	public void setExcludeUrls(Set<String> excludeUrls) {
		excludeUrls = (null == excludeUrls ? new HashSet<String>() : excludeUrls);
		excludeUrls.add(this.getRedirectUrl());
		excludeUrls.addAll(defContextUrls);
		this.excludeUrls = excludeUrls;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * 设置重定向地址,不过该操作会覆盖配置文件的设置的值 *
	 * 
	 * @param redirectUrl
	 *            重定向地址
	 */
	public void setRedirectUrl(String redirectUrl) {
		redirectUrl = (StringUtils.trim(redirectUrl));
		redirectUrl = (null == redirectUrl ? "/index.do" : redirectUrl);
		redirectUrl = (redirectUrl.startsWith("/") ? redirectUrl : ("/" + redirectUrl));
		this.redirectUrl = redirectUrl;
	}

	public Set<String> getDefWhiteUrls() {
		return defWhiteUrls;
	}

	/**
	 * 设置默认白名单地址,不过该操作会覆盖配置文件的设置的值 *
	 * 
	 * @param defWhiteUrls
	 *            默认白名单集合
	 */
	public void setDefWhiteUrls(Set<String> defWhiteUrls) {
		this.defWhiteUrls = defWhiteUrls;
	}

	public Set<String> getDefBlackUrls() {
		return defBlackUrls;
	}

	/**
	 * 设置默认黑名单地址,不过该操作会覆盖配置文件的设置的值
	 * 
	 * @param defBlackUrls
	 *            默认黑名单集合
	 */
	public void setDefBlackUrls(Set<String> defBlackUrls) {
		this.defBlackUrls = defBlackUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			IUserRoleService userRoleService = SpringBeanManager.getBean(IUserRoleService.class, UserRoleServiceImpl.class);
			IRolePermissionService rolePermissionService = SpringBeanManager.getBean(IRolePermissionService.class, RolePermissionServiceImpl.class);
			String contextPath = request.getContextPath();
			String requestURI = request.getRequestURI();
			int contextPathPos = StringUtils.indexOf(requestURI, contextPath);
			requestURI = requestURI.substring(contextPathPos + contextPath.length());
			requestURI = (requestURI.startsWith("/") ? requestURI : ("/" + requestURI));
			String method = request.getMethod();
			TbUser loginUser = UserUtils.getCurrentUser();
			if (null == excludeUrls || !this.isExcludeUrl(requestURI)) {
				if (null != userRoleService && null != rolePermissionService) {
					boolean isOk = true;
					boolean isDefault = true;
					if (null != loginUser) {// 无登录,默认无权限
						if (!(loginUser instanceof TbSuperUser) && !(PermissionELFunction.isRoles(loginUser, StringUtils.split(CommonConstants.ADMIN_ROLE_CODE, ",")))) {// 如果是超级管理员直接通过全部权限
							List<TbRole> roleList = userRoleService.findRolesByUser(loginUser);
							for (int i = 0, len = roleList.size(); i < len; i++) {// 如果无role，默认全部允许
								TbRole role = roleList.get(i);
								if (null != role) {
									List<TbPermission> permissionList = rolePermissionService.findPermissionByRole(role);
									for (int j = 0, len2 = permissionList.size(); j < len2; j++) {// 如果无permission，默认全部允许
										TbPermission permission = permissionList.get(j);
										if (null != permission) {
											isDefault = false;// 有角色和权限
											String whiteUrls = permission.getWhiteUrls();
											String blackUrls = permission.getBlackUrls();
											if (StringUtils.isNotBlank(whiteUrls)) {
												if (this.processPermissionUrls(whiteUrls, method, requestURI)) {
													return true;// 符合白名单直接返回true继续执行
												}
											} else {// 使用默认白名单
												if (this.processPermissionUrls(this.defWhiteUrls, method, requestURI)) {
													return true;// 符合默认白名单直接返回true继续执行
												}
											}
											if (StringUtils.isNotBlank(blackUrls)) {
												if (this.processPermissionUrls(blackUrls, method, requestURI)) {
													isOk = false;// 其他权限或者角色有白名单
												}
											} else {// 使用默认黑名单
												if (this.processPermissionUrls(this.defBlackUrls, method, requestURI)) {
													isOk = false;// 默认黑名单不通过
												}
											}
										}
									}
								}
							}
							if (isDefault) {// 当都没有角色和权限的时候使用
								if (this.processPermissionUrls(this.defWhiteUrls, method, requestURI)) {
									return true;// 符合默认白名单直接返回true继续执行
								}
								if (this.processPermissionUrls(this.defBlackUrls, method, requestURI)) {
									isOk = false;// 默认黑名单不通过
								}
							}
						}
					} else {
						isOk = false;// 无登录,默认无权限
					}
					if (!isOk) {// 有黑名单,但无白名单
						boolean isJson = request.getHeader("accept").contains("json");
						response.setCharacterEncoding("UTF-8");
						response.setHeader("Cache-Control", "no-cache");
						if (isJson) {
							response.setContentType("text/json;charset=UTF-8");
							PrintWriter out = response.getWriter();
							out.println("{\"msg\":\"Message:登陆超时,请重新登录!\"}");
							out.close();
						} else {
							response.setContentType("text/html;charset=UTF-8");
							PrintWriter out = response.getWriter();
							if (null == loginUser) {
								out.println("<script>if(confirm('你没有权限访问此页面!'))window.top.location.href='" + contextPath + this.getRedirectUrl() + "'</script>");
							} else {
								out.println("<script>alert('你没有权限访问此页面!');history.go(-1);</script>");
							}
							out.close();
						}
						return false;// 没有权限直接返回false跳转回首页
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;// 执行出错直接通过，不妨碍正常功能实用
	}

	protected boolean processPermissionUrls(String urls, String requestMethod, String requestURI) {
		if (StringUtils.isNotBlank(urls)) {
			Set<String> urlSet = new HashSet<String>();
			String[] urlsArr = StringUtils.split(StringUtils.trim(urls), ",");
			for (int k = 0, len3 = urlsArr.length; k < len3; k++) {
				String url = StringUtils.trim(urlsArr[k]);
				urlSet.add(url);
			}
			if (urlSet.contains(ALL_URLS)) {// 如果有ALL_URLS则是匹配
				return true;
			}
			if (urlSet.contains(ALL_NOT_URLS)) {// 如果有ALL_NOT_URLS则是不匹配,不过会被ALL_URLS覆盖
				return false;
			}
			Map<?, ?> requestMappingMap = (Map<?, ?>) HttpInternalObjectManager.getServletContext().getAttribute(CommonConstants.GLOBAL_REQUEST_MAPPING_URL);
			Iterator<String> urlIt = urlSet.iterator();
			while (urlIt.hasNext()) {
				String url = urlIt.next();
				boolean isEq = false;
				if (StringUtils.trim(requestURI).matches(url)) {// 如果匹配手动输入的匹配表达式,直接返回true
					isEq = true;
				} else {
					RequestMapping requestMapping = (RequestMapping) requestMappingMap.get(url);
					if (null != requestMapping) {
						// 暂时只支持vales和method的判断
						RequestMethod[] methodArr = requestMapping.method();
						String[] valueArr = requestMapping.value();
						// 最初时候为true,防止无值时候返回false
						boolean methodEq = true;
						boolean valueEq = true;
						if (ArrayUtils.isNotEmpty(methodArr)) {
							methodEq = false;// 初始化为false
							for (int l = 0, len4 = methodArr.length; l < len4; l++) {
								RequestMethod methodType = methodArr[l];
								if (methodType.name().equals(requestMethod)) {
									methodEq = true;
									break;
								}
							}
						}
						if (ArrayUtils.isNotEmpty(valueArr)) {
							valueEq = false;// 初始化为false
							for (int l = 0, len4 = valueArr.length; l < len4; l++) {
								String values = StringUtils.trim(valueArr[l]);
								values = (values.startsWith("/") ? values : ("/" + values));
								if (StringUtils.trim(requestURI).matches(values)) {
									valueEq = true;
									break;
								}
							}
						}
						isEq = (methodEq && valueEq);// 两个都相等才有效
					}
				}
				if (isEq) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean processPermissionUrls(Set<String> urlSet, String requestMethod, String requestURI) {
		if (null != urlSet && 0 < urlSet.size()) {
			StringBuilder builder = new StringBuilder();
			String[] urlArr = urlSet.toArray(new String[0]);
			for (int i = 0, len = urlArr.length; i < len; i++) {
				String url = StringUtils.trim(urlArr[i]);
				builder.append(url + ((len - 1 > i) ? "," : ""));
			}
			return this.processPermissionUrls(builder.toString(), requestMethod, requestURI);
		}
		return false;
	}

	protected boolean isExcludeUrl(String urls) {
		urls = (null == urls ? "" : urls);
		urls = StringUtils.trim(urls);
		String thisUrls = (urls.startsWith("/") ? urls : ("/" + urls));
		if (null != this.excludeUrls) {
			Iterator<String> excludeUrlIt = this.excludeUrls.iterator();
			while (excludeUrlIt.hasNext()) {
				String excludeUrl = excludeUrlIt.next();
				excludeUrl = (StringUtils.trim(excludeUrl));
				excludeUrl = (excludeUrl.startsWith("/") ? excludeUrl : ("/" + excludeUrl));
				if (thisUrls.matches(excludeUrl)) {
					return true;
				}
			}
		}
		return false;
	}
}