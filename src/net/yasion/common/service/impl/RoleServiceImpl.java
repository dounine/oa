package net.yasion.common.service.impl;

import java.util.List;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dao.IRoleDAO;
import net.yasion.common.dao.IRolePermissionDAO;
import net.yasion.common.dao.IUserRoleDAO;
import net.yasion.common.dto.RoleDTO;
import net.yasion.common.model.TbRole;
import net.yasion.common.service.IRoleService;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<TbRole, RoleDTO, String> implements IRoleService {

	private IRoleDAO roleDAO;

	private IRolePermissionDAO rolePermissionDAO;

	private IUserRoleDAO userRoleDAO;

	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}

	@Autowired
	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public IRolePermissionDAO getRolePermissionDAO() {
		return rolePermissionDAO;
	}

	@Autowired
	public void setRolePermissionDAO(IRolePermissionDAO rolePermissionDAO) {
		this.rolePermissionDAO = rolePermissionDAO;
	}

	public IUserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}

	@Autowired
	public void setUserRoleDAO(IUserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}

	@Override
	public Integer removeById(String id) {
		rolePermissionDAO.deleteByRoleId(id);
		userRoleDAO.deleteByRoleId(id);
		return roleDAO.deleteById(id);
	}

	@Override
	public Integer removeByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			rolePermissionDAO.deleteByRoleId(ids[i]);
		}
		for (int i = 0, len = ids.length; i < len; i++) {
			userRoleDAO.deleteByRoleId(ids[i]);
		}
		return roleDAO.deleteByIds(ids);
	}

	@Override
	public List<TbRole> findByCodes(String[] codes) {
		Criterion criterion = null;
		for (int i = 0, len = codes.length; i < len; i++) {
			Criterion thisCriterion = Restrictions.eq("code", codes[i]);
			if (null != criterion) {
				criterion = Restrictions.or(criterion, thisCriterion);
			} else {
				criterion = thisCriterion;
			}
		}
		IResultSet<TbRole> entityList = roleDAO.listByCriteria(new Criterion[] { criterion }, new Order[] { Order.desc("id") }, null);
		return entityList.getResultList();
	}

	@Override
	protected IBaseDAO<TbRole, String> getDefaultDAO() {
		return this.roleDAO;
	}
}