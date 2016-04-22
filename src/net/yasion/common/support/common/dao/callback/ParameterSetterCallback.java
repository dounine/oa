package net.yasion.common.support.common.dao.callback;

import org.hibernate.Query;

/** 参数设置回调,用于HQL或者SQL查村时候设置参数值 */
public interface ParameterSetterCallback {
	/**
	 * 设置参数
	 * 
	 * @param query
	 *            对应HQL或者SQL生产的查询对象
	 */
	public void onSetter(Query query);
}