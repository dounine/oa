package net.yasion.common.service;

import java.io.Serializable;
import java.util.List;

import net.yasion.common.dto.BaseDTO;
import net.yasion.common.model.TbPermission;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbRolePermission;

public interface IRolePermissionService extends IBaseService<TbRolePermission, BaseDTO<String>, String> {

	public List<TbPermission> findPermissionByRole(TbRole role);

	public List<TbRole> findRolesByPermission(TbPermission permission);

	public boolean deleteByRole(TbRole role);

	public boolean deleteByPermission(TbPermission permission);

	public Serializable[] saveByRole(TbRole role, final String[] permissionIds);

	public Serializable[] saveByPermission(TbPermission permission, final String[] roleIds);

}