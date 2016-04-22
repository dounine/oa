package net.yasion.common.dao;

import java.util.List;

import net.yasion.common.model.TbPermission;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbRolePermission;

public interface IRolePermissionDAO extends IBaseDAO<TbRolePermission, String> {

	public List<TbRole> getRolesByPermissionId(final String permissionId);

	public List<TbPermission> getPermissionsByRoleId(final String roleId);

	public Integer deleteByRoleId(final String roleId);

	public Integer deleteByPermissionId(final String permissionId);

}