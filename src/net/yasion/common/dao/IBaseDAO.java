package net.yasion.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import net.yasion.common.dto.TransformBaseDTO;
import net.yasion.common.model.BaseModel;
import net.yasion.common.support.common.dao.callback.ParameterSetterCallback;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public interface IBaseDAO<POJO extends BaseModel<ID>, ID extends Serializable> {

	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            唯一标识
	 * @return 持久化对象
	 */
	public POJO get(ID id);

	/**
	 * 根据id数组获取多个实体
	 * 
	 * @param ids
	 *            id数组
	 * @return 持久化对象列表
	 */
	public List<POJO> get(ID[] ids);

	/**
	 * 根据id获取实体
	 * 
	 * @param id
	 *            唯一标识
	 * @return 持久化对象
	 */
	public POJO load(ID id);

	/**
	 * 根据id数组获取多个实体
	 * 
	 * @param ids
	 *            id数组
	 * @return 持久化对象列表
	 */
	public List<POJO> load(ID[] ids);

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体对象
	 * @return 唯一标识
	 */
	public ID save(POJO entity);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void update(POJO entity);

	/**
	 * 根据实体执行保存或者更新
	 * 
	 * @param entity
	 *            实体
	 */
	public void saveOrUpdate(POJO entity);

	/**
	 * 根据实体执行保存或者更新(带有合并功能，能够合并session中的持久化实体和游离态的实体)
	 * 
	 * @param entity
	 *            实体
	 * @return 返回合并后的实体(持久态)
	 */
	public POJO merge(POJO entity);

	/**
	 * 根据提供的HQL来更新数据
	 * 
	 * @param hql
	 *            自定义的HQL，(注意：写实体后面的语法即可，因为内部已经补上"UPDATE "防止其他用途)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	public Integer updateByHQL(String hql, ParameterSetterCallback callback);

	/**
	 * 根据提供的SQL来更新数据
	 * 
	 * @param sql
	 *            自定义的SQL，(注意：写实体后面的语法即可，因为内部已经补上"UPDATE "防止其他用途)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	public Integer updateBySQL(String sql, ParameterSetterCallback callback);

	/**
	 * 查询所有的记录
	 * 
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listAll();

	/**
	 * 查询所有的记录,并排序
	 * 
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * 
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listAll(Order[] orders);

	/**
	 * 查询所有的记录,并排序
	 * 
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * 
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * 
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listAll(Map<String, Map<String, Object>> filterMap, Order[] orders);

	/**
	 * 按属性列出对象列表
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listByProperty(String property, Object value);

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
	public IResultSet<POJO> listByProperty(String property, Object value, Map<String, Map<String, Object>> filterMap, Order[] orders, String[] groupPropertyNames);

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
	public IResultSet<POJO> listByProperty(Integer pageNumber, Integer pageSize, String property, Object value);

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
	public IResultSet<POJO> listByProperty(Integer pageNumber, Integer pageSize, String property, Object value, Map<String, Map<String, Object>> filterMap, Order[] orders, String[] groupPropertyNames);

	/**
	 * 按Criterion集合条件列出记录
	 * 
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listByPropertySet(Map<String, Object> criterionMap);

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
	public IResultSet<POJO> listByPropertySet(Map<String, Object> criterionMap, Map<String, Map<String, Object>> filterMap, Order[] orders, String[] groupPropertyNames);

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
	public IResultSet<POJO> listByPropertySet(Integer pageNumber, Integer pageSize, Map<String, Object> criterionMap);

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
	public IResultSet<POJO> listByPropertySet(Integer pageNumber, Integer pageSize, Map<String, Object> criterionMap, Map<String, Map<String, Object>> filterMap, Order[] orders, String[] groupPropertyNames);

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listByCriteria(Criterion[] criterion);

	/**
	 * 按Criterion查询列表数据.
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listByCriteria(Criterion[] criterion, String alias);

	/**
	 * 按Criterion查询列表数据. zz
	 * 
	 * @param criterion
	 *            数量可变的Criterion.
	 * @param orders
	 *            指定的排序条件,默认是倒序Id排列
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listByCriteria(Criterion[] criterion, Order[] orders, String[] groupPropertyNames);

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
	public IResultSet<POJO> listByCriteria(Criterion[] criterion, Map<String, Map<String, Object>> filterMap, String alias, Order[] orders, String[] groupPropertyNames);

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
	public IResultSet<POJO> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion);

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
	public IResultSet<POJO> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, String alias);

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
	public IResultSet<POJO> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Order[] orders, String[] groupPropertyNames);

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
	public IResultSet<POJO> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Map<String, Map<String, Object>> filterMap, String alias, Order[] orders, String[] groupPropertyNames);

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
	public <T extends TransformBaseDTO> IResultSet<T> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Class<T> transformDTOClass, Map<String, Map<String, Object>> filterMap, String alias, Order[] orders, String[] groupPropertyNames);

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
	public IResultSet<?> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Class<? extends TransformBaseDTO> transformDTOClass, Boolean flag);

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
	public <T extends TransformBaseDTO> IResultSet<T> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Class<T> transformDTOClass);

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
	public IResultSet<?> listByCriteria(Integer pageNumber, Integer pageSize, Criterion[] criterion, Class<? extends TransformBaseDTO> transformDTOClass, Map<String, Map<String, Object>> filterMap, String alias, Order[] orders, String[] groupPropertyNames, Boolean flag);

	/**
	 * 根据HQL查询记录
	 * 
	 * @param hql
	 *            自定义的HQL
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回查询到的实体
	 */
	public IResultSet<POJO> listByHQL(String hql, ParameterSetterCallback callback);

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
	public IResultSet<POJO> listByHQL(String hql, Map<String, Map<String, Object>> filterMap, ParameterSetterCallback callback);

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
	public IResultSet<POJO> listByHQL(Integer pageNumber, Integer pageSize, String hql, Map<String, Map<String, Object>> filterMap, ParameterSetterCallback callback);

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
	public <T extends TransformBaseDTO> IResultSet<T> listByHQL(String hql, Class<T> transformDTOClass, ParameterSetterCallback callback);

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
	public IResultSet<?> listByHQL(Integer pageNumber, Integer pageSize, String hql, Class<? extends TransformBaseDTO> transformDTOClass, ParameterSetterCallback callback, Boolean flag);

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
	public <T extends TransformBaseDTO> IResultSet<T> listByHQL(Integer pageNumber, Integer pageSize, String hql, Class<T> transformDTOClass, ParameterSetterCallback callback);

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
	public IResultSet<?> listByHQL(Integer pageNumber, Integer pageSize, String hql, Map<String, Map<String, Object>> filterMap, ParameterSetterCallback callback, Boolean flag);

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
	public <T extends TransformBaseDTO> IResultSet<T> listByHQL(Integer pageNumber, Integer pageSize, String hql, Map<String, Map<String, Object>> filterMap, Class<T> transformDTOClass, ParameterSetterCallback callback);

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
	public IResultSet<?> listByHQL(Integer pageNumber, Integer pageSize, String hql, Map<String, Map<String, Object>> filterMap, Class<? extends TransformBaseDTO> transformDTOClass, ParameterSetterCallback callback, Boolean flag);

	/**
	 * 根据SQL查询记录
	 * 
	 * @param sql
	 *            自定义的SQL
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * 
	 * @return 返回查询到的记录
	 */
	public IResultSet<?> listBySQL(String sql, ParameterSetterCallback callback);

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
	public <T extends TransformBaseDTO> IResultSet<T> listBySQL(String sql, Class<T> transformDTOClass, ParameterSetterCallback callback);

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
	public IResultSet<?> listBySQL(Integer pageNumber, Integer pageSize, String sql, Class<? extends TransformBaseDTO> transformDTOClass, ParameterSetterCallback callback, Boolean flag);

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
	public <T extends TransformBaseDTO> IResultSet<T> listBySQL(Integer pageNumber, Integer pageSize, String sql, Class<T> transformDTOClass, ParameterSetterCallback callback);

	/**
	 * 按属性获取唯一对象
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 返回唯一的实体
	 */
	public POJO getUniqueByProperty(String property, Object value);

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
	public POJO getUniqueByProperty(String property, Object value, String[] groupPropertyNames);

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
	public POJO getUniqueByProperty(String property, Object value, Criterion[] additionCriterions);

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
	public POJO getUniqueByProperty(String property, Object value, Criterion[] additionCriterions, String alias);

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
	public POJO getUniqueByProperty(String property, Object value, String[] groupPropertyNames, Criterion[] additionCriterions, String alias);

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
	public POJO getUniqueByProperty(String property, Object value, Map<String, Map<String, Object>> filterMap, String[] groupPropertyNames, Criterion[] additionCriterions, String alias);

	/**
	 * 创建一个DetachedCriteria查询对象，默认要查询的类型是当前支持实体
	 * 
	 * @return DetachedCriteria查询对象
	 */
	public DetachedCriteria createDetachedCriteria();

	/**
	 * 创建一个DetachedCriteria查询对象
	 * 
	 * @param entityClz
	 *            要针对创建的实体类类对象
	 * @return DetachedCriteria查询对象
	 * */
	public DetachedCriteria createDetachedCriteria(Class<?> entityClz);

	/**
	 * 创建一个DetachedCriteria查询对象
	 * 
	 * @param entityClz
	 *            要针对创建的实体类类对象
	 * @param alias
	 *            实体的别名
	 * @return DetachedCriteria查询对象
	 * */
	public DetachedCriteria createDetachedCriteria(Class<?> entityClz, String alias);

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
	public DetachedCriteria createDetachedCriteria(Class<?> entityClz, Criterion[] criterions, String alias);

	/**
	 * 按criterions统计记录数
	 * 
	 * @param criterions
	 *            指定的criterions数组
	 * @return 记录数
	 */
	public Integer countByCriteria(Criterion[] criterions);

	/**
	 * 按criterions统计记录数
	 * 
	 * @param criterions
	 *            指定的criterions数组
	 * @param groupPropertyNames
	 *            组属性名，指定的属性划分组 ,默认是无组
	 * @return 记录数
	 */
	public Integer countByCriteria(Criterion[] criterions, String[] groupPropertyNames);

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
	public Integer countByCriteria(Criterion[] criterions, Map<String, Map<String, Object>> filterMap, String[] groupPropertyNames);

	/**
	 * 按指定的HQL语句统计记录数
	 * 
	 * @param hql
	 *            hql查询语句
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 记录数
	 */
	public Integer countByHQLQuery(String hql, ParameterSetterCallback callback);

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
	public Integer countByHQLQuery(String hql, Map<String, Map<String, Object>> filterMap, final ParameterSetterCallback callback);

	/**
	 * 按指定的SQL语句统计记录数
	 * 
	 * @param sql
	 *            sql查询语句
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * @return 记录数
	 */
	public Integer countBySQLQuery(String sql, ParameterSetterCallback callback);

	/**
	 * 根据id删除记录
	 * 
	 * @param id
	 *            唯一标识
	 * @return 返回受影响的行数
	 */
	public Integer deleteById(ID id);

	/**
	 * 根据多个id删除记录
	 * 
	 * @param ids
	 *            多个唯一标识
	 * @return 返回受影响的行数
	 */
	public Integer deleteByIds(ID[] ids);

	/**
	 * 根据实体删除记录
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteByEntity(POJO entity);

	/**
	 * 根据多个实体删除记录
	 * 
	 * @param entitys
	 *            多个实体
	 */
	public void deleteByEntitys(POJO[] entitys);

	/**
	 * 根据属性值删除记录
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return 返回受影响的行数
	 * */
	public Integer deleteByProperty(String property, Object value);

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
	public Integer deleteByProperty(String property, Object value, Map<String, Map<String, Object>> filterMap);

	/**
	 * 根据属性集合删除记录
	 * 
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * @return 返回受影响的行数
	 * */
	public Integer deleteByPropertySet(Map<String, Object> criterionMap);

	/**
	 * 根据属性集合删除记录
	 * 
	 * @param criterionMap
	 *            Criterion集合条件,用键值对匹配
	 * @param filterMap
	 *            过滤器键值队,key为过滤器名;value为过滤器参数,参数也是一个键值队(因为可以有多个参数),key为参数名,value为参数值(注意:使用过滤器必须先在配置文件配置好)
	 * @return 返回受影响的行数
	 * */
	public Integer deleteByPropertySet(Map<String, Object> criterionMap, Map<String, Map<String, Object>> filterMap);

	/**
	 * 根据过滤条件删除记录
	 * 
	 * @param criterions
	 *            过滤条件数组
	 * @return 返回受影响的行数
	 * */
	public Integer deleteByCriteria(Criterion[] criterions);

	/**
	 * 根据过滤条件删除记录
	 * 
	 * @param criterions
	 *            过滤条件数组
	 * @param alias
	 *            查询中包括的主别名
	 * @return 返回受影响的行数
	 * */
	public Integer deleteByCriteria(Criterion[] criterions, String alias);

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
	public Integer deleteByCriteria(Criterion[] criterions, Map<String, Map<String, Object>> filterMap, String alias);

	/**
	 * 根据提供的HQL来删除数据
	 * 
	 * @param hql
	 *            自定义的HQL，(注意：写实体后面的语法即可，因为内部已经补上"DELETE FROM"防止其他用途)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理HQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	public Integer deleteByHQL(String hql, ParameterSetterCallback callback);

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
	public Integer deleteByHQL(String hql, Map<String, Map<String, Object>> filterMap, ParameterSetterCallback callback);

	/**
	 * 根据提供的SQL来删除数据
	 * 
	 * @param sql
	 *            自定义的SQL，(注意：写实体后面的语法即可，因为内部已经补上"DELETE FROM"防止其他用途)
	 * @param callback
	 *            用来处理参数设置问题的回调函数，用来处理SQL中对应参数的设置
	 * @return 返回受影响的行数
	 * */
	public Integer deleteBySQL(String sql, ParameterSetterCallback callback);

	/** 强制清除缓存 */
	public void clearCache();

	/** 强制进行从内存到数据库的同步 */
	public void flushCache();

	/**
	 * 把指定的缓冲对象进行清除
	 * 
	 * @param entity
	 *            指定对象
	 */
	public void evictCache(POJO entity);

	/**
	 * 获得Dao对应的默认实体类的类类型,此为对外的统一接口
	 * 
	 * @return 实体类的类类型
	 */
	public Class<POJO> getDefaultEntityClass();

	/**
	 * 获得Dao对应的默认排序对象,此为对外的统一接口
	 * 
	 * @return 排序对象数组
	 */
	public Order[] getDefaultOrders();

}