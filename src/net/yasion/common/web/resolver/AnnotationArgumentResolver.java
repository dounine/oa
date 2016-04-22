package net.yasion.common.web.resolver;

import java.io.BufferedReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.yasion.common.annotation.ConverterArrayParam;
import net.yasion.common.annotation.ModelDTOCriterion;
import net.yasion.common.annotation.ModelDTOSearch;
import net.yasion.common.annotation.ModelJson;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dto.BaseDTO;
import net.yasion.common.support.common.service.enumeration.OperatorType;
import net.yasion.common.utils.AfxBeanUtils;
import net.yasion.common.utils.HttpUtils;
import net.yasion.common.web.processor.ExtendedServletModelAttributeMethodProcessor;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AnnotationArgumentResolver implements HandlerMethodArgumentResolver {

	protected static final Set<Class<? extends Annotation>> suppoertAnnotationSet = new HashSet<Class<? extends Annotation>>(0);

	static {
		suppoertAnnotationSet.add(ModelJson.class);
		suppoertAnnotationSet.add(ModelDTOSearch.class);
		suppoertAnnotationSet.add(ModelDTOCriterion.class);
		suppoertAnnotationSet.add(ConverterArrayParam.class);
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return (this.isSupportAnnotation(parameter.getParameterAnnotations()));
	}

	protected boolean isSupportAnnotation(Annotation[] annotationClasses) {
		for (int i = 0; i < annotationClasses.length; i++) {
			if (suppoertAnnotationSet.contains(annotationClasses[i].annotationType())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		if (null != parameter.getParameterAnnotation(ModelJson.class)) {
			return this.resolveModelJson(parameter, mavContainer, webRequest, binderFactory);
		} else if (null != parameter.getParameterAnnotation(ModelDTOSearch.class)) {
			return this.resolveModelDTOSearch(parameter, mavContainer, webRequest, binderFactory);
		} else if (null != parameter.getParameterAnnotation(ModelDTOCriterion.class)) {
			return this.resolveModelDTOCriterion(parameter, mavContainer, webRequest, binderFactory);
		} else if (null != parameter.getParameterAnnotation(ConverterArrayParam.class)) {
			return this.resolveConverterArrayParam(parameter, mavContainer, webRequest, binderFactory);
		} else {
			Class<?> parameterClassType = parameter.getParameterType();
			return parameterClassType.newInstance();
		}
	}

	protected String getRequestJsonParameter(HttpServletRequest request) {
		StringBuilder parameterStringValue = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			reader.mark(0);// 可能无效所以在使用了ModelJson注解的方法,request.getReader可能已经失效
			char[] buffer = new char[1024];
			int readCount;
			while (-1 != (readCount = reader.read(buffer))) {
				parameterStringValue.append(buffer, 0, readCount);
			}
			reader.reset();// 重设位置，使得下个参数读取的值有效
			return parameterStringValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
			String parameterValue = parameterStringValue.toString();
			request.setAttribute(CommonConstants.MODEL_JSON_BACKUP_ATTRIBUTE_NAME, parameterValue);
			return parameterValue;
		}
	}

	protected Object resolveModelJson(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String parameterStringValue = this.getRequestJsonParameter(request);
		Class<?> parameterClassType = parameter.getParameterType();
		Object parameterObj = null;
		try {
			if (parameterClassType.isArray()) {
				JSONArray jsonArr = JSONArray.fromObject(parameterStringValue);
				Class<?> arrayType = parameterClassType.getComponentType();
				Object arrayInstance = Array.newInstance(arrayType, jsonArr.size());
				for (int i = 0, len = jsonArr.size(); i < len; i++) {
					JSONObject jsonObj = jsonArr.getJSONObject(i);
					Object bean = JSONObject.toBean(jsonObj, arrayType);
					Array.set(arrayInstance, i, bean);
				}
				parameterObj = arrayInstance;
			} else {
				JSONObject jsonObj = JSONObject.fromObject(parameterStringValue);
				parameterObj = JSONObject.toBean(jsonObj, parameterClassType);
			}
		} catch (Exception e) {
			if (parameterClassType.isArray()) {
				parameterObj = Array.newInstance(parameterClassType.getComponentType(), 0);
			} else {
				parameterObj = parameterClassType.newInstance();
			}
			e.printStackTrace();
		}
		return parameterObj;
	}

	protected Object resolveModelDTOSearch(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Object parameterObj = null;
		Class<?> parameterClassType = parameter.getParameterType();
		ExtendedServletModelAttributeMethodProcessor defAttributeMethodProcessor = new ExtendedServletModelAttributeMethodProcessor(true);
		if (defAttributeMethodProcessor.supportsParameter(parameter)) {
			parameterObj = defAttributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
			if (parameterObj instanceof BaseDTO) {
				BaseDTO<?> dto = (BaseDTO<?>) parameterObj;
				AfxBeanUtils.setEmptyToNull(dto);
				Map<String, String[]> paramMap = HttpUtils.getQueryParams(request);
				dto.generateDefOperateRelation(request.getRequestURI(), paramMap);
				Map<String, List<OperatorType>> operateRelation = dto.getOperateRelation();
				Iterator<String> keyIt = operateRelation.keySet().iterator();
				Map<String, Object> operateValue = new HashMap<String, Object>();
				while (keyIt.hasNext()) {
					try {
						String key = keyIt.next();
						Field field = AfxBeanUtils.getField(dto.getClass(), key);
						Class<?> type = field.getType();
						type = (type.isArray() || Collection.class.isAssignableFrom(type) ? type.getComponentType() : type);
						Constructor<?> constructor = type.getConstructor(String.class);
						String[] vals = paramMap.get(key);
						if (ArrayUtils.isNotEmpty(vals)) {
							List<Object> valList = new ArrayList<Object>();
							for (int i = 0, len = vals.length; i < len; i++) {
								String val = vals[i];
								if (StringUtils.isNotBlank(val)) {
									val = (CommonConstants.GET.equalsIgnoreCase(request.getMethod()) ? URLDecoder.decode(val, "UTF-8") : val);
									Object obj = constructor.newInstance(val);
									valList.add(obj);
								}
							}
							operateValue.put(key, valList);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dto.setOperateValue(operateValue);
			}
			if (null == parameterObj) {
				parameterObj = parameterClassType.newInstance();
			}
			return parameterObj;
		} else {
			return parameterClassType.newInstance();
		}
	}

	protected Object resolveModelDTOCriterion(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		Object parameterObj = null;
		Class<?> parameterClassType = parameter.getParameterType();
		ExtendedServletModelAttributeMethodProcessor defAttributeMethodProcessor = new ExtendedServletModelAttributeMethodProcessor(true);
		if (defAttributeMethodProcessor.supportsParameter(parameter)) {
			parameterObj = defAttributeMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
			if (parameterObj instanceof BaseDTO) {
				BaseDTO<?> dto = (BaseDTO<?>) parameterObj;
				AfxBeanUtils.setEmptyToNull(dto);
				Map<String, String[]> paramMap = HttpUtils.getQueryParams(request);
				dto.generateDefCriterion(request.getRequestURI(), paramMap);
			}
			if (null == parameterObj) {
				parameterObj = parameterClassType.newInstance();
			}
			return parameterObj;
		} else {
			return parameterClassType.newInstance();
		}
	}

	protected Object resolveConverterArrayParam(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		String parameterStringValue = request.getParameter(parameter.getParameterName());
		Class<?> parameterClassType = parameter.getParameterType();
		if (parameterClassType.isArray()) {
			if (null != parameterStringValue) {
				String[] array = StringUtils.split(StringUtils.trim(parameterStringValue), ",|;");
				Class<?> arrayType = parameterClassType.getComponentType();
				Constructor<?> typeConstructor = arrayType.getConstructor(String.class);
				if (null != typeConstructor) {
					Object arrayInstance = Array.newInstance(arrayType, array.length);
					for (int i = 0; i < array.length; i++) {
						try {
							Array.set(arrayInstance, i, typeConstructor.newInstance(array[i]));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					return arrayInstance;
				}
			}
			return Array.newInstance(parameterClassType, 0);
		} else {
			return parameterClassType.newInstance();
		}
	}
}