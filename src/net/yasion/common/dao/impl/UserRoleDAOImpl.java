package net.yasion.common.dao.impl;

import java.util.List;

import net.yasion.common.dao.IRoleDAO;
import net.yasion.common.dao.IUserDAO;
import net.yasion.common.dao.IUserRoleDAO;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbUser;
import net.yasion.common.model.TbUserRole;
import net.yasion.common.support.common.dao.callback.ParameterSetterCallback;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("userRoleDAO")
public class UserRoleDAOImpl extends BaseDAOImpl<TbUserRole, String> implements IUserRoleDAO {

	private IUserDAO userDAO;

	private IRoleDAO roleDAO;

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	@Autowired
	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}

	@Autowired
	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	protected Class<TbUserRole> getEntityClass() {
		return TbUserRole.class;
	}

	@Override
	public List<TbUser> getUsersByRoleId(final String roleId) {
		DetachedCriteria userRoleCriteria = DetachedCriteria.forClass(this.getEntityClass(), "userRole").add(Restrictions.eqProperty("userRole.tbUser.id", "user.id")).add(Restrictions.eq("userRole.tbRole.id", roleId));
		IResultSet<TbUser> userSet = this.userDAO.listByCriteria(new Criterion[] { Subqueries.exists(userRoleCriteria.setProjection(Projections.property("tbUser"))) }, "user");
		return userSet.getResultList();
	}

	@Override
	public List<TbRole> getRolesByUserId(final String userId) {
		DetachedCriteria userRoleCriteria = DetachedCriteria.forClass(this.getEntityClass(), "userRole").add(Restrictions.eqProperty("userRole.tbRole.id", "role.id")).add(Restrictions.eq("userRole.tbUser.id", userId));
		IResultSet<TbRole> roleSet = this.roleDAO.listByCriteria(new Criterion[] { Subqueries.exists(userRoleCriteria.setProjection(Projections.property("tbRole"))) }, "role");
		return roleSet.getResultList();
	}

	@Override
	public Integer deleteByRoleId(final String roleId) {
		return this.deleteByHQL(this.getEntityClass().getName() + " WHERE tbRole.id = :roleId ", new ParameterSetterCallback() {

			@Override
			public void onSetter(Query query) {
				query.setString("roleId", roleId);
			}
		});
	}

	@Override
	public Integer deleteByUserId(final String userId) {
		return this.deleteByHQL(this.getEntityClass().getName() + " WHERE tbUser.id = :userId ", new ParameterSetterCallback() {

			@Override
			public void onSetter(Query query) {
				query.setString("userId", userId);
			}
		});
	}
}