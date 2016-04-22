package net.yasion.common.core.uurp.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.yasion.common.core.uurp.UurpAdaptor;
import net.yasion.common.dao.ISuperUserDAO;
import net.yasion.common.dao.IUnitDAO;
import net.yasion.common.dao.IUserDAO;
import net.yasion.common.dao.IUserRoleDAO;
import net.yasion.common.model.TbUnit;
import net.yasion.common.model.TbUser;
import net.yasion.common.support.common.dao.callback.ParameterSetterCallback;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class UurpAdaptorImpl implements UurpAdaptor {

	private IUnitDAO unitDAO;

	private IUserDAO userDAO;

	private ISuperUserDAO superUserDAO;

	private IUserRoleDAO userRoleDAO;

	public IUnitDAO getUnitDAO() {
		return unitDAO;
	}

	@Autowired
	public void setUnitDAO(IUnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	@Autowired
	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ISuperUserDAO getSuperUserDAO() {
		return superUserDAO;
	}

	@Autowired
	public void setSuperUserDAO(ISuperUserDAO superUserDAO) {
		this.superUserDAO = superUserDAO;
	}

	public IUserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	@Autowired
	public void setUserRoleDAO(IUserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	@Override
	public List<TbUnit> findUnitTree(String parentUnitId) {
		TbUnit tbUnit = this.unitDAO.get(parentUnitId);
		List<TbUnit> parentSubUnits = new ArrayList<TbUnit>(tbUnit.getSubUnits());
		Iterator<TbUnit> parentSubUnitIt = parentSubUnits.iterator();
		List<TbUnit> allSubUnit = new ArrayList<TbUnit>();
		while (parentSubUnitIt.hasNext()) {
			TbUnit parentSubUnit = parentSubUnitIt.next();
			List<TbUnit> subUnits = this.findUnitTreeHelper(parentSubUnit);
			allSubUnit.addAll(subUnits);
		}
		return allSubUnit;
	}

	@Override
	public List<TbUnit> findUnitChildren(String parentUnitId) {
		TbUnit tbUnit = this.unitDAO.get(parentUnitId);
		List<TbUnit> parentSubUnits = new ArrayList<TbUnit>(tbUnit.getSubUnits());
		return parentSubUnits;
	}

	private List<TbUnit> findUnitTreeHelper(TbUnit parentUnit) {
		List<TbUnit> parentSubUnits = new ArrayList<TbUnit>(parentUnit.getSubUnits());
		Iterator<TbUnit> parentSubUnitIt = parentSubUnits.iterator();
		List<TbUnit> allSubUnit = new ArrayList<TbUnit>();
		allSubUnit.add(parentUnit);
		while (parentSubUnitIt.hasNext()) {
			TbUnit parentSubUnit = parentSubUnitIt.next();
			List<TbUnit> subUnits = findUnitTreeHelper(parentSubUnit);
			allSubUnit.addAll(subUnits);
		}
		return allSubUnit;
	}

	@Override
	public List<TbUser> findUnitUsers(String parentUnitId) {
		TbUnit tbUnit = this.unitDAO.get(parentUnitId);
		return new ArrayList<TbUser>(tbUnit.getTbUsers());
	}

	@Override
	public List<TbUser> findUnitAllUsers(String parentUnitId) {
		TbUnit tbUnit = this.unitDAO.get(parentUnitId);
		List<TbUnit> parentSubUnits = new ArrayList<TbUnit>(tbUnit.getSubUnits());
		Iterator<TbUnit> parentSubUnitIt = parentSubUnits.iterator();
		List<TbUser> allSubUser = new ArrayList<TbUser>();
		while (parentSubUnitIt.hasNext()) {
			TbUnit parentSubUnit = parentSubUnitIt.next();
			List<TbUser> subUnitsUser = this.findUnitAllUsersHelper(parentSubUnit);
			allSubUser.addAll(subUnitsUser);
		}
		return allSubUser;
	}

	private List<TbUser> findUnitAllUsersHelper(TbUnit parentUnit) {
		List<TbUnit> parentSubUnits = new ArrayList<TbUnit>(parentUnit.getSubUnits());
		Iterator<TbUnit> parentSubUnitIt = parentSubUnits.iterator();
		List<TbUser> allSubUser = new ArrayList<TbUser>();
		allSubUser.addAll(parentUnit.getTbUsers());
		while (parentSubUnitIt.hasNext()) {
			TbUnit parentSubUnit = parentSubUnitIt.next();
			List<TbUser> subUnitsUser = findUnitAllUsersHelper(parentSubUnit);
			allSubUser.addAll(subUnitsUser);
		}
		return allSubUser;
	}

	@Override
	public TbUnit findTopUnit(String unitId) {
		TbUnit unit = this.unitDAO.get(unitId);
		return (null == unit ? unit : findTopUnitHelper(unit));
	}

	private TbUnit findTopUnitHelper(TbUnit thisUnit) {
		TbUnit parent = thisUnit.getParent();
		if (null == parent) {
			return thisUnit;
		} else {
			return findTopUnitHelper(parent);
		}
	}

	@Override
	public List<TbUnit> queryUnitTree(String parentUnitId) {
		IResultSet<TbUnit> unitSet = this.queryUnitTree(parentUnitId, null, null);
		return (null != unitSet ? unitSet.getResultList() : null);
	}

	@Override
	public IResultSet<TbUnit> queryUnitTree(String parentUnitId, Integer pageNumber, Integer pageSize) {
		final List<String> subUnitIdList = this.querySubUnitHelper(new String[] { parentUnitId });
		if (CollectionUtils.isNotEmpty(subUnitIdList)) {
			StringBuilder builder = new StringBuilder(" FROM " + this.unitDAO.getDefaultEntityClass().getName() + " WHERE ");
			for (int i = 0, len = subUnitIdList.size(); i < len; i++) {
				builder.append(" id = :id" + i + (i < len - 1 ? " OR " : ""));
			}
			builder.append(" ORDER BY id");
			IResultSet<TbUnit> unitSet = this.unitDAO.listByHQL(pageNumber, pageSize, builder.toString(), null, new ParameterSetterCallback() {
				@Override
				public void onSetter(Query query) {
					for (int i = 0, len = subUnitIdList.size(); i < len; i++) {
						query.setParameter("id" + i, subUnitIdList.get(i));
					}
				}
			});
			return unitSet;
		}
		return null;
	}

	@Override
	public IResultSet<TbUnit> queryOtherUnitTree(String unitId, Integer pageNumber, Integer pageSize) {
		final List<String> subUnitIdList = this.querySubUnitHelper(new String[] { unitId });
		subUnitIdList.add(unitId);
		StringBuilder builder = new StringBuilder(" FROM " + this.unitDAO.getDefaultEntityClass().getName() + " WHERE ");
		for (int i = 0, len = subUnitIdList.size(); i < len; i++) {
			builder.append(" id <> :id" + i + (i < len - 1 ? " AND " : ""));
		}
		builder.append(" ORDER BY id");
		IResultSet<TbUnit> unitSet = this.unitDAO.listByHQL(pageNumber, pageSize, builder.toString(), null, new ParameterSetterCallback() {
			@Override
			public void onSetter(Query query) {
				for (int i = 0, len = subUnitIdList.size(); i < len; i++) {
					query.setParameter("id" + i, subUnitIdList.get(i));
				}
			}
		});
		return unitSet;
	}

	@Override
	public List<TbUnit> queryUnitChildren(String parentUnitId) {
		IResultSet<TbUnit> unitSet = this.queryUnitChildren(parentUnitId, null, null);
		return unitSet.getResultList();
	}

	@Override
	public IResultSet<TbUnit> queryUnitChildren(final String parentUnitId, Integer pageNumber, Integer pageSize) {
		StringBuilder builder = new StringBuilder(" FROM " + this.unitDAO.getDefaultEntityClass().getName() + " WHERE parent.id = :parentUnitId ");
		IResultSet<TbUnit> unitSet = this.unitDAO.listByHQL(pageNumber, pageSize, builder.toString(), null, new ParameterSetterCallback() {
			@Override
			public void onSetter(Query query) {
				query.setParameter("parentUnitId", parentUnitId);
			}
		});
		return unitSet;
	}

	@Override
	public List<TbUser> queryUnitUsers(final String unitId) {
		IResultSet<TbUser> userSet = this.queryUnitUsers(unitId, null, null);
		return userSet.getResultList();
	}

	@Override
	public IResultSet<TbUser> queryUnitUsers(final String unitId, Integer pageNumber, Integer pageSize) {
		StringBuilder builder = new StringBuilder(" FROM " + this.userDAO.getDefaultEntityClass().getName() + " WHERE tbUnit.id = :unitId ");
		IResultSet<TbUser> uerSet = this.userDAO.listByHQL(pageNumber, pageSize, builder.toString(), null, new ParameterSetterCallback() {
			@Override
			public void onSetter(Query query) {
				query.setParameter("unitId", unitId);
			}
		});
		return uerSet;
	}

	@Override
	public List<TbUser> queryUnitAllUsers(String unitId) {
		IResultSet<TbUser> userSet = this.queryUnitAllUsers(unitId, null, null);
		return userSet.getResultList();
	}

	@Override
	public IResultSet<TbUser> queryUnitAllUsers(String unitId, Integer pageNumber, Integer pageSize) {
		final List<String> subUnitIdList = querySubUnitHelper(new String[] { unitId });
		subUnitIdList.add(unitId);
		StringBuilder builder = new StringBuilder(" FROM " + this.userDAO.getDefaultEntityClass().getName() + " WHERE ");
		for (int i = 0, len = subUnitIdList.size(); i < len; i++) {
			builder.append(" tbUnit.id = :unitId" + i + (i < len - 1 ? " OR " : ""));
		}
		builder.append(" ORDER BY id");
		IResultSet<TbUser> userSet = this.userDAO.listByHQL(pageNumber, pageSize, builder.toString(), null, new ParameterSetterCallback() {
			@Override
			public void onSetter(Query query) {
				for (int i = 0, len = subUnitIdList.size(); i < len; i++) {
					query.setParameter("unitId" + i, subUnitIdList.get(i));
				}
			}
		});
		return userSet;
	}

	private List<String> querySubUnitHelper(final String[] parentUnitIds) {
		List<String> subUnitIdList = new ArrayList<String>();
		if (ArrayUtils.isNotEmpty(parentUnitIds)) {
			StringBuilder builder = new StringBuilder("SELECT id FROM tb_unit WHERE ");
			for (int i = 0, len = parentUnitIds.length; i < len; i++) {
				builder.append(" parent_id = :parentId" + i + (i < len - 1 ? " OR " : ""));
			}
			builder.append(" ORDER BY id");
			IResultSet<?> childrenSet = this.unitDAO.listBySQL(builder.toString(), new ParameterSetterCallback() {
				@Override
				public void onSetter(Query query) {
					for (int i = 0, len = parentUnitIds.length; i < len; i++) {
						query.setParameter("parentId" + i, parentUnitIds[i]);
					}
				}
			});
			List<?> parentUnitIdList = childrenSet.getResultList();
			List<Object> newParentUnitIdList = new ArrayList<Object>();
			newParentUnitIdList.addAll(parentUnitIdList);
			String[] subUnitIdArr = newParentUnitIdList.toArray(new String[0]);
			subUnitIdList.addAll(new ArrayList<String>(Arrays.asList(subUnitIdArr)));
			List<String> subSubUnitIdList = this.querySubUnitHelper(subUnitIdArr);
			subUnitIdList.addAll(subSubUnitIdList);
		}
		return subUnitIdList;
	}
}