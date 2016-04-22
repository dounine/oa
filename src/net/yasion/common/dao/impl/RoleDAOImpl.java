package net.yasion.common.dao.impl;

import net.yasion.common.dao.IRoleDAO;
import net.yasion.common.model.TbRole;

import org.springframework.stereotype.Repository;

@Repository("roleDAO")
public class RoleDAOImpl extends BaseDAOImpl<TbRole, String> implements IRoleDAO {

	@Override
	protected Class<TbRole> getEntityClass() {
		return TbRole.class;
	}
}