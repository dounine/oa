package net.yasion.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.yasion.common.annotation.Alias;
import net.yasion.common.annotation.ModelAliasLink;
import net.yasion.common.constant.CommonConstants;
import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dto.TransformBaseDTO;
import net.yasion.common.model.BaseModel;
import net.yasion.common.support.common.dao.callback.ParameterSetterCallback;
import net.yasion.common.support.common.dao.interfaces.IResultSet;
import net.yasion.common.support.common.dao.transform.ColumnToDTOTransformer;
import net.yasion.common.utils.AfxBeanUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDAOImpl<POJO extends BaseModel<ID>, ID extends Serializable> implements IBaseDAO<POJO, ID> {

	protected SessionFactory sessionFactory;

	private Boolean useCache = true;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Boolean getUseCache() {
		return useCache;
	}

	public void setUseCache(Boolean useCache) {
		this.useCache = useCache;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            唯一标识
	 * @return 持久化对象
	 */
	@Override
	public POJO get(ID id) {
		return get(id, false);
	}

	/**
	 * 根据id数组获取多个实体
	 * 
	 * @param ids
	 *            id数组
	 * @return 持久化对象列表
	 */
	@Override
	public List<POJO> get(ID[] ids) {
		List<POJO> entityList = new ArrayList<POJO>();
		for (int i = 0, len = ids.length; i < len; i++) {
			entityList.add(this.get(ids[i]));
		}
		return entityList;
	}

	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            唯一标识
	 * @param lock
	 *            是否锁定，使用LockOptions.UPGRADE
	 * @return 持久化对象
	 */
	@SuppressWarnings("unchecked")
	protected final POJO get(ID id, boolean lock) {
		POJO entity = null;
		if (lock) {
			entity = (POJO) getSession().get(getEntityClass(), id, LockOptions.UPGRADE);
		} else {
			entity = (POJO) getSession().get(getEntityClass(), id);
		}
		return entity;
	}

	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            唯一标识
	 * @return 持久化对象
	 */
	@Override
	public POJO load(ID id) {
		return load(id, false);
	}

	/**
	 * 根据id数组获取多个实体
	 * 
	 * @param ids
	 *            id数组
	 * @return 持久化对象列表
	 */
	@Override
	public List<POJO> load(ID[] ids) {
		List<POJO> entityList = new ArrayList<POJO>();
		for (int i = 0, len = ids.length; i < len; i++) {
			entityList.add(this.load(ids[i]));
		}
		return entityList;
	}

	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            唯一标识
	 * @param lock
	 *            是否锁定，使用LockOptions.UPGRADE
	 * @return 持久化对象
	 */
	@SuppressWarnings("unchecked")
	protected final POJO load(ID id, boolean lock) {
		POJO entity = null;
		if (lock) {
			entity = (POJO) getSession().load(getEntityClass(), id, LockOptions.UPGRADE);
		} else {
			entity = (POJO) getSession().load(getEntityClass(), id);
		}
		return entity;
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体对象
	 * @return 唯一标识
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ID save(POJO entity) {
		return (ID) this.getSession().save(entity);
	}

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	@Override
	public void update(POJO entity) {
		this.getSession().update(entity);
	}

	/**
	 * 根据实体执行保存或者更新
	 * 
	 * @param entity
	 *            实体
	 */
	@Override
	public void saveOrUpdate(POJO entity) {
		this.getSession().saveOrUpdate(entity);
	}

	/**
	 * 根据实体执行保存或者更新(带有合并功能，能够合并session中的持久化实体和游离态的实体)
	 * 
	 * @param entity
	 *            实体
	 * @return 返回合并后的实体(持久态)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public POJO merge(POJO entity) {
		return (POJO) this.getSession().merge(entity);
	}

	/**
	 * 根据提供的HQL来更新数据
	 * 
	 * @param hql
	 *            自定义的HQL，(注意：写实体后面的语法即可，因为内部已经补上"UPDATE "防止其他用途)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer updateByHQL(String hql, ParameterSetterCallback callback) {
		Query query = this.getSession().createQuery("UPDATE " + hql);
		callback.onSetter(query);
		return query.executeUpdate();
	}

	/**
	 * 根据提供的SQL来更新数据
	 * 
	 * @param sql
	 *            自定义的SQL，(注意：写实体后面的语法即可，因为内部已经补上"UPDATE "防止其他用途)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer updateBySQL(String sql, ParameterSetterCallback callback) {
		Query query = this.getSession().createSQLQuery("UPDATE " + sql);
		callback.onSetter(query);
		return query.executeUpdate();
	}

	/**
	 * 添加排序条件
	 * 
	 * @param criteria
	 *            查询对象
	 * @param orders
	 *            排序对象数组
	 */
	private void addOrderToCriteria(Criteria criteria, Order[] orders) {
		if (null != orders && 0 < orders.length) {
			for (int i = 0; i < orders.length; i++) {
				criteria.addOrder(orders[i]);
			}
		}
	}

	/**
	 * 添加组条件
	 * 
	 * @param criteria
	 *            查询对象
	 * @param groupPropertyNames
	 *            组属性名称的数组
	 * @return 返回生成的ProjectionList
	 */
	private ProjectionList addGroupToCriteria(Criteria criteria, String[] groupPropertyNames) {
		ProjectionList projectionList = null;
		if (null != groupPropertyNames && 0 < groupPropertyNames.length) {
			projectionList = Projections.projectionList();
			for (int i = 0; i < groupPropertyNames.length; i++) {
				projectionList.add(Projections.groupProperty(groupPropertyNames[i]));
			}
			criteria.setProjection(projectionList);
		}
		return projectionList;
	}

	/**
	 * 在当前session启用过滤器
	 * 
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 */
	private void enableFilter(Map<String, Map<String, Object>> filterMap) {
		Session hibernateSession = this.getSession();
		if (null != filterMap && 0 < filterMap.size()) {
			Iterator<String> filterNameIt = filterMap.keySet().iterator();
			while (filterNameIt.hasNext()) {
				String filterName = filterNameIt.next();// 获取过滤器名
				Map<String, Object> filterParamMap = filterMap.get(filterName);
				if (null != filterParamMap && 0 < filterParamMap.size()) {
					Filter hibernateFilter = hibernateSession.enableFilter(filterName);// 启动filter
					Iterator<String> paramNameIt = filterParamMap.keySet().iterator();
					while (paramNameIt.hasNext()) {
						String paramName = paramNameIt.next();// 参数名
						Object paramValue = filterParamMap.get(paramName);// 参数值
						if (paramValue.getClass().isArray()) {// 数组
							hibernateFilter.setParameterList(paramName, (Object[]) paramValue);
						} else if (Collection.class.isAssignableFrom(paramValue.getClass())) {// Collaction
							hibernateFilter.setParameterList(paramName, (Collection<?>) paramValue);
						} else {// 普通情况
							hibernateFilter.setParameter(paramName, paramValue);
						}
					}
				}
			}
		}
	}

	/**
	 * 查询所有的记录
	 * 
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listAll() {
		return this.listAll(this.getOrders());
	}

	/**
	 * 查询所有的记录,并排序
	 * 
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * 
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listAll(Order[] orders) {
		return this.listByCriteria(null, orders, null);
	}

	/**
	 * 查询所有的记录,并排序
	 * 
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)(注意:使用过滤器必须先在配置文件配置好)
	 * 
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * 
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listAll(Map<String, Map<String, Object>> filterMap, Order[] orders) {
		return this.listByCriteria(null, filterMap, null, orders, null);
	}

	/**
	 * 按属性列出对象列表
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByProperty(String property, Object value) {
		return this.listByProperty(property, value, null, this.getOrders(), null);
	}

	/**
	 * 按属性列出对象列表
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupFieldNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByProperty(String property, Object value, Map<String, Map<String, Object>> filterMap, Order[] orders, String[] groupPropertyNames) {
		return this.listByProperty(null, null, property, value, filterMap, orders, groupPropertyNames);
	}

	/**
	 * 按属性列出对象列表
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public IResultSet<POJO> listByProperty(Integer pageNumber, Integer pageSize, String property, Object value) {
		return this.listByProperty(pageNumber, pageSize, property, value, null, this.getOrders(), null);
	}

	/**
	 * 按属性列出对象列表
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IResultSet<POJO> listByProperty(Integer pageNumber, Integer pageSize, String property, Object value, Map<String, Map<String, Object>> filterMap, Order[] orders, String[] groupPropertyNames) {
		IResultSet<POJO> pager = null;
		this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
		Criteria criteria = createCriteria(new Criterion[] { Restrictions.eq(property, value) });
		this.addGroupToCriteria(criteria, groupPropertyNames);
		this.addOrderToCriteria(criteria, orders);
		if (null != pageNumber && null != pageSize) {
			Criteria criteriaCount = createCriteria(new Criterion[] { Restrictions.eq(property, value) });
			ProjectionList groupProjectionList = this.addGroupToCriteria(criteriaCount, groupPropertyNames);
			this.addOrderToCriteria(criteriaCount, orders);
			int totalResultCount = this.countByCriteria(criteriaCount, groupProjectionList);
			int totaPagesCount = calcTotalPagesCount(totalResultCount, pageSize);
			pageNumber = (0 > pageNumber ? 1 : pageNumber);// 控制在有效范围
			pageNumber = (pageNumber > totaPagesCount ? totaPagesCount : pageNumber);// 控制在有效范围
			criteria.setFirstResult(pageSize * (pageNumber - 1));
			criteria.setMaxResults(pageSize);
			List<POJO> resultList = criteria.list();
			pager = new Pagelet<POJO>(pageSize, pageNumber, totalResultCount, totaPagesCount, resultList);
		} else {
			List<POJO> resultList = criteria.list();
			pager = new Pagelet<POJO>(resultList.size(), resultList);
		}
		return pager;
	}

	/**
	 * 按Criterion集合条件列出记录
	 * 
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByPropertySet(Map<String, Object> criterionMap) {
		return this.listByPropertySet(criterionMap, null, this.getOrders(), null);
	}

	/**
	 * 按Criterion集合条件列出记录
	 * 
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByPropertySet(Map<String, Object> criterionMap, Map<String, Map<String, Object>> filterMap, Order[] orders, String[] groupPropertyNames) {
		return this.listByPropertySet(null, null, criterionMap, filterMap, orders, groupPropertyNames);
	}

	/**
	 * 按Criterion集合条件列出记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public IResultSet<POJO> listByPropertySet(Integer pageNumber, Integer pageSize, Map<String, Object> criterionMap) {
		return this.listByPropertySet(pageNumber, pageSize, criterionMap, null, this.getOrders(), null);
	}

	/**
	 * 按Criterion集合条件列出记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IResultSet<POJO> listByPropertySet(Integer pageNumber, Integer pageSize, Map<String, Object> criterionMap, Map<String, Map<String, Object>> filterMap, Order[] orders, String[] groupPropertyNames) {
		IResultSet<POJO> pager = null;
		Set<Criterion> criterionSet = new HashSet<Criterion>();
		Iterator<String> keyIt = criterionMap.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			Object origValue = criterionMap.get(key);
			criterionSet.add(Restrictions.eq(key, origValue));
		}
		this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
		Criteria criteria = createCriteria(criterionSet.toArray(new Criterion[0]));
		this.addGroupToCriteria(criteria, groupPropertyNames);
		this.addOrderToCriteria(criteria, orders);
		if (null != pageNumber && null != pageSize) {
			Criteria criteriaCount = createCriteria(criterionSet.toArray(new Criterion[0]));
			ProjectionList groupProjectionList = this.addGroupToCriteria(criteriaCount, groupPropertyNames);
			this.addOrderToCriteria(criteriaCount, orders);
			int totalResultCount = this.countByCriteria(criteriaCount, groupProjectionList);
			int totaPagesCount = calcTotalPagesCount(totalResultCount, pageSize);
			pageNumber = (0 > pageNumber ? 1 : pageNumber);// 控制在有效范围
			pageNumber = (pageNumber > totaPagesCount ? totaPagesCount : pageNumber);// 控制在有效范围
			criteria.setFirstResult(pageSize * (pageNumber - 1));
			criteria.setMaxResults(pageSize);
			List<POJO> resultList = criteria.list();
			pager = new Pagelet<POJO>(pageSize, pageNumber, totalResultCount, totaPagesCount, resultList);
		} else {
			List<POJO> resultList = criteria.list();
			pager = new Pagelet<POJO>(resultList.size(), resultList);
		}
		return pager;
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByCriteria(Criterion[] criterion) {
		return this.listByCriteria(criterion, "");
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param alias
	 *            当前实体别名
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByCriteria(Criterion[] criterion, String alias) {
		return this.listByCriteria(criterion, null, alias, this.getOrders(), null);
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByCriteria(Criterion[] criterion, Order[] orders, String[] groupPropertyNames) {
		return this.listByCriteria(criterion, null, null, orders, groupPropertyNames);
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param alias
	 *            当前实体别名
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByCriteria(Criterion[] criterion, Map<String, Map<String, Object>> filterMap, String alias, Order[] orders, String[] groupPropertyNames) {
		return this.listByCriteria(null, null, criterion, filterMap, alias, orders, groupPropertyNames);
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public IResultSet<POJO> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion) {
		return this.listByCriteria(pageNumber, pageSize, criterion, "");
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param alias
	 *            当前实体的别名
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public IResultSet<POJO> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, String alias) {
		return this.listByCriteria(pageNumber, pageSize, criterion, null, alias, this.getOrders(), null);
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public IResultSet<POJO> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Order[] orders, String[] groupPropertyNames) {
		return this.listByCriteria(pageNumber, pageSize, criterion, null, null, orders, groupPropertyNames);
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param alias
	 *            当前实体别名
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IResultSet<POJO> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Map<String, Map<String, Object>> filterMap, String alias, Order[] orders, String[] groupPropertyNames) {
		IResultSet<POJO> resultSet = null;
		IResultSet<?> scrPager = listByCriteria(pageNumber, pageSize, criterion, null, filterMap, alias, orders, groupPropertyNames, true);
		List<?> entityList = scrPager.getResultList();
		if (null != pageNumber && null != pageSize) {
			resultSet = new Pagelet<POJO>(pageSize, pageNumber, scrPager.getTotalResultCount(), scrPager.getTotalPageCount(), (List<POJO>) entityList);
		} else {
			resultSet = new Pagelet<POJO>(entityList.size(), (List<POJO>) entityList);
		}
		return resultSet;
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param alias
	 *            当前实体别名
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends TransformBaseDTO> IResultSet<T> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Class<T> transformDTOClass, Map<String, Map<String, Object>> filterMap, String alias, Order[] orders, String[] groupPropertyNames) {
		IResultSet<T> resultSet = null;
		IResultSet<?> scrPager = listByCriteria(pageNumber, pageSize, criterion, transformDTOClass, filterMap, alias, orders, groupPropertyNames, true);
		List<?> entityList = scrPager.getResultList();
		if (null != pageNumber && null != pageSize) {
			resultSet = new Pagelet<T>(pageSize, pageNumber, scrPager.getTotalResultCount(), scrPager.getTotalPageCount(), (List<T>) entityList);
		} else {
			resultSet = new Pagelet<T>(entityList.size(), (List<T>) entityList);
		}
		return resultSet;
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param flag
	 *            纯粹是用来区分开两个参数一样的方法标识
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IResultSet<?> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Class<? extends TransformBaseDTO> transformDTOClass, Boolean flag) {
		IResultSet<Object> resultSet = null;
		IResultSet<?> scrPager = listByCriteria(pageNumber, pageSize, criterion, transformDTOClass, null, null, this.getOrders(), null, true);
		List<?> entityList = scrPager.getResultList();
		if (null != pageNumber && null != pageSize) {
			resultSet = new Pagelet<Object>(pageSize, pageNumber, scrPager.getTotalResultCount(), scrPager.getTotalPageCount(), (List<Object>) entityList);
		} else {
			resultSet = new Pagelet<Object>(entityList.size(), (List<Object>) entityList);
		}
		return resultSet;
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends TransformBaseDTO> IResultSet<T> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Class<T> transformDTOClass) {
		IResultSet<T> resultSet = null;
		IResultSet<?> scrPager = listByCriteria(pageNumber, pageSize, criterion, transformDTOClass, null, null, this.getOrders(), null, true);
		List<?> entityList = scrPager.getResultList();
		if (null != pageNumber && null != pageSize) {
			resultSet = new Pagelet<T>(pageSize, pageNumber, scrPager.getTotalResultCount(), scrPager.getTotalPageCount(), (List<T>) entityList);
		} else {
			resultSet = new Pagelet<T>(entityList.size(), (List<T>) entityList);
		}
		return resultSet;
	}

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param alias
	 *            当前实体别名
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @param flag
	 *            纯粹是用来区分开两个参数一样的方法标识
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IResultSet<?> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Class<? extends TransformBaseDTO> transformDTOClass, Map<String, Map<String, Object>> filterMap, String alias, Order[] orders, String[] groupPropertyNames, Boolean flag) {
		IResultSet<?> pager = null;
		this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
		Criteria criteria = createCriteria(criterion, alias);// 别名
		this.addGroupToCriteria(criteria, groupPropertyNames);
		this.addOrderToCriteria(criteria, orders);
		if (null != transformDTOClass) {
			ColumnToDTOTransformer columnToDTOTransformer = null;
			if (ArrayUtils.isNotEmpty(groupPropertyNames)) {
				columnToDTOTransformer = new ColumnToDTOTransformer(transformDTOClass, groupPropertyNames);
			} else {
				columnToDTOTransformer = new ColumnToDTOTransformer(transformDTOClass);
			}
			columnToDTOTransformer.setIsCriteria(ArrayUtils.isEmpty(groupPropertyNames));
			criteria.setResultTransformer(columnToDTOTransformer);
		}
		if (null != pageNumber && null != pageSize) {
			Criteria criteriaCount = createCriteria(criterion, alias);// 别名
			ProjectionList groupProjectionList = this.addGroupToCriteria(criteriaCount, groupPropertyNames);
			this.addOrderToCriteria(criteriaCount, orders);
			int totalResultCount = this.countByCriteria(criteriaCount, groupProjectionList);
			int totaPagesCount = calcTotalPagesCount(totalResultCount, pageSize);
			pageNumber = (0 > pageNumber ? 1 : pageNumber);// 控制在有效范围
			pageNumber = (pageNumber > totaPagesCount ? totaPagesCount : pageNumber);// 控制在有效范围
			criteria.setFirstResult(pageSize * (pageNumber - 1));
			criteria.setMaxResults(pageSize);
			List<Object> resultList = criteria.list();
			pager = new Pagelet<Object>(pageSize, pageNumber, totalResultCount, totaPagesCount, resultList);
		} else {
			List<Object> resultList = criteria.list();
			pager = new Pagelet<Object>(resultList.size(), resultList);
		}
		return pager;
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param hql
	 *            自定义的HQL
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByHQL(String hql, ParameterSetterCallback callback) {
		Map<String, Map<String, Object>> filterMap = null;
		return this.listByHQL(null, null, hql, filterMap, callback);
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param hql
	 *            自定义的HQL
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回查询到的实体
	 */
	@Override
	public IResultSet<POJO> listByHQL(String hql, Map<String, Map<String, Object>> filterMap, ParameterSetterCallback callback) {
		return this.listByHQL(null, null, hql, filterMap, callback);
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param hql
	 *            自定义的HQL
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public <T extends TransformBaseDTO> IResultSet<T> listByHQL(String hql, Class<T> transformDTOClass, ParameterSetterCallback callback) {
		return listByHQL(null, null, hql, null, transformDTOClass, callback);
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param hql
	 *            自定义的HQL
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @param flag
	 *            纯粹是用来区分开两个参数一样的方法标识
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public IResultSet<?> listByHQL(Integer pageNumber, Integer pageSize, String hql, Class<? extends TransformBaseDTO> transformDTOClass, ParameterSetterCallback callback, Boolean flag) {
		return listByHQL(pageNumber, pageSize, hql, null, transformDTOClass, callback, true);
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param hql
	 *            自定义的HQL
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public <T extends TransformBaseDTO> IResultSet<T> listByHQL(Integer pageNumber, Integer pageSize, String hql, Class<T> transformDTOClass, ParameterSetterCallback callback) {
		return listByHQL(pageNumber, pageSize, hql, null, transformDTOClass, callback);
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param hql
	 *            自定义的HQL
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @param flag
	 *            纯粹是用来区分开两个参数一样的方法标识
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	public IResultSet<?> listByHQL(Integer pageNumber, Integer pageSize, String hql, Map<String, Map<String, Object>> filterMap, ParameterSetterCallback callback, Boolean flag) {
		return listByHQL(pageNumber, pageSize, hql, filterMap, null, callback, true);
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param hql
	 *            自定义的HQL
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends TransformBaseDTO> IResultSet<T> listByHQL(Integer pageNumber, Integer pageSize, String hql, Map<String, Map<String, Object>> filterMap, Class<T> transformDTOClass, ParameterSetterCallback callback) {
		IResultSet<T> resultSet = null;
		IResultSet<?> scrPager = listByHQL(pageNumber, pageSize, hql, filterMap, transformDTOClass, callback, true);
		List<?> entityList = scrPager.getResultList();
		if (null != pageNumber && null != pageSize) {
			resultSet = new Pagelet<T>(pageSize, pageNumber, scrPager.getTotalResultCount(), scrPager.getTotalPageCount(), (List<T>) scrPager.getResultList());
		} else {
			resultSet = new Pagelet<T>(entityList.size(), (List<T>) entityList);
		}
		return resultSet;
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param hql
	 *            自定义的HQL
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IResultSet<POJO> listByHQL(Integer pageNumber, Integer pageSize, String hql, Map<String, Map<String, Object>> filterMap, ParameterSetterCallback callback) {
		IResultSet<POJO> resultSet = null;
		IResultSet<?> scrPager = listByHQL(pageNumber, pageSize, hql, filterMap, null, callback, true);
		List<?> entityList = scrPager.getResultList();
		if (null != pageNumber && null != pageSize) {
			resultSet = new Pagelet<POJO>(pageSize, pageNumber, scrPager.getTotalResultCount(), scrPager.getTotalPageCount(), (List<POJO>) entityList);
		} else {
			resultSet = new Pagelet<POJO>(entityList.size(), (List<POJO>) entityList);
		}
		return resultSet;
	}

	/**
	 * 根据HQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param hql
	 *            自定义的HQL
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @param flag
	 *            纯粹是用来区分开两个参数一样的方法标识
	 * 
	 * @return 返回封装了查询到的实体的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IResultSet<?> listByHQL(Integer pageNumber, Integer pageSize, String hql, Map<String, Map<String, Object>> filterMap, Class<? extends TransformBaseDTO> transformDTOClass, ParameterSetterCallback callback, Boolean flag) {
		IResultSet<?> pager = null;
		this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
		Query query = this.getSession().createQuery(hql);
		query.setCacheable(this.getUseCache());
		if (null != transformDTOClass) {
			query.setResultTransformer(new ColumnToDTOTransformer(transformDTOClass));
		}
		callback.onSetter(query);
		if (null != pageNumber && null != pageSize) {
			int totalResultCount = this.countByHQLQuery(query, callback);
			int totaPagesCount = calcTotalPagesCount(totalResultCount, pageSize);
			pageNumber = (0 > pageNumber ? 1 : pageNumber);// 控制在有效范围
			pageNumber = (pageNumber > totaPagesCount ? totaPagesCount : pageNumber);// 控制在有效范围
			query.setFirstResult(pageSize * (pageNumber - 1));
			query.setMaxResults(pageSize);
			List<Object> resultList = query.list();
			pager = new Pagelet<Object>(pageSize, pageNumber, totalResultCount, totaPagesCount, resultList);
		} else {
			List<Object> resultList = query.list();
			pager = new Pagelet<Object>(resultList.size(), resultList);
		}
		return pager;
	}

	/**
	 * 根据SQL查询记录
	 * 
	 * @param sql
	 *            自定义的SQL
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * @param flag
	 *            纯粹是用来区分开两个参数一样的方法标识
	 * 
	 * @return 返回查询到的记录
	 */
	@Override
	public IResultSet<?> listBySQL(String sql, ParameterSetterCallback callback) {
		return this.listBySQL(null, null, sql, null, callback, true);
	}

	/**
	 * 根据SQL查询记录
	 * 
	 * @param sql
	 *            自定义的SQL
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * 
	 * @return 返回查询到的记录
	 */
	@Override
	public <T extends TransformBaseDTO> IResultSet<T> listBySQL(String sql, Class<T> transformDTOClass, ParameterSetterCallback callback) {
		return this.listBySQL(null, null, sql, transformDTOClass, callback);
	}

	/**
	 * 根据SQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param sql
	 *            自定义的SQL
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * @param flag
	 *            纯粹是用来区分开两个参数一样的方法标识
	 * 
	 * @return 返回封装了查询到的记录的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public IResultSet<?> listBySQL(Integer pageNumber, Integer pageSize, String sql, Class<? extends TransformBaseDTO> transformDTOClass, ParameterSetterCallback callback, Boolean flag) {
		IResultSet<?> pager = null;
		SQLQuery query = this.getSession().createSQLQuery(sql);
		query.setCacheable(this.getUseCache());
		if (null != transformDTOClass) {
			query.setResultTransformer(new ColumnToDTOTransformer(transformDTOClass));
		}
		query.setCacheable(false);// FIXME 在使用原生SQL查询时候,使用二级缓存会出现问题::Hibernate Bug :NamedNativeQuerys are not cacheable ; https://hibernate.atlassian.net/browse/HHH-9111
		callback.onSetter(query);
		if (null != pageNumber && null != pageSize) {
			int totalResultCount = this.countBySQLQuery(query, callback);
			int totaPagesCount = calcTotalPagesCount(totalResultCount, pageSize);
			pageNumber = (0 > pageNumber ? 1 : pageNumber);// 控制在有效范围
			pageNumber = (pageNumber > totaPagesCount ? totaPagesCount : pageNumber);// 控制在有效范围
			query.setFirstResult(pageSize * (pageNumber - 1));
			query.setMaxResults(pageSize);
			List<Object> resultList = query.list();
			pager = new Pagelet<Object>(pageSize, pageNumber, totalResultCount, totaPagesCount, resultList);
		} else {
			List<Object> resultList = query.list();
			pager = new Pagelet<Object>(resultList.size(), resultList);
		}
		return pager;
	}

	/**
	 * 根据SQL查询记录
	 * 
	 * @param pageNumber
	 *            页码，大于0
	 * @param pageSize
	 *            页面大小，大于0
	 * @param sql
	 *            自定义的SQL
	 * @param transformDTOClass
	 *            用于查询时候将单独的行转成指定的DTO对象的类类型
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * 
	 * @return 返回封装了查询到的记录的集合对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends TransformBaseDTO> IResultSet<T> listBySQL(Integer pageNumber, Integer pageSize, String sql, Class<T> transformDTOClass, ParameterSetterCallback callback) {
		IResultSet<T> resultSet = null;
		IResultSet<?> scrPager = listBySQL(pageNumber, pageSize, sql, transformDTOClass, callback, true);
		List<?> entityList = scrPager.getResultList();
		if (null != pageNumber && null != pageSize) {
			resultSet = new Pagelet<T>(pageSize, pageNumber, scrPager.getTotalResultCount(), scrPager.getTotalPageCount(), (List<T>) entityList);
		} else {
			resultSet = new Pagelet<T>(entityList.size(), (List<T>) entityList);
		}
		return resultSet;
	}

	/**
	 * 按属性获取唯一对象
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 返回唯一的实体
	 */
	@Override
	public POJO getUniqueByProperty(String property, Object value) {
		return this.getUniqueByProperty(property, value, null, null);
	}

	/**
	 * 按属性获取唯一对象
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 返回唯一的实体
	 */
	@Override
	public POJO getUniqueByProperty(String property, Object value, String[] groupPropertyNames) {
		return this.getUniqueByProperty(property, value, groupPropertyNames, null, null);
	}

	/**
	 * 按属性获取唯一对象
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param additionCriterions
	 *            附加的查询对象
	 * @return 返回唯一的实体
	 */
	@Override
	public POJO getUniqueByProperty(String property, Object value, Criterion[] additionCriterions) {
		return this.getUniqueByProperty(property, value, additionCriterions, null);
	}

	/**
	 * 按属性获取唯一对象
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param additionCriterions
	 *            附加的查询对象
	 * @param alias
	 *            别名
	 * @return 返回唯一的实体
	 */
	@Override
	public POJO getUniqueByProperty(String property, Object value, Criterion[] additionCriterions, String alias) {
		return this.getUniqueByProperty(property, value, null, additionCriterions, alias);
	}

	/**
	 * 按属性获取唯一对象
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @param additionCriterions
	 *            附加的查询对象
	 * @param alias
	 *            别名
	 * @return 返回唯一的实体
	 */
	@Override
	public POJO getUniqueByProperty(String property, Object value, String[] groupPropertyNames, Criterion[] additionCriterions, String alias) {
		return this.getUniqueByProperty(property, value, null, groupPropertyNames, additionCriterions, alias);
	}

	/**
	 * 按属性获取唯一对象
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @param additionCriterions
	 *            附加的查询对象
	 * @param alias
	 *            别名
	 * @return 返回唯一的实体
	 */
	@Override
	@SuppressWarnings("unchecked")
	public POJO getUniqueByProperty(String property, Object value, Map<String, Map<String, Object>> filterMap, String[] groupPropertyNames, Criterion[] additionCriterions, String alias) {
		this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
		List<Criterion> additionCriterionList = new ArrayList<Criterion>();
		if (ArrayUtils.isNotEmpty(additionCriterions)) {
			additionCriterionList.addAll(Arrays.asList(additionCriterions));
		}
		additionCriterionList.add(Restrictions.eq(property, value));
		Criteria criteria = createCriteria(additionCriterionList.toArray(new Criterion[0]), alias);
		this.addGroupToCriteria(criteria, groupPropertyNames);
		return (POJO) criteria.uniqueResult();
	}

	/**
	 * 创建默认的Criteria,后续可进行更多处理,辅助函数.
	 * 
	 * @return 返回创建好的Criteria对象(无别名)
	 */
	protected final Criteria createCriteria() {
		return this.createCriteria(null);
	}

	/**
	 * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
	 * 
	 * @param criterions
	 *            多个条件
	 * @return 返回创建好的Criteria对象(无别名)
	 */
	protected final Criteria createCriteria(Criterion[] criterions) {
		return this.createCriteria(criterions, null);
	}

	/**
	 * 根据Criterion条件创建Criteria,后续可进行更多处理,辅助函数.
	 * 
	 * @param criterions
	 *            多个条件
	 * @param alias
	 *            指定别名
	 * @return 返回创建好的Criteria对象
	 */
	protected final Criteria createCriteria(Criterion[] criterions, String alias) {
		Criteria criteria = (StringUtils.isNotBlank(alias) ? getSession().createCriteria(getEntityClass(), alias) : getSession().createCriteria(getEntityClass()));
		criteria.setCacheable(this.getUseCache());
		this.createAlias(getEntityClass(), criteria);
		if (null != criterions) {
			for (int i = 0; i < criterions.length; i++) {
				criteria.add(criterions[i]);
			}
		}
		return criteria;
	}

	/**
	 * 创建一个DetachedCriteria查询对象，默认要查询的类型是当前支持实体
	 * 
	 * @return DetachedCriteria查询对象
	 */
	@Override
	public DetachedCriteria createDetachedCriteria() {
		return this.createDetachedCriteria(this.getEntityClass());
	}

	/**
	 * 创建一个DetachedCriteria查询对象
	 * 
	 * @param entityClz
	 *            要针对创建的实体类类对象
	 * @return DetachedCriteria查询对象
	 * */
	@Override
	public DetachedCriteria createDetachedCriteria(Class<?> entityClz) {
		return this.createDetachedCriteria(entityClz, null);
	}

	/**
	 * 创建一个DetachedCriteria查询对象
	 * 
	 * @param entityClz
	 *            要针对创建的实体类类对象
	 * @param alias
	 *            实体的别名
	 * @return DetachedCriteria查询对象
	 * */
	@Override
	public DetachedCriteria createDetachedCriteria(Class<?> entityClz, String alias) {
		return this.createDetachedCriteria(entityClz, null, alias);
	}

	/**
	 * 创建一个DetachedCriteria查询对象
	 * 
	 * @param entityClz
	 *            要针对创建的实体类类对象
	 * @param criterions
	 *            多个条件
	 * @param alias
	 *            实体的别名
	 * @return DetachedCriteria查询对象
	 * */
	@Override
	public DetachedCriteria createDetachedCriteria(Class<?> entityClz, Criterion[] criterions, String alias) {
		DetachedCriteria detachedCriteria = StringUtils.isNotBlank(alias) ? DetachedCriteria.forClass(entityClz, alias) : DetachedCriteria.forClass(entityClz);
		this.createAlias(entityClz, detachedCriteria);
		if (null != criterions) {
			for (int i = 0; i < criterions.length; i++) {
				detachedCriteria.add(criterions[i]);
			}
		}
		return detachedCriteria;
	}

	/**
	 * 为指定的DetachedCriteria创建别名，使Hibernate多级插叙生效
	 * 
	 * @param entityClz
	 *            指定的实体
	 * @param detachedCriteria
	 *            离线查询对象
	 * @param criteria
	 *            一般的查询对象
	 */
	private void createAlias(Class<?> entityClz, DetachedCriteria detachedCriteria) {
		this.createAlias(entityClz, detachedCriteria, null);
	}

	/**
	 * 为指定的Criteria创建别名，使Hibernate多级插叙生效
	 * 
	 * @param entityClz
	 *            指定的实体
	 * @param detachedCriteria
	 *            离线查询对象
	 * @param criteria
	 *            一般的查询对象
	 */
	private void createAlias(Class<?> entityClz, Criteria criteria) {
		this.createAlias(entityClz, null, criteria);
	}

	/**
	 * 为指定的DetachedCriteria或者Criteria创建别名，使Hibernate多级插叙生效(DetachedCriteria和Criteria只能指定一个非空，否则是DetachedCriteria)
	 * 
	 * @param entityClz
	 *            指定的实体
	 * @param detachedCriteria
	 *            离线查询对象
	 * @param criteria
	 *            一般的查询对象
	 */
	private void createAlias(Class<?> entityClz, DetachedCriteria detachedCriteria, Criteria criteria) {
		Field[] fields = AfxBeanUtils.getAllFields(entityClz);
		for (int index = 0; index < fields.length; index++) {
			Field field = fields[index];
			Class<?> fieldClass = field.getType();
			if (!fieldClass.isPrimitive() && !fieldClass.isArray()) {
				String pageName = fieldClass.getPackage().getName();
				if (CommonConstants.MODEL_PAGECKAGE_NAME.equals(pageName) || BaseModel.class.isAssignableFrom(fieldClass)) {
					ModelAliasLink modelAlias = field.getAnnotation(ModelAliasLink.class);
					if (null != modelAlias) {
						String thisAlias = modelAlias.thisAlias();
						if (StringUtils.isNotBlank(thisAlias)) {
							String aliasName = thisAlias;
							if (null != detachedCriteria) {
								detachedCriteria.createAlias(field.getName(), aliasName);
							} else {
								criteria.createAlias(field.getName(), aliasName);
							}
							Alias[] allAlias = modelAlias.alias();
							if (0 < allAlias.length) {
								for (int i = 0; i < allAlias.length; i++) {
									Alias nowAlias = allAlias[i];
									if (null != detachedCriteria) {
										detachedCriteria.createAlias(nowAlias.item(), nowAlias.alias());
									} else {
										criteria.createAlias(nowAlias.item(), nowAlias.alias());
									}
								}
							}
						} else {
							String aliasName = field.getName();
							if (null != detachedCriteria) {
								detachedCriteria.createAlias(aliasName, aliasName);
							} else {
								criteria.createAlias(aliasName, aliasName);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 按criteria统计记录数
	 * 
	 * @param criteria
	 *            指定的criteria
	 * @param projectionList
	 *            指定的projectionList
	 * @return 记录数
	 */
	protected final int countByCriteria(Criteria criteria, ProjectionList projectionList) {
		if (null == projectionList) {
			return ((Number) (criteria.setProjection(Projections.rowCount()).uniqueResult())).intValue();
		} else {// 在有group by时候特殊处理
			List<?> countList = criteria.list();
			return (CollectionUtils.isEmpty(countList) ? 0 : countList.size());
		}
	}

	/**
	 * 按criterions统计记录数
	 * 
	 * @param criterions
	 *            指定的criterions数组
	 * @return 记录数
	 */
	@Override
	public Integer countByCriteria(Criterion[] criterions) {
		return this.countByCriteria(criterions, null);
	}

	/**
	 * 按criterions统计记录数
	 * 
	 * @param criterions
	 *            指定的criterions数组
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 记录数
	 */
	@Override
	public Integer countByCriteria(Criterion[] criterions, String[] groupPropertyNames) {
		return this.countByCriteria(criterions, null, groupPropertyNames);
	}

	/**
	 * 按criterions统计记录数
	 * 
	 * @param criterions
	 *            指定的criterions数组
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 记录数
	 */
	@Override
	public Integer countByCriteria(Criterion[] criterions, Map<String, Map<String, Object>> filterMap, String[] groupPropertyNames) {
		this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
		Criteria criteria = createCriteria(criterions);
		ProjectionList groupProjectionList = this.addGroupToCriteria(criteria, groupPropertyNames);
		return this.countByCriteria(criteria, groupProjectionList);
	}

	/**
	 * 按指定的HQL语句统计记录数
	 * 
	 * @param hql
	 *            hql查询语句
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 记录数
	 */
	@Override
	public Integer countByHQLQuery(String hql, final ParameterSetterCallback callback) {
		return this.countByHQLQuery(hql, null, callback);
	}

	/**
	 * 按指定的HQL语句统计记录数
	 * 
	 * @param hql
	 *            hql查询语句
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 记录数
	 */
	@Override
	public Integer countByHQLQuery(String hql, Map<String, Map<String, Object>> filterMap, final ParameterSetterCallback callback) {
		if (StringUtils.containsIgnoreCase(hql, "GROUP BY")) {
			this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
			Query queryCount = this.getSession().createQuery(hql);
			callback.onSetter(queryCount);
			List<?> countList = queryCount.list();
			return (CollectionUtils.isEmpty(countList) ? 0 : countList.size());
		} else {
			String countHQL = "SELECT COUNT(*) " + hql;
			this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
			Query queryCount = this.getSession().createQuery(countHQL);
			callback.onSetter(queryCount);
			Number number = (Number) queryCount.uniqueResult();
			return number.intValue();
		}
	}

	/**
	 * 按指定的SQL语句统计记录数
	 * 
	 * @param sql
	 *            sql查询语句
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * @return 记录数
	 */
	@Override
	public Integer countBySQLQuery(String sql, final ParameterSetterCallback callback) {
		String countSQL = "SELECT COUNT(*) FROM (" + sql + ") _T";
		Query queryCount = this.getSession().createSQLQuery(countSQL);
		callback.onSetter(queryCount);
		Number number = (Number) queryCount.uniqueResult();
		return number.intValue();
	}

	/**
	 * 按存在的query统计记录数
	 * 
	 * @param query
	 *            指定存在的query
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 记录数
	 */
	protected final Integer countByHQLQuery(final Query query, final ParameterSetterCallback callback) {
		String hql = query.getQueryString();
		if (StringUtils.containsIgnoreCase(hql, "GROUP BY")) {
			Query queryCount = this.getSession().createQuery(hql);
			callback.onSetter(queryCount);
			List<?> countList = queryCount.list();
			return (CollectionUtils.isEmpty(countList) ? 0 : countList.size());
		} else {
			String countHQL = "SELECT COUNT(*) " + hql;
			Query queryCount = this.getSession().createQuery(countHQL);
			callback.onSetter(queryCount);
			Number number = (Number) queryCount.uniqueResult();
			return number.intValue();
		}
	}

	/**
	 * 按存在的sql query统计记录数
	 * 
	 * @param query
	 *            指定存在的sql query
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * @return 记录数
	 */
	protected final Integer countBySQLQuery(final SQLQuery query, final ParameterSetterCallback callback) {
		String sql = query.getQueryString();
		String countSQL = "SELECT COUNT(*) FROM (" + sql + ") _T";
		Query queryCount = this.getSession().createSQLQuery(countSQL);
		callback.onSetter(queryCount);
		Number number = (Number) queryCount.uniqueResult();
		return number.intValue();
	}

	/**
	 * 根据id删除记录
	 * 
	 * @param id
	 *            唯一标识
	 * @return 返回受影响的行数
	 */
	@Override
	public Integer deleteById(ID id) {
		this.deleteByEntity(this.get(id));
		return 1;
	}

	/**
	 * 根据多个id删除记录
	 * 
	 * @param ids
	 *            多个唯一标识
	 * @return 返回受影响的行数
	 */
	@Override
	public Integer deleteByIds(ID[] ids) {
		int count = 0;
		for (int i = 0; i < ids.length; i++) {
			count += (this.deleteById(ids[i]));
		}
		return count;
	}

	/**
	 * 根据实体删除记录
	 * 
	 * @param entity
	 *            实体
	 */
	@Override
	public void deleteByEntity(POJO entity) {
		this.getSession().delete(entity);
	}

	/**
	 * 根据多个实体删除记录
	 * 
	 * @param entitys
	 *            多个实体
	 */
	@Override
	public void deleteByEntitys(POJO[] entitys) {
		for (int i = 0; i < entitys.length; i++) {
			this.deleteByEntity(entitys[i]);
		}
	}

	/**
	 * 根据属性值删除记录
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByProperty(String property, Object value) {
		return this.deleteByProperty(property, value, null);
	}

	/**
	 * 根据属性值删除记录
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByProperty(String property, Object value, Map<String, Map<String, Object>> filterMap) {
		List<POJO> deleteEntityList = this.listByProperty(property, value, filterMap, null, null).getResultList();
		for (int i = 0; i < deleteEntityList.size(); i++) {
			POJO entity = deleteEntityList.get(i);
			this.deleteByEntity(entity);
		}
		return deleteEntityList.size();
	}

	/**
	 * 根据属性集合删除记录
	 * 
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByPropertySet(Map<String, Object> criterionMap) {
		return this.deleteByPropertySet(criterionMap, null);
	}

	/**
	 * 根据属性集合删除记录
	 * 
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByPropertySet(Map<String, Object> criterionMap, Map<String, Map<String, Object>> filterMap) {
		List<POJO> deleteEntityList = this.listByPropertySet(criterionMap, filterMap, null, null).getResultList();
		for (int i = 0; i < deleteEntityList.size(); i++) {
			POJO entity = deleteEntityList.get(i);
			this.deleteByEntity(entity);
		}
		return deleteEntityList.size();
	}

	/**
	 * 根据过滤条件删除记录
	 * 
	 * @param criterions
	 *            过滤条件数组
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByCriteria(Criterion[] criterions) {
		return this.deleteByCriteria(criterions, null);
	}

	/**
	 * 根据过滤条件删除记录
	 * 
	 * @param criterions
	 *            过滤条件数组
	 * @param alias
	 *            查询中包括的主别名
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByCriteria(Criterion[] criterions, String alias) {
		return this.deleteByCriteria(criterions, null, alias);
	}

	/**
	 * 根据过滤条件删除记录
	 * 
	 * @param criterions
	 *            过滤条件数组
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param alias
	 *            查询中包括的主别名
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByCriteria(Criterion[] criterions, Map<String, Map<String, Object>> filterMap, String alias) {
		List<POJO> deleteEntityList = this.listByCriteria(criterions, filterMap, alias, null, null).getResultList();
		for (int i = 0; i < deleteEntityList.size(); i++) {
			POJO entity = deleteEntityList.get(i);
			this.deleteByEntity(entity);
		}
		return deleteEntityList.size();
	}

	/**
	 * 根据提供的HQL来删除数据
	 * 
	 * @param hql
	 *            自定义的HQL，(注意：写实体后面的语法即可，因为内部已经补上"DELETE FROM"防止其他用途)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByHQL(String hql, ParameterSetterCallback callback) {
		return this.deleteByHQL(hql, null, callback);
	}

	/**
	 * 根据提供的HQL来删除数据
	 * 
	 * @param hql
	 *            自定义的HQL，(注意：写实体后面的语法即可，因为内部已经补上"DELETE FROM"防止其他用途)
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteByHQL(String hql, Map<String, Map<String, Object>> filterMap, ParameterSetterCallback callback) {
		this.enableFilter(filterMap);// 启动filter,当filterMap为空时候不会启动
		Query query = this.getSession().createQuery("DELETE FROM " + hql);
		callback.onSetter(query);
		return query.executeUpdate();
	}

	/**
	 * 根据提供的SQL来删除数据
	 * 
	 * @param sql
	 *            自定义的SQL，(注意：写实体后面的语法即可，因为内部已经补上"DELETE FROM"防止其他用途)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	@Override
	public Integer deleteBySQL(String sql, ParameterSetterCallback callback) {
		Query query = this.getSession().createSQLQuery("DELETE FROM " + sql);
		callback.onSetter(query);
		return query.executeUpdate();
	}

	/** 强制清除缓存 */
	@Override
	public final void clearCache() {
		getSession().clear();
	}

	/** 强制进行从内存到数据库的同步 */
	@Override
	public final void flushCache() {
		getSession().flush();
	}

	/**
	 * 把指定的缓冲对象进行清除
	 * 
	 * @param entity
	 *            指定对象
	 */
	@Override
	public final void evictCache(POJO entity) {
		getSession().evict(entity);
	}

	/**
	 * 计算总分页数
	 * 
	 * @param totalCount
	 *            条目总数
	 * @param pageSize
	 *            每页大小
	 * @return 总分页数
	 */
	private Integer calcTotalPagesCount(Integer totalCount, Integer pageSize) {
		int litlePageCount = (totalCount % pageSize);
		int mainPageCount = (totalCount / pageSize);
		return (litlePageCount == 0 ? mainPageCount : mainPageCount + 1);
	}

	/**
	 * 获得Dao对应的默认实体类的类类型,此为对外的统一接口
	 * 
	 * @return 实体类的类类型
	 */
	@Override
	public final Class<POJO> getDefaultEntityClass() {
		return this.getEntityClass();
	}

	/**
	 * 获得Dao对应的默认排序对象,此为对外的统一接口
	 * 
	 * @return 排序对象数组
	 */
	@Override
	public final Order[] getDefaultOrders() {
		return this.getOrders();
	}

	/**
	 * 获得Dao对应的默认排序对象
	 * 
	 * @return 排序对象数组
	 */
	protected Order[] getOrders() {
		return new Order[] { Order.desc("createDate"), Order.desc("id") };
	}

	/**
	 * 获得Dao对应的实体类
	 * 
	 * @return 实体类类型
	 */
	protected abstract Class<POJO> getEntityClass();

	/** 具体实现分页的集合对象 */
	protected class Pagelet<U> implements IResultSet<U> {

		protected int pageSize = 0;

		protected int pageNumber = 0;

		protected int totalResultCount = 0;

		protected int totalPageCount = 0;

		protected List<U> resultList = null;

		protected boolean isPage = false;

		public Pagelet(int pageSize, int pageNumber, int totalResultCount, int totalPageCount, List<U> resultList) {
			super();
			this.pageSize = pageSize;
			this.pageNumber = pageNumber;
			this.totalResultCount = totalResultCount;
			this.totalPageCount = totalPageCount;
			this.resultList = resultList;
			this.isPage = true;
		}

		public Pagelet(int totalResultCount, List<U> resultList) {
			super();
			this.totalResultCount = totalResultCount;
			this.resultList = resultList;
			this.isPage = false;
		}

		@Override
		public int getPageSize() {
			return pageSize;
		}

		@Override
		public int getPageNumber() {
			return pageNumber;
		}

		@Override
		public int getTotalResultCount() {
			return totalResultCount;
		}

		@Override
		public int getTotalPageCount() {
			return totalPageCount;
		}

		@Override
		public List<U> getResultList() {
			return resultList;
		}

		@Override
		public boolean isPage() {
			return isPage;
		}
	}
}