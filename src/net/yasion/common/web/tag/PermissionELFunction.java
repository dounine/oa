package net.yasion.common.web.tag;

import java.util.List;

import net.yasion.common.core.bean.manager.SpringBeanManager;
import net.yasion.common.model.TbPermission;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUser;
import net.yasion.common.service.IPermissionService;
import net.yasion.common.service.IRolePermissionService;
import net.yasion.common.service.IRoleService;
import net.yasion.common.service.IUserRoleService;
import net.yasion.common.service.impl.PermissionServiceImpl;
import net.yasion.common.service.impl.RolePermissionServiceImpl;
import net.yasion.common.service.impl.RoleServiceImpl;
import net.yasion.common.service.impl.UserRoleServiceImpl;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class PermissionELFunction {

	private static IRoleService roleService = SpringBeanManager.getBean(IRoleService.class, RoleServiceImpl.class);
	private static IPermissionService permissionService = SpringBeanManager.getBean(IPermissionService.class, PermissionServiceImpl.class);
	private static IUserRoleService userRoleService = SpringBeanManager.getBean(IUserRoleService.class, UserRoleServiceImpl.class);
	private static IRolePermissionService rolePermissionService = SpringBeanManager.getBean(IRolePermissionService.class, RolePermissionServiceImpl.class);

	/**
	 * 判断某用户是否有指定权限
	 * 
	 * @param user
	 *            指定用户
	 * @param permissionCodes
	 *            对应权限的code
	 * @return 是否有权限
	 */
	public static Boolean isPermissions(TbUser user, String[] permissionCodes) {
		if (null != user && ArrayUtils.isNotEmpty(permissionCodes)) {
			String[] newPermissionCodes = new String[permissionCodes.length];
			for (int i = 0, len = permissionCodes.length; i < len; i++) {
				newPermissionCodes[i] = StringUtils.trim(permissionCodes[i]);
			}
			if (!(user instanceof TbSuperUser)) {
				List<TbPermission> entityLsit = permissionService.findByCodes(newPermissionCodes);
				if (null != entityLsit && 0 < entityLsit.size()) {
					List<TbRole> roleList = userRoleService.findRolesByUser(user);
					for (int i = 0, len = roleList.size(); i < len; i++) {
						TbRole role = roleList.get(i);
						if (null != role) {// 如果无role，默认全部允许
							List<TbPermission> permissionList = rolePermissionService.findPermissionByRole(role);
							for (int j = 0, len2 = permissionList.size(); j < len2; j++) {
								TbPermission permission = permissionList.get(j);
								if (null != permission) {
									if (entityLsit.contains(permission)) {
										return true;
									}
								}
							}
						}
					}
				}
			} else {// 超级管理员直接通过
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断某用户是否为某指定角色
	 * 
	 * @param user
	 *            指定用户
	 * @param roleCodes
	 *            对应角色的code
	 * @return 是否为指定角色
	 */
	public static Boolean isRoles(TbUser user, String[] roleCodes) {
		if (null != user && ArrayUtils.isNotEmpty(roleCodes)) {
			String[] newRoleCodes = new String[roleCodes.length];
			for (int i = 0, len = roleCodes.length; i < len; i++) {
				newRoleCodes[i] = StringUtils.trim(roleCodes[i]);
			}
			if (!(user instanceof TbSuperUser)) {
				List<TbRole> entityLsit = roleService.findByCodes(newRoleCodes);
				if (null != entityLsit && 0 < entityLsit.size()) {
					List<TbRole> roleList = userRoleService.findRolesByUser(user);
					for (int i = 0, len = roleList.size(); i < len; i++) {
						TbRole role = roleList.get(i);
						if (null != role) {
							if (entityLsit.contains(role)) {
								return true;
							}
						}
					}
				}
			} else {// 超级管理员直接通过
				return true;
			}
		}
		return false;
	}
}