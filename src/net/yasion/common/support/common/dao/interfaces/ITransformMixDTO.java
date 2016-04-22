package net.yasion.common.support.common.dao.interfaces;

/**
 * 转换成一个实际的对象的时候用来标识的接口,因为HQL、Criteria有可能返回的是多个实体, 通过接口可以标识这个是转换成一个实际对象,这里相当于多个实体的合并成一个DTO
 */
public interface ITransformMixDTO {

	/** 根据实体获取指定的前缀,用于多个实体时候区分开 */
	public String getPrefix(Object value, String alias, Integer index);
}