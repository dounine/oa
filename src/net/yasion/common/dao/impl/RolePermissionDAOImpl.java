package net.yasion.common.dao.impl;

import java.util.List;

import net.yasion.common.dao.IPermissionDAO;
import net.yasion.common.dao.IRoleDAO;
import net.yasion.common.dao.IRolePermissionDAO;
import net.yasion.common.model.TbPermission;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbRolePermission;
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

@Repository("rolePermissionDAO")
public class RolePermissionDAOImpl extends BaseDAOImpl<TbRolePermission, String> implements IRolePermissionDAO {

	private IRoleDAO roleDAO;

	private IPermissionDAO permissionDAO;

	public IPermissionDAO getPermissionDAO() {
		return permissionDAO;
	}

	@Autowired
	public void setPermissionDAO(IPermissionDAO permissionDAO) {
		this.permissionDAO = permissionDAO;
	}

	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}

	@Autowired
	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	protected Class<TbRolePermission> getEntityClass() {
		return TbRolePermission.class;
	}

	@Override
	public List<TbRole> getRolesByPermissionId(final String permissionId) {
		DetachedCriteria userRoleCriteria = DetachedCriteria.forClass(this.getEntityClass(), "rolePermission").add(Restrictions.eqProperty("rolePermission.tbRole.id", "role.id")).add(Restrictions.eq("rolePermission.tbPermission.id", permissionId));
		IResultSet<TbRole> roleSet = this.roleDAO.listByCriteria(new Criterion[] { Subqueries.exists(userRoleCriteria.setProjection(Projections.property("tbRole"))) }, "role");
		return roleSet.getResultList();
	}

	@Override
	public List<TbPermission> getPermissionsByRoleId(final String roleId) {
		DetachedCriteria userRoleCriteria = DetachedCriteria.forClass(this.getEntityClass(), "rolePermission").add(Restrictions.eqProperty("rolePermission.tbPermission.id", "permission.id")).add(Restrictions.eq("rolePermission.tbRole.id", roleId));
		IResultSet<TbPermission> permissionSet = this.permissionDAO.listByCriteria(new Criterion[] { Subqueries.exists(userRoleCriteria.setProjection(Projections.property("tbPermission"))) }, "permission");
		return permissionSet.getResultList();
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
	public Integer deleteByPermissionId(final String permissionId) {
		return this.deleteByHQL(this.getEntityClass().getName() + " WHERE tbPermission.id = :permissionId ", new ParameterSetterCallback() {

			@Override
			public void onSetter(Query query) {
				query.setString("permissionId", permissionId);
			}
		});
	}
}