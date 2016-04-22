package net.yasion.common.core.bean.listener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import net.yasion.common.constant.CommonConstants;
import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.core.http.manager.HttpInternalObjectManager;
import net.yasion.common.utils.AfxBeanUtils;
import net.yasion.common.web.interceptor.PermissionControllInterceptor;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** 在spring容器启动完成后会触发ContextRefreshedEvent事件,这里给spring bean处理一些操作 */
@Component
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

	private Map<RequestMethod, String> requestMethodStringMap = new HashMap<RequestMethod, String>();

	public Map<RequestMethod, String> getRequestMethodStringMap() {
		return requestMethodStringMap;
	}

	public void setRequestMethodStringMap(Map<RequestMethod, String> requestMethodStringMap) {
		this.requestMethodStringMap = requestMethodStringMap;
	}

	public ApplicationContextListener() {
		super();
		requestMethodStringMap.put(RequestMethod.GET, "GET");
		requestMethodStringMap.put(RequestMethod.HEAD, "HEAD");
		requestMethodStringMap.put(RequestMethod.POST, "POST");
		requestMethodStringMap.put(RequestMethod.PUT, "PUT");
		requestMethodStringMap.put(RequestMethod.DELETE, "DELETE");
		requestMethodStringMap.put(RequestMethod.OPTIONS, "OPTIONS");
		requestMethodStringMap.put(RequestMethod.TRACE, "TRACE");
		System.out.println("ApplicationContextListener Create");
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		SpringBeanManager.getAppContextSet().add(event.getApplicationContext());// 设置SpringBeanManager的容器
		this.prepareControllerAnnotationClass();
	}

	protected void prepareControllerAnnotationClass() {
		Map<String, Object> controllerObj = SpringBeanManager.getBeansWithAnnotation(Controller.class);
		if (null != controllerObj && 0 < controllerObj.size()) {
			this.loadGlobalRequestMapping();// 加载Controller类进去内存
		}
	}

	protected void loadGlobalRequestMapping() {
		Map<String, Annotation> requestMappingAnnotationMap = new LinkedHashMap<String, Annotation>();
		Map<String, Object> atControllerMap = SpringBeanManager.getBeansWithAnnotation(Controller.class);
		requestMappingAnnotationMap.put(PermissionControllInterceptor.ALL_URLS, null);
		requestMappingAnnotationMap.put(PermissionControllInterceptor.ALL_NOT_URLS, null);
		if (null != atControllerMap) {
			Collection<Object> valuesColl = atControllerMap.values();
			Iterator<Object> valuesIt = valuesColl.iterator();
			while (valuesIt.hasNext()) {
				Object atControllerObj = valuesIt.next();
				Method[] methodArr = AfxBeanUtils.getAnnotationMethods(atControllerObj.getClass(), RequestMapping.class);
				for (int i = 0, len = methodArr.length; i < len; i++) {
					Method method = methodArr[i];
					if (null != method) {
						Annotation annotation = method.getAnnotation(RequestMapping.class);
						if (null != annotation) {
							StringBuilder builder = new StringBuilder();
							RequestMapping requestMappingObj = (RequestMapping) annotation;
							String[] values = requestMappingObj.value();
							String[] consumes = requestMappingObj.consumes();
							String[] headers = requestMappingObj.headers();
							RequestMethod[] methods = requestMappingObj.method();
							String[] params = requestMappingObj.params();
							String[] produces = requestMappingObj.produces();
							if (ArrayUtils.isNotEmpty(consumes)) {
								builder.append("C:");
								for (int j = 0, len2 = consumes.length; j < len2; j++) {
									String consume = consumes[j];
									builder.append(consume + ((j < len2 - 1) ? ";" : ""));
								}
								builder.append("#");
							}
							if (ArrayUtils.isNotEmpty(headers)) {
								builder.append("H:");
								for (int j = 0, len2 = headers.length; j < len2; j++) {
									String header = headers[j];
									builder.append(header + ((j < len2 - 1) ? ";" : ""));
								}
								builder.append("#");
							}
							if (ArrayUtils.isNotEmpty(methods)) {
								builder.append("M:");
								for (int j = 0, len2 = methods.length; j < len2; j++) {
									RequestMethod requestMethod = methods[j];
									builder.append(requestMethodStringMap.get(requestMethod) + ((j < len2 - 1) ? ";" : ""));
								}
								builder.append("#");
							}
							if (ArrayUtils.isNotEmpty(params)) {
								builder.append("PA:");
								for (int j = 0, len2 = params.length; j < len2; j++) {
									String param = params[j];
									builder.append(param + ((j < len2 - 1) ? ";" : ""));
								}
								builder.append("#");
							}
							if (ArrayUtils.isNotEmpty(produces)) {
								builder.append("PR:");
								for (int j = 0, len2 = produces.length; j < len2; j++) {
									String produce = produces[j];
									builder.append(produce + ((j < len2 - 1) ? ";" : ""));
								}
								builder.append("#");
							}
							if (ArrayUtils.isNotEmpty(values)) {
								builder.append("V:");
								for (int j = 0, len2 = values.length; j < len2; j++) {
									String value = values[j];
									builder.append(value + ((j < len2 - 1) ? ";" : ""));
								}
							}
							requestMappingAnnotationMap.put(builder.toString(), requestMappingObj);
						}
					}
				}
			}
		}
		HttpInternalObjectManager.getServletContext().setAttribute(CommonConstants.GLOBAL_REQUEST_MAPPING_URL, Collections.unmodifiableMap(requestMappingAnnotationMap));
	}
}