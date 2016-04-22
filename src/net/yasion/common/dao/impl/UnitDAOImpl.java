package net.yasion.common.dao.impl;

import net.yasion.common.dao.IUnitDAO;
import net.yasion.common.model.TbUnit;

import org.springframework.stereotype.Repository;

@Repository("unitDAO")
public class UnitDAOImpl extends BaseDAOImpl<TbUnit, String> implements IUnitDAO {

	@Override
	protected Class<TbUnit> getEntityClass() {
		return TbUnit.class;
	}
}