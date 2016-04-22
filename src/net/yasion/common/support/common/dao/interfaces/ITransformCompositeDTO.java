package net.yasion.common.support.common.dao.interfaces;

/**
 * 转换成一个实际的对象的时候用来标识的接口,因为HQL、Criteria有可能返回的是多个实体, 通过接口可以标识这个是转换成一个实际对象,这里相当于多个实体的组成一个复合的DTO
 */
public interface ITransformCompositeDTO {

	/** 根据实体获取指定的名字,用于多个实体时候区分开是哪一个实体,用于区分相同类型的实体 */
	public String getFieldName(Object value, String alias, Integer index);
}