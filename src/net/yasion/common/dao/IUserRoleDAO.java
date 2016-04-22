package net.yasion.common.dao;

import java.util.List;

import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbUser;
import net.yasion.common.model.TbUserRole;

public interface IUserRoleDAO extends IBaseDAO<TbUserRole, String> {

	public Integer deleteByRoleId(final String roleId);

	public Integer deleteByUserId(final String userId);

	public List<TbUser> getUsersByRoleId(final String roleId);

	public List<TbRole> getRolesByUserId(final String userId);

}