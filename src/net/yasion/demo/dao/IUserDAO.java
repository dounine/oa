package net.yasion.demo.dao;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.demo.model.User;

public interface IUserDAO extends IBaseDAO<User, String> {

	public User insert(User user);

	public void delete(User user);

	public User find(String userId);
}