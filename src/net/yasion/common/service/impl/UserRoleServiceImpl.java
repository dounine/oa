package net.yasion.common.service.impl;

import java.util.List;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IUserRoleDAO;
import net.yasion.common.dto.BaseDTO;
import net.yasion.common.model.TbRole;
import net.yasion.common.model.TbUser;
import net.yasion.common.model.TbUserRole;
import net.yasion.common.service.IUserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl extends BaseServiceImpl<TbUserRole, BaseDTO<String>, String> implements IUserRoleService {

	private IUserRoleDAO userRoleDAO;

	public IUserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	@Autowired
	public void setUserRoleDAO(IUserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	@Override
	public List<TbUser> findUsersByRole(final TbRole role) {
		return this.userRoleDAO.getUsersByRoleId(role.getId());
	}

	@Override
	public List<TbRole> findRolesByUser(final TbUser user) {
		return this.userRoleDAO.getRolesByUserId(user.getId());
	}

	@Override
	public boolean deleteByRole(final TbRole role) {
		return (this.userRoleDAO.deleteByRoleId(role.getId()) > 0);
	}

	@Override
	public boolean deleteByUser(final TbUser user) {
		return (this.userRoleDAO.deleteByUserId(user.getId()) > 0);
	}

	@Override
	public String[] saveByRole(final TbRole role, final String[] userIds) {
		String[] userRoleIds = new String[userIds.length];
		for (int i = 0; i < userIds.length; i++) {
			TbUser user = new TbUser();
			user.setId(userIds[i]);
			TbUserRole userRole = new TbUserRole(user, role);
			userRoleIds[i] = this.userRoleDAO.save(userRole);
		}
		return userRoleIds;
	}

	@Override
	public String[] saveByUser(final TbUser user, final String[] roleIds) {
		String[] userRoleIds = new String[roleIds.length];
		for (int i = 0; i < roleIds.length; i++) {
			TbRole role = new TbRole();
			role.setId(roleIds[i]);
			TbUserRole userRole = new TbUserRole(user, role);
			userRoleIds[i] = this.userRoleDAO.save(userRole);
		}
		return userRoleIds;
	}

	@Override
	protected IBaseDAO<TbUserRole, String> getDefaultDAO() {
		return this.userRoleDAO;
	}
}