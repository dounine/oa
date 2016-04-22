package net.yasion.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.yasion.common.core.bean.manager.SpringBeanManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtils {

	private static final SessionFactory sessionFactory = lookupSessionFactory();

	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}

	protected static SessionFactory lookupSessionFactory() {
		SessionFactory sessionFactory = SpringBeanManager.getBean(SessionFactory.class);
		return sessionFactory;
	}

	/** 获取当前的Hibeernate Session */
	public static Session getCurrentSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	/** 强制清除缓存 */
	public static void clearCache() {
		Session session = getCurrentSession();
		if (null != session) {
			session.clear();
		}
	}

	/** 强制进行从内存到数据库的同步 */
	public static void flushCache() {
		Session session = getCurrentSession();
		if (null != session) {
			session.flush();
		}
	}

	/**
	 * 把指定的缓冲对象进行清除
	 * 
	 * @param entity
	 *            指定对象
	 */
	public static void evictCache(Object entity) {
		Session session = getCurrentSession();
		if (null != session) {
			session.evict(entity);
		}
	}

	/**
	 * 获取所有实体的类名
	 * 
	 * @return 所有实体类型的列表
	 */
	public static List<String> getAllEntityClassNames() {
		Map<?, ?> allClassMetadata = getSessionfactory().getAllClassMetadata();
		String[] entityNameArr = allClassMetadata.keySet().toArray(new String[0]);
		return new ArrayList<String>(Arrays.asList(entityNameArr));
	}
}