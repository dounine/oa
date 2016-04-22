package net.yasion.common.service;

import java.util.List;

import net.yasion.common.dto.BaseDTO;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbUser;
import net.yasion.common.model.TbUserRole;

public interface IUserRoleService extends IBaseService<TbUserRole, BaseDTO<String>, String> {

	public List<TbUser> findUsersByRole(final TbRole role);

	public List<TbRole> findRolesByUser(final TbUser user);

	public boolean deleteByRole(final TbRole role);

	public boolean deleteByUser(final TbUser user);

	public String[] saveByRole(final TbRole role, final String[] userIds);

	public String[] saveByUser(final TbUser user, final String[] roleIds);

}