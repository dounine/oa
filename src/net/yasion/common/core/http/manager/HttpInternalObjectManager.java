package net.yasion.common.core.http.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.utils.HttpUtils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/** 管理http内置对象以及线程唯一变量 */
public class HttpInternalObjectManager {

	/* session与Thread对应关系：一个session可以在多个线程中使用,一个线程只能获取一个session */

	/** Servlet上下文 */
	private static ServletContext servletContext = null;

	/** 线程Id与Session映射对象 */
	private static Map<Long, HttpSession> threadIdSessionMap = new HashMap<Long, HttpSession>();

	/** Session Id与Session映射对象 */
	private static Map<String, HttpSession> sessionIdSessionMap = new HashMap<String, HttpSession>();

	/** 线程Id与Session Id映射对象 */
	private static Map<Long, String> threadIdSessionIdMap = new HashMap<Long, String>();

	/** Session Id 与线程集合映射对象 */
	private static Map<String, Set<Long>> sessionIdthreadIdsMap = new HashMap<String, Set<Long>>();

	/** 线程全局参数对象 */
	private static ThreadLocal<Map<String, Object>> threadGlobalVariable = new ThreadLocal<Map<String, Object>>();

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		HttpInternalObjectManager.servletContext = servletContext;
	}

	/**
	 * 把session给HttpInternalObjectManager管理
	 * 
	 * @param session
	 *            指定的session
	 */
	public synchronized static void putSession(HttpSession session) {
		if (null != session) {
			threadIdSessionMap.put(Thread.currentThread().getId(), session);
			sessionIdSessionMap.put(session.getId(), session);
			threadIdSessionIdMap.put(Thread.currentThread().getId(), session.getId());
			Set<Long> threadIdSet = sessionIdthreadIdsMap.get(session.getId());
			if (null != threadIdSet) {
				threadIdSet.add(Thread.currentThread().getId());
			} else {
				threadIdSet = new HashSet<Long>();
				threadIdSet.add(Thread.currentThread().getId());
				sessionIdthreadIdsMap.put(session.getId(), threadIdSet);
			}
		}
	}

	/**
	 * 把request给HttpInternalObjectManager管理
	 * 
	 * @param request
	 *            指定的request
	 */
	public static void putRequest(HttpServletRequest request) {
		putThreadVariable(CommonConstants.THREAD_GLOBAL_VARIABLE_REQUEST, request);
	}

	/**
	 * 把response给HttpInternalObjectManager管理
	 * 
	 * @param response
	 *            指定的response
	 */
	public static void putResponse(HttpServletResponse response) {
		putThreadVariable(CommonConstants.THREAD_GLOBAL_VARIABLE_RESPONSE, response);
	}

	/**
	 * 把某些线程内唯一的变量给HttpInternalObjectManager管理
	 * 
	 * @param variableName
	 *            指定的变量名
	 * @param variableValue
	 *            变量值
	 */
	public static void putThreadVariable(String variableName, Object variableValue) {
		Map<String, Object> globalVariableMap = threadGlobalVariable.get();
		if (null == globalVariableMap) {
			globalVariableMap = new HashMap<String, Object>();
			threadGlobalVariable.set(globalVariableMap);
		}
		globalVariableMap.put(variableName, variableValue);
	}

	/**
	 * 根据线程Id获取session
	 * 
	 * @param threadId
	 *            线程Id
	 * @return session
	 */
	public static HttpSession getSession(Long threadId) {
		return threadIdSessionMap.get(threadId);
	}

	/**
	 * 根据session Id获取session
	 * 
	 * @param sessionId
	 *            session Id
	 * @return session
	 */
	public static HttpSession getSession(String sessionId) {
		return sessionIdSessionMap.get(sessionId);
	}

	/**
	 * 获取当前线程的session
	 * 
	 * @return session
	 */
	public static HttpSession getCurrentSession() {
		return getSession(Thread.currentThread().getId());
	}

	/**
	 * 通过当前请求对象url连接里面的jsessionid参数,获取共享的session,如果没有对应jsessionid就返回null
	 * 
	 * @return 共享的session,或者null
	 */
	public static HttpSession getSharedSession() {
		HttpServletRequest request = getCurrentRequest();
		HttpSession session = null;
		try {
			if (null != request) {
				Map<String, String[]> paramMap = HttpUtils.getQueryParams(request);
				String[] values = paramMap.get(CommonConstants.JSESSION_URL);
				if (ArrayUtils.isNotEmpty(values)) {
					String jsessionId = values[0];
					if (StringUtils.isNotBlank(jsessionId)) {
						session = getSession(jsessionId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	/**
	 * 根据变量名获取线程内指定的变量值
	 * 
	 * @param variableName
	 *            变量名
	 * @return 变量值
	 */
	public static Object getThreadVariable(String variableName) {
		if (StringUtils.isNotBlank(variableName)) {
			Map<String, Object> globalVariableMap = threadGlobalVariable.get();
			if (null != globalVariableMap) {
				return globalVariableMap.get(variableName);
			}
		}
		return null;
	}

	/**
	 * 获取当前线程的request
	 * 
	 * @return request
	 */
	public static HttpServletRequest getCurrentRequest() {
		Object origRequest = getThreadVariable(CommonConstants.THREAD_GLOBAL_VARIABLE_REQUEST);
		if (null != origRequest) {
			if (HttpServletRequest.class.isAssignableFrom(origRequest.getClass())) {
				return (HttpServletRequest) origRequest;
			}
		}
		return null;
	}

	/**
	 * 获取当前线程的response
	 * 
	 * @return response
	 */
	public static HttpServletResponse getCurrentResponse() {
		Object origResponse = getThreadVariable(CommonConstants.THREAD_GLOBAL_VARIABLE_RESPONSE);
		if (null != origResponse) {
			if (HttpServletResponse.class.isAssignableFrom(origResponse.getClass())) {
				return (HttpServletResponse) origResponse;
			}
		}
		return null;
	}

	/**
	 * 移除当前线程的session,HttpInternalObjectManager不再管理它
	 * 
	 * @return 被移除的session对象
	 * 
	 */
	public static HttpSession removeCurrentSession() {
		HttpSession session = getSession(Thread.currentThread().getId());
		removeSession(session);
		return session;
	}

	/**
	 * 移除指定的session,HttpInternalObjectManager不再管理它
	 * 
	 * @param session
	 *            指定的session
	 */
	public synchronized static void removeSession(HttpSession session) {
		if (null != session) {
			// 一个session可能在多条线程中使用,因为springMVC每个请求一个线程处理
			Set<Long> threadIdSet = sessionIdthreadIdsMap.remove(session.getId());
			if (null != threadIdSet) {
				Iterator<Long> threadIdIt = threadIdSet.iterator();
				while (threadIdIt.hasNext()) {
					Long threadId = threadIdIt.next();
					threadIdSessionMap.remove(threadId);
					threadIdSessionIdMap.remove(threadId);
				}
			}
			sessionIdSessionMap.remove(session.getId());
		}
	}

	/**
	 * 移除线程内的变量,HttpInternalObjectManager不再管理它
	 * 
	 * @param variableName
	 *            变量名
	 * @return 被移除的变量值
	 */
	public static Object removeThreadVariable(String variableName) {
		if (StringUtils.isNotBlank(variableName)) {
			Map<String, Object> globalVariableMap = threadGlobalVariable.get();
			if (null != globalVariableMap) {
				return globalVariableMap.remove(variableName);
			}
		}
		return null;
	}

	/**
	 * 移除当前线程的request,HttpInternalObjectManager不再管理它
	 * 
	 * @return 被移除的request对象
	 * 
	 */
	public static Object removeCurrentRequest() {
		return removeThreadVariable(CommonConstants.THREAD_GLOBAL_VARIABLE_REQUEST);
	}

	/**
	 * 移除当前线程的response,HttpInternalObjectManager不再管理它
	 * 
	 * @return 被移除的response对象
	 * 
	 */
	public static Object removeCurrentResponse() {
		return removeThreadVariable(CommonConstants.THREAD_GLOBAL_VARIABLE_RESPONSE);
	}
}