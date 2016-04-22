package net.yasion.common.dao.impl;

import net.yasion.common.dao.ISuperUserDAO;
import net.yasion.common.model.TbSuperUser;

import org.springframework.stereotype.Repository;

@Repository("superUserDAO")
public class SuperUserDAOImpl extends BaseDAOImpl<TbSuperUser, String> implements ISuperUserDAO {

	@Override
	protected Class<TbSuperUser> getEntityClass() {
		return TbSuperUser.class;
	}
}