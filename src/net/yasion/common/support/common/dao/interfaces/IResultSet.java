package net.yasion.common.support.common.dao.interfaces;

import java.util.List;

/** 结果集接口 */
public interface IResultSet<U> {

	/** 是否分页结果集 */
	public boolean isPage();

	/** 获取查询到的实体 */
	public List<U> getResultList();

	/** 获取分页总页数(无分页时候为0) */
	public int getTotalPageCount();

	/** 获取页面大小(无分页时候为0) */
	public int getPageSize();

	/** 获取页数(无分页时候为0) */
	public int getPageNumber();

	/** 获取查询到实体数量 */
	public int getTotalResultCount();
}