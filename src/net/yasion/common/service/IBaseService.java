package net.yasion.common.service;

import java.io.Serializable;
import java.util.List;

import net.yasion.common.dto.BaseDTO;
import net.yasion.common.model.BaseModel;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

import org.hibernate.criterion.Criterion;

public interface IBaseService<POJO extends BaseModel<ID>, DTO extends BaseDTO<ID>, ID extends Serializable> {

	/**
	 * 获取T类型的全部记录
	 * 
	 * @return T类型对象列表
	 */
	public List<POJO> listAll();

	/**
	 * 根据ID获取对应的实体
	 * 
	 * @param id
	 *            ID
	 * @return 返回对应实体
	 */
	public POJO findById(ID id);

	/**
	 * 根据ID获取对应的实体
	 * 
	 * @param ids
	 *            ID数组
	 * @return 返回对应实体对象列表
	 */
	public List<POJO> findByIds(ID[] ids);

	/**
	 * 根据ID获取对应的实体
	 * 
	 * @param id
	 *            ID
	 * @return 返回对应实体
	 */
	public POJO loadById(ID id);

	/**
	 * 根据ID获取对应的实体
	 * 
	 * @param ids
	 *            ID数组
	 * @return 返回对应实体对象列表
	 */
	public List<POJO> loadByIds(ID[] ids);

	/**
	 * 根据DTO查询对应的实体集合
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @return 实体集合(支持分页)
	 */
	public IResultSet<POJO> findByDTO(DTO dto, Integer pageNumber, Integer pageSize);

	/**
	 * 根据DTO查询对应的实体集合
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @param additionCriterionList
	 *            附加的查询条件对象列表
	 * @return 实体集合(支持分页)
	 */
	public IResultSet<POJO> findByDTO(DTO dto, Integer pageNumber, Integer pageSize, List<Criterion> additionCriterionList);

	/**
	 * 根据DTO查询对应的实体集合,并且使用上权限控制
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @param additionCriterionList
	 *            附加的查询条件对象列表
	 * @return 实体集合(支持分页)
	 */
	public IResultSet<POJO> findByDTOOnPermission(DTO dto, Integer pageNumber, Integer pageSize, List<Criterion> additionCriterionList);

	/**
	 * 根据DTO查询对应的实体集合,并且使用上权限控制
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @return 实体集合(支持分页)
	 */
	public IResultSet<POJO> findByDTOOnPermission(DTO dto, Integer pageNumber, Integer pageSize);

	/**
	 * 根据DTO查询对应的实体集合(该自动忽略被逻辑删除的记录，就是说flag为'D'的记录不会被查出来)
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @return 实体集合(支持分页)
	 */
	public IResultSet<POJO> lFindByDTO(DTO dto, Integer pageNumber, Integer pageSize);

	/**
	 * 根据DTO查询对应的实体集合,并且使用上权限控制(该自动忽略被逻辑删除的记录，就是说flag为'D'的记录不会被查出来)
	 * 
	 * @param dto
	 *            dto对象
	 * @param pageNumber
	 *            当前页数
	 * @param pageSize
	 *            每页大小
	 * @return 实体集合(支持分页)
	 */
	public IResultSet<POJO> lFindByDTOOnPermission(DTO dto, Integer pageNumber, Integer pageSize);

	/**
	 * 根据ID删除对应的实体
	 * 
	 * @param id
	 *            ID
	 * @return 返回被删除的实体数
	 */
	public Integer removeById(ID id);

	/**
	 * 根据ID数组删除对应的实体
	 * 
	 * @param ids
	 *            ID数组
	 * @return 返回被删除的实体数
	 */
	public Integer removeByIds(ID[] ids);

	/**
	 * 根据ID逻辑删除对应的实体(逻辑删除只是将flag改成'D'标志)
	 * 
	 * @param id
	 *            ID
	 * @return 返回被删除的实体数
	 */
	public Integer lRemoveById(ID id);

	/**
	 * 根据ID数组逻辑删除对应的实体(逻辑删除只是将flag改成'D'标志)
	 * 
	 * @param ids
	 *            ID数组
	 * @return 返回被删除的实体数
	 */
	public Integer lRemoveByIds(ID[] ids);

	/**
	 * 根据实体保存数据
	 * 
	 * @param entity
	 *            实体(游离态)
	 * @return 返回实体(持久态)
	 */
	public POJO save(POJO entity);

	/**
	 * 根据DTO保存数据,期间会转成实体
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回实体,或者null
	 */
	public POJO save(DTO dto);

	/**
	 * 根据DTO保存数据,期间会转成实体(不保存逻辑删除字段flag,只能通过逻辑删除进行flag赋值)
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回实体,或者null
	 */
	public POJO lSave(DTO dto);

	/**
	 * 根据实体更新数据
	 * 
	 * @param entity
	 *            实体(游离态)
	 * @return 返回实体(持久态)
	 */
	public POJO update(POJO entity);

	/**
	 * 根据DTO更新数据,期间会转成实体
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回实体
	 */
	public POJO update(DTO dto);

	/**
	 * 根据DTO更新数据,期间会转成实体(不保存逻辑删除字段flag,只能通过逻辑删除进行flag赋值)
	 * 
	 * @param dto
	 *            DTO对象
	 * @return 返回实体
	 */
	public POJO lUpdate(DTO dto);
}