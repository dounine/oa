package net.yasion.common.core.uurp;

import java.util.List;

import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

public interface UurpAdaptor {

	public List<TbUnit> findUnitTree(String parentUnitId);

	public List<TbUnit> findUnitChildren(String parentUnitId);

	public List<TbUser> findUnitUsers(String parentUnitId);

	public List<TbUser> findUnitAllUsers(String parentUnitId);

	public TbUnit findTopUnit(String unitId);

	public List<TbUnit> queryUnitTree(String parentUnitId);

	public IResultSet<TbUnit> queryUnitTree(String parentUnitId, Integer pageNumber, Integer pageSize);

	public List<TbUnit> queryUnitChildren(String parentUnitId);

	public IResultSet<TbUnit> queryUnitChildren(String parentUnitId, Integer pageNumber, Integer pageSize);

	public List<TbUser> queryUnitUsers(String unitId);

	public IResultSet<TbUser> queryUnitUsers(String unitId, Integer pageNumber, Integer pageSize);

	public List<TbUser> queryUnitAllUsers(String unitId);

	public IResultSet<TbUser> queryUnitAllUsers(String unitId, Integer pageNumber, Integer pageSize);

	public IResultSet<TbUnit> queryOtherUnitTree(String unitId, Integer pageNumber, Integer pageSize);

}