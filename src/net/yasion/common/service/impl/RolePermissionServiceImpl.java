package net.yasion.common.service.impl;

import java.io.Serializable;
import java.util.List;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IRolePermissionDAO;
import net.yasion.common.dto.BaseDTO;
import net.yasion.common.model.TbPermission;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbRolePermission;
import net.yasion.common.service.IRolePermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("rolePermissionService")
@Transactional
public class RolePermissionServiceImpl extends BaseServiceImpl<TbRolePermission, BaseDTO<String>, String> implements IRolePermissionService {

	private IRolePermissionDAO rolePermissionDAO;

	public IRolePermissionDAO getRolePermissionDAO() {
		return rolePermissionDAO;
	}

	@Autowired
	public void setRolePermissionDAO(IRolePermissionDAO rolePermissionDAO) {
		this.rolePermissionDAO = rolePermissionDAO;
	}

	@Override
	public List<TbPermission> findPermissionByRole(final TbRole role) {
		return this.rolePermissionDAO.getPermissionsByRoleId(role.getId());
	}

	@Override
	public List<TbRole> findRolesByPermission(final TbPermission permission) {
		return this.rolePermissionDAO.getRolesByPermissionId(permission.getId());
	}

	@Override
	public boolean deleteByRole(final TbRole role) {
		return (this.rolePermissionDAO.deleteByRoleId(role.getId()) > 0);
	}

	@Override
	public boolean deleteByPermission(final TbPermission permission) {
		return (this.rolePermissionDAO.deleteByPermissionId(permission.getId()) > 0);
	}

	@Override
	public Serializable[] saveByRole(final TbRole role, final String[] permissionIds) {
		Serializable[] rolePermissionIds = new Serializable[permissionIds.length];
		for (int i = 0; i < permissionIds.length; i++) {
			TbPermission permission = new TbPermission();
			permission.setId(permissionIds[i]);
			TbRolePermission rolePermission = new TbRolePermission(role, permission);
			rolePermissionIds[i] = this.rolePermissionDAO.save(rolePermission);
		}
		return rolePermissionIds;
	}

	@Override
	public Serializable[] saveByPermission(final TbPermission permission, final String[] roleIds) {
		Serializable[] rolePermissionIds = new Serializable[roleIds.length];
		for (int i = 0; i < roleIds.length; i++) {
			TbRole role = new TbRole();
			role.setId(roleIds[i]);
			TbRolePermission rolePermission = new TbRolePermission(role, permission);
			rolePermissionIds[i] = this.rolePermissionDAO.save(rolePermission);
		}
		return rolePermissionIds;
	}

	@Override
	protected IBaseDAO<TbRolePermission, String> getDefaultDAO() {
		return this.rolePermissionDAO;
	}
}