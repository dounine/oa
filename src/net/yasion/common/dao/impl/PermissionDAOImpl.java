package net.yasion.common.dao.impl;

import net.yasion.common.dao.IPermissionDAO;
import net.yasion.common.model.TbPermission;

import org.springframework.stereotype.Repository;

@Repository("permissionDAO")
public class PermissionDAOImpl extends BaseDAOImpl<TbPermission, String> implements IPermissionDAO {

	@Override
	protected Class<TbPermission> getEntityClass() {
		return TbPermission.class;
	}
}