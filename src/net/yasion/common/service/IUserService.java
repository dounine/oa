package net.yasion.common.service;

import net.yasion.common.dto.UserDTO;
import net.yasion.common.model.TbUser;
import net.yasion.common.support.common.dao.interfaces.IResultSet;

public interface IUserService extends IBaseService<TbUser, UserDTO, String> {

	public TbUser findByUsername(String name);

	public TbUser lFindByUsername(String userName);

	public IResultSet<TbUser> lFindByDTOOnAllUser(UserDTO dto, Integer pageNumber, Integer pageSize);

}