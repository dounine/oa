package net.yasion.common.core.bean.manager;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.yasion.common.core.uurp.UurpAdaptor;
import net.yasion.common.core.uurp.impl.UurpAdaptorImpl;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;

/**
 * Spring Bean 工具类,可以获取bean或者bean的属性值
 * 
 * @author mayj 20130531
 */
public class SpringBeanManager {

	/**
	 * 所有Spring上下文的集合,以便获取对应属性
	 */
	private static Set<ApplicationContext> appContextSet = new HashSet<ApplicationContext>(0);

	public static Set<ApplicationContext> getAppContextSet() {
		return SpringBeanManager.appContextSet;
	}

	/**
	 * 按bean名字获取bean。
	 * 
	 * @param beanName
	 *            名字
	 * @return 返回对应bean,可能返回null。
	 */
	public static Object getBean(String beanName) {
		Object bean = null;
		for (ApplicationContext appContext : SpringBeanManager.appContextSet) {
			if (appContext != null) {
				try {
					bean = appContext.getBean(beanName);
					if (bean != null) {
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return bean;
	}

	/**
	 * 按bean类类型获取bean。
	 * 
	 * @param clazz
	 *            类类型
	 * @return 返回对应bean,可能返回null。
	 */
	public static <T> T getBean(Class<T> clazz) {
		T bean = null;
		for (ApplicationContext appContext : SpringBeanManager.appContextSet) {
			if (appContext != null) {
				try {
					bean = appContext.getBean(clazz);
					if (bean != null) {
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return bean;
	}

	/**
	 * 按bean类类型(父类或者接口)以及bean实现类类类型获取bean。
	 * 
	 * @param clazz
	 *            类类型(父类或者接口)
	 * @param implClazz
	 *            实现类类类型
	 * @return 返回对应bean,可能返回null。
	 */
	public static <T> T getBean(Class<T> clazz, Class<? extends T> implClazz) {
		T bean = null;
		Map<String, T> clazzImplMap = getBeansOfType(clazz);
		Iterator<T> classImplIt = clazzImplMap.values().iterator();
		while (classImplIt.hasNext()) {
			T classImpl = classImplIt.next();
			if (!AopUtils.isAopProxy(classImpl)) {
				if (classImpl.getClass().isAssignableFrom(implClazz)) {
					bean = classImpl;
					break;
				}
			} else {
				T realClassImpl = null;
				if (AopUtils.isJdkDynamicProxy(classImpl)) {// jdk
					realClassImpl = getJdkDynamicProxyTargetObject(classImpl);
				} else { // cglib
					realClassImpl = getCglibProxyTargetObject(classImpl);
				}
				if (null != realClassImpl) {
					if (realClassImpl.getClass().isAssignableFrom(implClazz)) {
						bean = classImpl;
						break;
					}
				}
			}
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	protected static <T> T getCglibProxyTargetObject(T proxy) {
		try {
			Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
			h.setAccessible(true);
			Object dynamicAdvisedInterceptor = h.get(proxy);
			Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
			advised.setAccessible(true);
			T target = (T) ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
			return target;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected static <T> T getJdkDynamicProxyTargetObject(T proxy) {
		try {
			Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
			h.setAccessible(true);
			AopProxy aopProxy = (AopProxy) h.get(proxy);
			Field advised = aopProxy.getClass().getDeclaredField("advised");
			advised.setAccessible(true);
			T target = (T) ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
			return target;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按类注解clazz类型获取多个bean
	 * 
	 * @param clazz
	 *            类注解类类型
	 * @return 返回bean映射集合,可能返回null。
	 */
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
		Map<String, Object> beanMap = null;
		for (ApplicationContext appContext : SpringBeanManager.appContextSet) {
			if (appContext != null) {
				try {
					beanMap = appContext.getBeansWithAnnotation(clazz);
					if (beanMap != null && 0 < beanMap.size()) {
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return beanMap;
	}

	/**
	 * 按指定clazz类型获取多个bean
	 * 
	 * @param clazz
	 *            指定类类型
	 * @return 返回bean映射集合,可能返回null。
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		Map<String, T> beanMap = null;
		for (ApplicationContext appContext : SpringBeanManager.appContextSet) {
			if (appContext != null) {
				try {
					beanMap = appContext.getBeansOfType(clazz);
					if (beanMap != null && 0 < beanMap.size()) {
						break;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return beanMap;
	}

	public static UurpAdaptor getUurpAdaptor() {
		return getBean(UurpAdaptor.class, UurpAdaptorImpl.class);
	}
}