package net.yasion.demo.dao.impl;

import net.yasion.common.dao.impl.BaseDAOImpl;
import net.yasion.demo.dao.IUserDAO;
import net.yasion.demo.model.User;

import org.springframework.stereotype.Repository;

@Repository("demoUserDAO")
public class UserDAOImpl extends BaseDAOImpl<User, String> implements IUserDAO {

	@Override
	public User insert(User user) {
		// Session session = sessionFactory.getCurrentSession();
		// Transaction tx = session.beginTransaction();
		// session.save(user);
		this.save(user);
		// tx.commit();
		// session.flush();
		// session.close();
		return user;
	}

	@Override
	public void delete(User user) {
		this.deleteByEntity(user);
	}

	@Override
	public User find(String userId) {
		return this.get(userId);
	}

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}
}