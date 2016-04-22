package net.yasion.demo.service.impl;

import net.yasion.common.dao.IBaseDAO;
import net.yasion.common.dto.BaseDTO;
import net.yasion.common.service.impl.BaseServiceImpl;
import net.yasion.demo.dao.IUserDAO;
import net.yasion.demo.model.User;
import net.yasion.demo.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("demoUserService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, BaseDTO<String>, String> implements IUserService {

	@Autowired
	private IUserDAO demoUserDAO;

	public IUserDAO getDemoUserDAO() {
		return demoUserDAO;
	}

	@Autowired
	public void setDemoUserDAO(IUserDAO demoUserDAO) {
		this.demoUserDAO = demoUserDAO;
	}

	@Override
	@Transactional(readOnly = false)
	public User insert(User user) {
		demoUserDAO.save(user);
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(User user) {
		demoUserDAO.delete(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User find(String userId) {
		return demoUserDAO.find(userId);
	}

	@Override
	protected IBaseDAO<User, String> getDefaultDAO() {
		return this.demoUserDAO;
	}
}