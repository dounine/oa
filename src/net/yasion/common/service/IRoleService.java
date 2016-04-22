package net.yasion.common.service;

import java.util.List;

import net.yasion.common.dto.RoleDTO;
import net.yasion.common.model.TbRole;

public interface IRoleService extends IBaseService<TbRole, RoleDTO, String> {

	public List<TbRole> findByCodes(String[] codes);

}