package net.yasion.common.dao.impl;

import net.yasion.common.dao.IUserDAO;
import net.yasion.common.model.TbUser;

import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAOImpl extends BaseDAOImpl<TbUser, String> implements IUserDAO {

	@Override
	protected Class<TbUser> getEntityClass() {
		return TbUser.class;
	}
}