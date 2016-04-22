package net.yasion.common.service;

import net.yasion.common.dto.SuperUserDTO;
import net.yasion.common.model.TbSuperUser;
import net.yasion.common.model.TbUser;

public interface ISuperUserService extends IBaseService<TbSuperUser, SuperUserDTO, String> {

	public TbUser findByUsername(String userName);

	public TbUser lFindByUsername(String userName);

}