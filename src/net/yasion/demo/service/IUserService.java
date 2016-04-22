package net.yasion.demo.service;

import net.yasion.common.dto.BaseDTO;
import net.yasion.common.service.IBaseService;
import net.yasion.demo.model.User;

public interface IUserService extends IBaseService<User, BaseDTO<String>, String> {

	public User insert(User user);

	public void delete(User user);

	public User find(String userId);
}